package wms.domain.model.warehouse;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import wms.domain.model.Entry;
import wms.domain.model.cargo.CargoType.CargoSize;

public class Space implements Location {
	private CargoSize size;
	private int capacity;
	private List<Entry> entries = Lists.newArrayList();
	
	public Space(CargoSize size, int capacity) {
		this.size = size;
		this.capacity = capacity;
	}
	
	public boolean available(int ammount) {
		Function<Entry, Integer> sumForSize = new Function<Entry, Integer>() {
			public Integer apply(Entry entry) {
				if (entry.getCargoType().getSize() == size) {
					return entry.getAmmount();
				} else {
					return 0;
				}
			}
		};
		
		int total = 0;
		for (int amt : Iterables.transform(entries, sumForSize)) {
			total += amt;
		}

		return (total + ammount) <= capacity;
	}

	public void addEntry(Entry entry) {
		if (available(entry.getAmmount())) {
			entries.add(entry);
		}
	}
}
