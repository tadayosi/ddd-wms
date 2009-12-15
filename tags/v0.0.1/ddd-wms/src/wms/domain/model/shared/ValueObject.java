package wms.domain.model.shared;

public interface ValueObject<T> {
	  boolean sameValueAs(T other);
}
