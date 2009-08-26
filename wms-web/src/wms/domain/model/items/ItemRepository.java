package wms.domain.model.items;

public interface ItemRepository {
	Item findBy(String id);
}
