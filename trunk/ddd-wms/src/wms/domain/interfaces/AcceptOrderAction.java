package wms.domain.interfaces;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import wms.domain.model.cargo.CargoType;
import wms.domain.model.cargo.CargoType.CargoSize;
import wms.domain.model.order.OrderId;
import wms.domain.model.order.StockOrder;
import wms.domain.model.order.StockOrderDetail;
import wms.domain.model.order.StockOrderRepository;

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
	private StockOrderRepository repository;

	public OrderId process() {
		final StockOrder order = new StockOrder();
		order.setEat(eat);
		
		Function<StockOrderCommand, StockOrderDetail> func = new Function<StockOrderCommand, StockOrderDetail>() {
			public StockOrderDetail apply(StockOrderCommand command) {
				CargoSize size = CargoSize.findValueFor(command.size);
				if (size == null) return null;
				StockOrderDetail detail = new StockOrderDetail();
				CargoType type = new CargoType(size);
				detail.setType(type);
				detail.setAmmount(command.ammount);
				
				return detail;
			}
		};

		for(StockOrderDetail detail : Iterables.transform(orders, func)) {
			order.addDetail(detail);
		}
		repository.save(order);
		return order.getId();
	}

	public void setRepository(StockOrderRepository repository) {
		this.repository = repository;
	}

}
