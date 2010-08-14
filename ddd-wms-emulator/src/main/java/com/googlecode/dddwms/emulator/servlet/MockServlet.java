package com.googlecode.dddwms.emulator.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.dddwms.action.WmsAction;

public class MockServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private WmsAction action = new WmsAction();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // read message
        String message = messageIn(req);

        String pathInfo = req.getPathInfo();
        if (pathInfo.matches("/arrivalRequest")) {

            handleArrivalRequest(resp, message);

        } else if (pathInfo.matches("/arrival")) {

            handleArrival(message);

        } else if (pathInfo.matches("/shippingRequest")) {

            handleShippingRequest(resp, message);

        } else if (pathInfo.matches("/ship")) {

            handleShip(resp);

        } else {
            resp.setStatus(404);
        }
    }

    private void handleArrivalRequest(HttpServletResponse resp, String message)
            throws IOException {
        long arrivalId = action.arrivalRequest(message);

        resp.setContentType("text/plain");
        resp.getWriter().printf("{\"id\":%d}", arrivalId);
    }

    private void handleArrival(String message) {
        action.arrival(message);
    }

    private void handleShippingRequest(HttpServletResponse resp, String message)
            throws IOException {
        long shippingId = action.shippingRequest(message);

        resp.setContentType("text/plain");
        resp.getWriter().printf("{\"id\":%d}", shippingId);
    }

    private void handleShip(HttpServletResponse resp) throws IOException {

        List<Long> shippedIds = action.ship();

        StringBuilder sb = new StringBuilder();
        for (long id : shippedIds) {
            sb.append("{\"id\":");
            sb.append(id);
            sb.append("},");
        }
        String messageContent = "";
        if (shippedIds != null && shippedIds.size() > 0) {
            messageContent = sb.substring(0, sb.lastIndexOf(","));
        }

        resp.setContentType("text/plain");
        resp.getWriter().print("{\"items\":[");
        resp.getWriter().print(messageContent);
        resp.getWriter().print("]}");
    }

    private static String messageIn(HttpServletRequest req) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(
                req.getInputStream()));
        String message = r.readLine();

        // assert
        String nextLine = r.readLine();
        if (nextLine != null) {
            throw new IllegalArgumentException("unexpected message:" + nextLine);
        }
        return message;
    }
}
