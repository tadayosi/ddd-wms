package wms.domain.model.events;

import wms.domain.model.items.Item;

/**
 * 移動
 * AccountパターンのEntry
 * @author kentaro
 */
public class Transfer {
	private int ammount;
	private Item item;

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
