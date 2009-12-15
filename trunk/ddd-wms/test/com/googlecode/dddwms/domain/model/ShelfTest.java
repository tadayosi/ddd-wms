package com.googlecode.dddwms.domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.dddwms.domain.model.Item;
import com.googlecode.dddwms.domain.model.Shelf;

import junit.framework.Assert;

/**
 * 棚の単体テストケース
 * @author kentaro
 *
 */
@Deprecated
public class ShelfTest {

	/**
	 * 最初は空
	 */
	@Test
	public void shouldBeEmptyWhenCreated(){
		Shelf shelf = new Shelf();
		Assert.assertTrue("最初は空",shelf.isEmpty());
	}
	
	/**
	 * 棚に物品を載せる
	 */
	@Test
	public void shouldStockItem() throws Exception {
		Shelf shelf = new Shelf();
		Item bottledWater = new Item();
		bottledWater.setName("エビアン12本入り");
		shelf.stock(bottledWater);
		Assert.assertFalse("空ではない",shelf.isEmpty());
	}
	
	/**
	 * 棚から物品を取り出す
	 */
	@Test
	public void shouldTakeItemOffTheShelf() throws Exception {
		
		// 棚に載せておく
		Shelf shelf = new Shelf();
		Item bottledWater = new Item();
		bottledWater.setName("エビアン12本入り");
		shelf.stock(bottledWater);
		
		// 取り出す
		
		
	}
}
