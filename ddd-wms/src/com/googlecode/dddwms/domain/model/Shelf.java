package com.googlecode.dddwms.domain.model;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Shelf {

	private List<Item> items = new ArrayList<Item>();
	
	public boolean isEmpty() {
		return items.isEmpty();
	}

	public void stock(Item item) {
		items.add(item);
	}

}
