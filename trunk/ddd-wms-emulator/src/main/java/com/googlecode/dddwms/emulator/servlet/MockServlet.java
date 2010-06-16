package com.googlecode.dddwms.emulator.servlet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AtomicLong arrivalId = new AtomicLong();
	private AtomicLong shippingId = new AtomicLong();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo.matches("/arrivalRequest")) {
			resp.setContentType("text/plain");
			resp.getWriter().printf("{\"id\":%d}", arrivalId.incrementAndGet());
		} else if (pathInfo.matches("/arrival")) {
		} else if (pathInfo.matches("/shippingRequest")) {
			resp.setContentType("text/plain");
			resp.getWriter().printf("{\"id\":%d}", shippingId.incrementAndGet());
		} else {
			resp.setStatus(404);
		}
	}
}
