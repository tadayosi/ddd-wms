package wms.domain.model.events;

import java.util.Date;
import java.util.List;

import wms.domain.model.items.Lot;

import com.google.common.collect.Lists;

/**
 * 入荷/入荷実績
 * @author kentaro
 */
public class ActualArrival {
	// めんどくさくなってしまった
	// StockOrderの子の中で一意
	private int id;
	private List<Lot> lots = Lists.newArrayList();
	
	// 時分秒以下不要、適切な型に要変更
	private Date actualArrivalTime;
	
	// 関連の方向はこっち向きでOKなのか？
	private PlannedArrival prior;
	
	public void addCargo(Lot cargo) {
		lots.add(cargo);
	}
}
