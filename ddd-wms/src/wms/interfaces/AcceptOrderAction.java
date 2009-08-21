package wms.interfaces;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import wms.domain.model.basics.Size;
import wms.domain.model.events.PlannedArrival;
import wms.domain.model.events.PlannedArrivalDetail;
import wms.domain.model.events.PlannedArrivalRepository;
import wms.domain.model.items.Item;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * 入荷予定受付をハンドリングするアクション。
 * インスタンス変数にリクエスト内容が格納されると仮想。
 * @author kentaro
 *
 */
public class AcceptOrderAction {

	public List<StockOrderCommand> orders;
	public Date eat;
	@Resource
	private PlannedArrivalRepository repository;

	public String process() {
		final PlannedArrival order = new PlannedArrival();
		order.setEat(eat);
		
		Function<StockOrderCommand, PlannedArrivalDetail> func = new Function<StockOrderCommand, PlannedArrivalDetail>() {
			public PlannedArrivalDetail apply(StockOrderCommand command) {
				Size size = Size.findValueFor(command.size);
				if (size == null) return null;
				PlannedArrivalDetail detail = new PlannedArrivalDetail();
				Item type = new Item(size);
				detail.setType(type);
				detail.setAmmount(command.ammount);
				
				return detail;
			}
		};

		for(PlannedArrivalDetail detail : Iterables.transform(orders, func)) {
			order.addDetail(detail);
		}
		repository.save(order);
		return order.getId();
	}

	public void setRepository(PlannedArrivalRepository repository) {
		this.repository = repository;
	}

}
