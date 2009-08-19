package wms.domain.model.cargo;

import wms.domain.model.shared.Entity;
import wms.domain.model.warehouse.Location;
/**
 * 物品
 * @author kentaro
 *
 */
public class Cargo implements Entity<Cargo> {

	private String id;
	private State state = State.ACCEPTED;
	private CargoType type;
	
	// このあたりなんか不自然
	private Location location;

	public void markInvalid() {
		this.state = State.INVALID;
	}
	
	public void markValid() {
		state = State.VALID;
	}
	
	public boolean isValid() {
		return state != State.INVALID && state != State.ACCEPTED;
	}
	
	public boolean sameIdentityAs(Cargo other) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	static enum State {
		ACCEPTED, VALID, INVALID, IN_STORAGE, SHIPPING, SHIPPED
	}
}
