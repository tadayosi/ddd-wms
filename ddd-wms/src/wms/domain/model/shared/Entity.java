package wms.domain.model.shared;

public interface Entity<T> {
	  boolean sameIdentityAs(T other);
}
