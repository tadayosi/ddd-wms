package wms.domain.model.events;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 入荷予定
 * @author kentaro
 */
public class PlannedArrival {
	private List<PlannedArrivalDetail> details = Lists.newArrayList();
	private String id;
	// estimated arrival time
	// Dateじゃまずい気もするが、とりあえず気にしない
	private Date eat;
	
	public List<PlannedArrivalDetail> getDetails() {
		return Collections.unmodifiableList(details);
	}
	
	public void addDetail(PlannedArrivalDetail detail) {
		details.add(detail);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getEat() {
		return eat;
	}

	public void setEat(Date eat) {
		this.eat = eat;
	}
}
