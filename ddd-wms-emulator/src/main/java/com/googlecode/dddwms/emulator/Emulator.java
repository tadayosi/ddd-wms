package com.googlecode.dddwms.emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.emulator.model.Arrival;
import com.googlecode.dddwms.emulator.servlet.WebSocket;

public class Emulator implements Runnable {
	
	/** 出荷指示間隔 */
	private static final long SHIP_INTERVAL = 30000;
	
	protected Logger log = LoggerFactory.getLogger(Emulator.class);
	protected WebSocket ws = new WebSocket();
	protected HttpClient client = new HttpClient();
	protected String targetUrl;
	protected Random random = new Random();
	
	private long lastShipTime = 0;
	
	protected List<Arrival> arrivalList = new ArrayList<Arrival>();

	public Emulator(String targetUrl) {
		this.targetUrl = targetUrl;
		client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		try {
			client.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			arrivalRequest();
			arrival();
			shippingRequest();
			ship();
		} catch (Throwable e) {
			log.warn(e.getMessage(), e);
		}
	}

	/**
	 * 入荷
	 */
	private void arrival() {
		long currentTime = System.currentTimeMillis();
		for (Iterator<Arrival> iterator = arrivalList.iterator(); iterator.hasNext();) {
			Arrival arrival = iterator.next();
			if (arrival.time <= currentTime) {
				iterator.remove();
				Map<String, Object> post = new HashMap<String, Object>();
				post.put("id", arrival.id);
				post("/arrival", JSON.toString(post));

				Map<String, Object> command = new HashMap<String, Object>();
				command.put("type", "arrival");
				command.put("data", post);
				ws.sendMessage(JSON.toString(command));
			}
		}
	}

	/**
	 * 入荷依頼
	 */
	private void arrivalRequest() {
		if (random.nextInt(5) != 0) {
			return;
		}
		long currentTime = System.currentTimeMillis();
		Map<String, Object> post = new HashMap<String, Object>();
		long time = currentTime + (random.nextInt(25) + 5) * 1000;
		post.put("time", time);
		post.put("items", createItems());
		String content = post("/arrivalRequest", JSON.toString(post));
		if (content != null) {
			Arrival arrival = new Arrival();
			arrival.id = (Long)((Map<?, ?>)JSON.parse(content)).get("id");
			arrival.time = time;
			arrivalList.add(arrival);
			post.put("id", arrival.id);

			Map<String, Object> command = new HashMap<String, Object>();
			command.put("type", "arrivalRequest");
			command.put("data", post);
			ws.sendMessage(JSON.toString(command));
		}
	}

	/**
	 * 出荷指示
	 */
	private void shippingRequest() {
		if (random.nextInt(5) != 0) {
			return;
		}
		long currentTime = System.currentTimeMillis();
		Map<String, Object> post = new HashMap<String, Object>();
		long time = currentTime + (random.nextInt(25) + 5) * 1000;
		post.put("time", time);
		post.put("items", createItems());
		String content = post("/shippingRequest", JSON.toString(post));
		if (content != null) {
			Arrival arrival = new Arrival();
			arrival.id = (Long)((Map<?, ?>)JSON.parse(content)).get("id");
			arrival.time = time;
			arrivalList.add(arrival);
			post.put("id", arrival.id);

			Map<String, Object> command = new HashMap<String, Object>();
			command.put("type", "shippingRequest");
			command.put("data", post);
			ws.sendMessage(JSON.toString(command));
		}
	}
	
	/**
	 * 出荷
	 * 出荷指示の間隔ごとごとに出荷リクエストを送信する
	 */
	private void ship() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastShipTime < SHIP_INTERVAL){
			return;
		}
		post("/ship", "{}");
		lastShipTime = currentTime;
		// TODO WebSocketとの通信を実装する？
	}
	
	protected List<Map<String, Object>> createItems() {
		int count = random.nextInt(4) + 1;
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>(count);
		for (int i = 0; i < count; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", random.nextInt(4) + 1);
			item.put("amount", random.nextInt(10) + 1);
			items.add(item);
		}
		return items;
	}
	
	/**
	 * HTTP POST
	 * @param uri
	 * @param data
	 * @return
	 */
	private String post(String uri, String data) {
		log.debug("post {} {}", uri, data);
		ContentExchange exchange = new ContentExchange(true);
		exchange.setMethod("POST");
		exchange.setURL(targetUrl + uri);
		exchange.setRequestContent(new ByteArrayBuffer(data));
		try {
			client.send(exchange);
			int status = exchange.waitForDone();
			if (status == HttpExchange.STATUS_COMPLETED) {
				return exchange.getResponseContent();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}