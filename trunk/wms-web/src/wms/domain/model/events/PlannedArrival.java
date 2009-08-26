package wms.domain.model.events;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.common.collect.Lists;

/**
 * 入荷予定
 * @author kentaro
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PlannedArrival {
	private List<PlannedArrivalDetail> details = Lists.newArrayList();
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	// estimated arrival time
	// Dateじゃまずい気もするが、とりあえず気にしない
	@Persistent
	private Date eat;
	
	public List<PlannedArrivalDetail> getDetails() {
		return Collections.unmodifiableList(details);
	}
	
	public void addDetail(PlannedArrivalDetail detail) {
		details.add(detail);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getEat() {
		return eat;
	}

	public void setEat(Date eat) {
		this.eat = eat;
	}
}
