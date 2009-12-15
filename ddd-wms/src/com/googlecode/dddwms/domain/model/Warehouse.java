package com.googlecode.dddwms.domain.model;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class Warehouse {
	
	Multiset<Item> items = HashMultiset.create();
	
	public boolean empty() {
		return items.isEmpty();
	}

	public void store(Item item) {
		items.add(item);
	}

}
