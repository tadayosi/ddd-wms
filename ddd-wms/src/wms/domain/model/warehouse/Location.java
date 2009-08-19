package wms.domain.model.warehouse;

import wms.domain.model.Entry;


public interface Location {

	void addEntry(Entry entry);
	
	boolean available(int ammount);
}
