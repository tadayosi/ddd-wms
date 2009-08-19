package wms.domain.model.warehouse;

import java.util.HashSet;
import java.util.Set;

import wms.domain.model.shared.Entity;

/**
 * Aggregate Root?
 * @author kentaro
 *
 */
public class Warehouse implements Entity<Warehouse> {
	private String id;
	private Set<Shelf> shelves = new HashSet<Shelf>();

	public void addShelf(Shelf shelf) {
		shelves.add(shelf);
	}
	
	public boolean sameIdentityAs(Warehouse other) {
		return this.id.equals(other.id);
	}

}
