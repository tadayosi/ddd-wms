package com.googlecode.dddwms.domain.model;

import junit.framework.Assert;

import org.junit.Test;

import com.googlecode.dddwms.domain.model.Item;
import com.googlecode.dddwms.domain.model.Palette;

/**
 * 
 * @author kentaro
 *
 */
@Deprecated
public class PaletteTest {
	
	/**
	 * パレットを新規に作る。
	 */
	@Test
	public void shouldBeEmptyWhenPaletteIsCreated() {
		Palette palette = new Palette();
		Assert.assertTrue("パレットは空",palette.isEmpty());
	}
	
	/**
	 * パレットに物品を載せる。
	 */
	@Test
	public void shouldPalletizeItem() {
		Palette palette = new Palette();
		
		Item bottledWalter = new Item();
		bottledWalter.setName("エビアン");
		
		palette.palletize(bottledWalter);
		Assert.assertFalse("パレットは空ではない",palette.isEmpty());
	}
}
