package wms.domain.model.events;

import wms.domain.model.items.Lot;

/**
 * 検品明細
 * @author kentaro
 */
public class InspectionDetail {
	private Lot target;
	// 実際は詳細情報が必要なはず
	private boolean result;
	
	public Lot getTarget() {
		return target;
	}
	public void setTarget(Lot target) {
		this.target = target;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
}
