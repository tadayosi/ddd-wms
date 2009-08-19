package wms.infrastructure.persistence.memory;

import java.util.Set;

import com.google.common.collect.Sets;

import wms.domain.model.order.OrderId;
import wms.domain.model.order.StockOrder;
import wms.domain.model.order.StockOrderRepository;

/**
 * Repositoryのメモリ保存実装。
 * DIコンテナでSingletonになることを期待して何もやってない。
 * @author kentaro
 *
 */
public class StockOrderRepositoryOnMemory implements StockOrderRepository {

	private Set<StockOrder> orders = Sets.newHashSet();

	private int currentId = 0;
	
	public void save(StockOrder order) {
		// とりあえず、IDはここで割り当ててみる
		// 超適当、同期化もしてない
		order.setId(new OrderId(String.valueOf(currentId++)));
		orders.add(order);
	}

}
