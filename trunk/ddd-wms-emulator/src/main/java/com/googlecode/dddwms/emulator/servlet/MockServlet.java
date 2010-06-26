package com.googlecode.dddwms.emulator.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.dddwms.action.WmsAction;

public class MockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AtomicLong shippingId = new AtomicLong();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// read message
		BufferedReader r = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String message = r.readLine();
		
		// assert
		String nextLine = r.readLine();
		if ( nextLine != null) {
			throw new IllegalArgumentException("unexpected message:" + nextLine);
		}
		
		WmsAction action = new WmsAction();
		
		String pathInfo = req.getPathInfo();
		if (pathInfo.matches("/arrivalRequest")) {
			
			long arrivalId = action.arrivalRequest(message);
			
			resp.setContentType("text/plain");
			resp.getWriter().printf("{\"id\":%d}", arrivalId);
			
		} else if (pathInfo.matches("/arrival")) {
			
			action.arrival(message);
		
		} else if (pathInfo.matches("/shippingRequest")) {
			
			action.shippingRequest(message);
			
			resp.setContentType("text/plain");
			resp.getWriter().printf("{\"id\":%d}", shippingId.incrementAndGet());
		
		} else {
			resp.setStatus(404);
		}
	}
}
