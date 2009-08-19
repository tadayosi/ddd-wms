package wms.domain.model.warehouse;

import java.util.Map;

import wms.domain.model.Entry;
import wms.domain.model.cargo.CargoType.CargoSize;
import wms.domain.model.shared.Entity;

import com.google.common.collect.Maps;

public class Shelf implements Location, Entity<Shelf>{
	private String id;
	private Map<CargoSize, Space> spaces = Maps.newHashMap();
	
	public Shelf() {}

	public void addSpace(CargoSize size, Space space) {
		spaces.put(size, space);
	}
	
	public boolean available(int ammount) {
		// TODO きちんと
		return true;
	}
	
	public void addEntry(Entry entry) {
		Space space = spaces.get(entry.getCargoType().getSize());
		if (space.available(entry.getAmmount())) {
			space.addEntry(entry);
		}		
	}
	
	public boolean sameIdentityAs(Shelf other) {
		return equals(other);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shelf other = (Shelf) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
