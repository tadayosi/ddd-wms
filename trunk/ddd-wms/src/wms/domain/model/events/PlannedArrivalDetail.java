package wms.domain.model.events;

import wms.domain.model.items.Item;

/**
 * 入荷予定明細
 * @author kentaro
 */
public class PlannedArrivalDetail {
	private int ammount;
	private Item type;
	
	public int getAmmount() {
		return ammount;
	}
	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}
	public Item getType() {
		return type;
	}
	public void setType(Item type) {
		this.type = type;
	}
	
	
}
