package com.googlecode.dddwms.emulator.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.googlecode.dddwms.action.WmsAction;

public class MockServletTest {

	/**
	 * GIVEN : shipがコールされた時、
	 * WHEN  : Actionからの戻り値が[111, 222]だった場合、
	 * THEN  : responseのメッセージは{"items":[{"id":111},{"id":222}]}になる
	 */
	@Test
	public void shouldHandleShip() throws Exception {

		/* MOCK */
		// WmsAction - result:[111, 222]
		List<Long> result = new ArrayList<Long>();
		result.add(111L);
		result.add(222L);
		WmsAction mockAction = mock(WmsAction.class);
		when(mockAction.ship()).thenReturn(result);

		// HttpServletRequest
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getPathInfo()).thenReturn("/ship");

		// InputStream
		ServletInputStream mockInputStream = new ServletInputStream() {
			@Override
			public int read() throws IOException {
				StringReader reader = new StringReader("");
				return reader.read();
			}
		};
		when(req.getInputStream()).thenReturn(mockInputStream);

		// HttpServletResponse
		StringWriter writer = new StringWriter();
		HttpServletResponse resp = mock(HttpServletResponse.class);
		when(resp.getWriter()).thenReturn(new PrintWriter(writer));

		/* EXECUTE */
		MockServlet servlet = mockServlet(mockAction);
		servlet.doPost(req, resp);

		/* VERIFY */
		assertEquals("{\"items\":[{\"id\":111},{\"id\":222]}", writer.toString());
	}

	private static MockServlet mockServlet(WmsAction mockAction) throws NoSuchFieldException, IllegalAccessException {
		MockServlet servlet = new MockServlet();
		Field action = servlet.getClass().getDeclaredField("action");
		action.setAccessible(true);
		action.set(servlet, mockAction);
		return servlet;
	}

}
