package wms.domain.model.warehouse;

import wms.domain.model.basics.Size;
import wms.domain.model.events.Transfer;


public abstract class Location {

	protected InitialInventory initialInventory;
	
	public abstract void addEntry(Transfer entry);
	
	public abstract boolean available(Size size, int ammount);
}
