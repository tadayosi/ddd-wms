package wms.domain.model.warehouse;

import java.util.List;

import com.google.common.collect.Lists;

import wms.domain.model.Entry;

public class WaitForAcceptance implements Location {
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
