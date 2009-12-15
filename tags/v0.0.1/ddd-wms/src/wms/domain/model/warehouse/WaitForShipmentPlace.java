package wms.domain.model.warehouse;

import java.util.List;

import wms.domain.model.basics.Size;
import wms.domain.model.events.Transfer;

import com.google.common.collect.Lists;

/**
 * 具体的なクラスである必要はあるか？
 * @author kentaro
 */
public class WaitForShipmentPlace extends Location {
	private int capacity;
	private List<Transfer> entries = Lists.newArrayList();

	public void addEntry(Transfer entry) {
		entries.add(entry);
	}

	@Override
	public boolean available(Size size, int ammount) {
		// TODO Auto-generated method stub
		return false;
	}
}
