package com.googlecode.dddwms.emulator.servlet;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocket implements org.eclipse.jetty.websocket.WebSocket {
	private static final Logger log = LoggerFactory.getLogger(WebSocketServlet.class);
	protected static Set<WebSocket> members = new CopyOnWriteArraySet<WebSocket>();
	protected Outbound outbound;

	@Override
	public void onConnect(Outbound outbound) {
		log.debug("{}:onConnect", this.hashCode());
		this.outbound = outbound;
		members.add(this);
	}

	@Override
	public void onDisconnect() {
		log.debug("{}:onDisconnect", this.hashCode());
		members.remove(this);
	}

	@Override
	public void onMessage(byte frame, String message) {
		log.debug("{}:onMessage {}", this.hashCode(), message);
	}
	
	@Override
	public void onMessage(byte frame, byte[] data, int offset, int length) {
	}

	public void sendMessage(String format, Object... args) {
		String message = String.format(format, args);
		for (WebSocket member : members) {
			try {
				log.debug("{}:sendMessage {}", member.hashCode(), message);
				member.outbound.sendMessage(SENTINEL_FRAME, message);
			} catch (IOException e) {
				log.debug(e.getMessage(), e);
				members.remove(member);
			}
		}
	}
}
