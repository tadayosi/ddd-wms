package com.googlecode.dddwms.emulator;

import java.io.IOException;
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
	protected Logger log = LoggerFactory.getLogger(Emulator.class);
	protected WebSocket ws = new WebSocket();
	protected HttpClient client = new HttpClient();
	protected String targetUrl;
	protected Random random = new Random();
	
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
			//入荷依頼
			long currentTime = System.currentTimeMillis();
			if (random.nextInt(5) == 0) {
				Map<String, Object> post = new HashMap<String, Object>();
				long time = currentTime + (random.nextInt(25) + 5) * 1000;
				post.put("time", time);
				post.put("items", "");	//TODO
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
			//入荷
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
			
			//出荷指示
			if (random.nextInt(5) == 0) {
			}
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}

	private String post(String uri, String data) throws IOException, InterruptedException {
		log.debug("post {} {}", uri, data);
		ContentExchange exchange = new ContentExchange(true);
		exchange.setMethod("POST");
		exchange.setURL(targetUrl + uri);
		exchange.setRequestContent(new ByteArrayBuffer(data));
		client.send(exchange);
		int status = exchange.waitForDone();
		if (status == HttpExchange.STATUS_COMPLETED) {
			return exchange.getResponseContent();
		}
		return null;
	}
}