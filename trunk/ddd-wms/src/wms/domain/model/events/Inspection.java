package wms.domain.model.events;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 検品
 * @author kentaro
 */
public class Inspection {
	// 必要か？ヒアリングの結果次第
	private int id;
	private List<InspectionDetail> details = Lists.newArrayList();
	private ActualArrival prior;
	
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
	public ActualArrival getPrior() {
		return prior;
	}
	public void setPrior(ActualArrival prior) {
		this.prior = prior;
	}
}
