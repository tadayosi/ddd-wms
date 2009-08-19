package wms.domain.model.events;

import wms.domain.model.cargo.Cargo;

/**
 * 検査結果
 * @author kentaro
 *
 */
public class InspectionDetail {
	private Cargo target;
	// 実際は詳細情報が必要なはず
	private boolean result;
	
	public Cargo getTarget() {
		return target;
	}
	public void setTarget(Cargo target) {
		this.target = target;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
}
