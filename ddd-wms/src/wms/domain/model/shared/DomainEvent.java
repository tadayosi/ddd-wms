package wms.domain.model.shared;

public interface DomainEvent<T> {
	boolean sameEventAs(T other);
}
