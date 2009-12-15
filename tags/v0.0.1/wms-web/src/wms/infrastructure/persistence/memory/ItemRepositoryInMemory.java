package wms.infrastructure.persistence.memory;

import java.util.Map;

import com.google.common.collect.Maps;

import wms.domain.model.basics.Size;
import wms.domain.model.items.Item;
import wms.domain.model.items.ItemRepository;

public class ItemRepositoryInMemory implements ItemRepository {

	private Map<String, Item> items = Maps.newHashMap();
	
	public ItemRepositoryInMemory() {
		items.put("item001", new Item("item001", Size.L));
		items.put("item002", new Item("item002", Size.M));
		items.put("item003", new Item("item003", Size.S));
	}
	
	public Item findBy(String id) {
		return items.get(id);
	}

}
