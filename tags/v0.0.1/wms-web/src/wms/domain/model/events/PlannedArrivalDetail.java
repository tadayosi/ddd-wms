package wms.domain.model.events;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import wms.domain.model.items.Item;

/**
 * 入荷予定明細
 * @author kentaro
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class PlannedArrivalDetail {
    @Persistent
	private int ammount;
    
    @Persistent
	private Item type;
	
	public PlannedArrivalDetail(int ammount, Item type) {
		this.ammount = ammount;
		this.type = type;
	}
	
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
