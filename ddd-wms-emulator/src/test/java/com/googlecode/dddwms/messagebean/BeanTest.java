package com.googlecode.dddwms.messagebean;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.arnx.jsonic.JSON;

import org.junit.Test;

public class BeanTest {
	
	@Test
	public void testArrivalRequest() throws Exception {
		
		Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse("2010/06/25 10:50:51.111");
		
		String jsonMessage = "{\"time\":" + date.getTime() + ",\"items\":[{\"amount\":3,\"id\":4},{\"amount\":2,\"id\":3},{\"amount\":5,\"id\":4}]}";
		ArrivalRequestMessageBean bean = JSON.decode(jsonMessage, ArrivalRequestMessageBean.class);
		
		// time
		assertEquals(date, bean.time);
		
		// item1
		ArrivalItemsMessageBean items1 = bean.items.get(0);
		assertEquals(4,items1.id);
		assertEquals(3,items1.amount);
		
	}

}
