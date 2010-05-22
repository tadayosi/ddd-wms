package com.googlecode.dddwms.emulator.servlet;

import javax.servlet.http.HttpServletRequest;

public class WebSocketServlet extends org.eclipse.jetty.websocket.WebSocketServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected WebSocket doWebSocketConnect(HttpServletRequest request, String arg1) {
		return new WebSocket();
	}
}
