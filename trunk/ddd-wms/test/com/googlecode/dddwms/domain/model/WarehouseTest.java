package com.googlecode.dddwms.domain.model;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.googlecode.dddwms.domain.model.Warehouse;


/**
 * 倉庫の単体テストケース
 * @author kentaro
 *
 */
public class WarehouseTest {
	
	/**
	 * 倉庫は最初は空
	 * @throws Exception
	 */
	@Test
	public void shouldBeEmptyWhenCreated() throws Exception {
		Warehouse warehouse = new Warehouse();
		Assert.assertTrue("最初は空",warehouse.isEmpty());
	}
	
}
