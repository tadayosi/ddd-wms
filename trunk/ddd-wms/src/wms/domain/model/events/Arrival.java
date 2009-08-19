package wms.domain.model.events;

import java.util.Date;
import java.util.List;

import wms.domain.model.cargo.Cargo;
import wms.domain.model.order.StockOrder;

import com.google.common.collect.Lists;

public class Arrival {
	// めんどくさくなってしまった
	// StockOrderの子の中で一意
	private int id;
	private List<Cargo> cargoes = Lists.newArrayList();
	
	// 時分秒以下不要、適切な型に要変更
	private Date actualArrivalTime;
	
	// 関連の方向はこっち向きでOKなのか？
	private StockOrder prior;
	
	public void addCargo(Cargo cargo) {
		cargoes.add(cargo);
	}
}
