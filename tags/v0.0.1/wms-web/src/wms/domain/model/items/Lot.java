package wms.domain.model.items;

import wms.domain.model.shared.Entity;
import wms.domain.model.warehouse.Location;
/**
 * ロット
 * @author kentaro
 */
public class Lot implements Entity<Lot> {

	private String id;
	private Item type;
	
	private Location location;

	public boolean sameIdentityAs(Lot other) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
