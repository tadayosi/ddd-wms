package wms.domain.model.events;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 検査
 * @author kentaro
 *
 */
public class Inspection {
	// 必要か？ヒアリングの結果次第
	private int id;
	private List<InspectionDetail> details = Lists.newArrayList();
	private Arrival prior;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<InspectionDetail> getDetails() {
		return details;
	}
	public void setDetails(List<InspectionDetail> details) {
		this.details = details;
	}
	public Arrival getPrior() {
		return prior;
	}
	public void setPrior(Arrival prior) {
		this.prior = prior;
	}
}
