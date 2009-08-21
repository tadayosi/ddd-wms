package wms.domain.model.items;

import wms.domain.model.basics.Size;
import wms.domain.model.shared.Entity;

/**
 * 物品
 * @author kentaro
 */
public class Item implements Entity<Item> {

	private String id;
	private Size size;
	
	public Item() {}
	public Item(Size size) {
		this.size = size;
	}
	
	public Size getSize() {
		return size;
	}
	public boolean sameIdentityAs(Item other) {
		// TODO Auto-generated method stub
		return false;
	}
}
