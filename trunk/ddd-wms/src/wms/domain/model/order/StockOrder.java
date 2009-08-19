package wms.domain.model.order;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class StockOrder {
	private List<StockOrderDetail> details = Lists.newArrayList();
	private OrderId id;
	// estimated arrival time
	// Dateじゃまずい気もするが、とりあえず気にしない
	private Date eat;
	
	public List<StockOrderDetail> getDetails() {
		return Collections.unmodifiableList(details);
	}
	
	public void addDetail(StockOrderDetail detail) {
		details.add(detail);
	}

	public OrderId getId() {
		return id;
	}

	public void setId(OrderId id) {
		this.id = id;
	}

	public Date getEat() {
		return eat;
	}

	public void setEat(Date eat) {
		this.eat = eat;
	}
}
