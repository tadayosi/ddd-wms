package wms.domain.model.warehouse;

import java.util.List;

import wms.domain.model.Entry;

import com.google.common.collect.Lists;

public class WaitForShipment implements Location {
	private int capacity;
	private List<Entry> entries = Lists.newArrayList();

	public void addEntry(Entry entry) {
		if (available(entry.getAmmount())) {
			entries.add(entry);
		}
	}

	public boolean available(int ammount) {
		return ammount <= capacity;
	}
}
