package wms.interfaces;

import java.util.List;

/**
 * 入荷受付を処理するアクション。
 * @author kentaro
 *
 */
public class HandleArrivalAction {
	
	public String reserveId;
	public List<ArrivalCommand> commands;
	
	public void process() {
		
	}
}
