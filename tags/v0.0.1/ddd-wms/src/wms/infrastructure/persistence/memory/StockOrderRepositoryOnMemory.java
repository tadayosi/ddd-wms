package wms.infrastructure.persistence.memory;

import java.util.Set;

import wms.domain.model.events.PlannedArrival;
import wms.domain.model.events.PlannedArrivalRepository;

import com.google.common.collect.Sets;

/**
 * Repositoryのメモリ保存実装。
 * DIコンテナでSingletonになることを期待して何もやってない。
 * @author kentaro
 *
 */
public class StockOrderRepositoryOnMemory implements PlannedArrivalRepository {

	private Set<PlannedArrival> orders = Sets.newHashSet();

	private int currentId = 0;
	
	public void save(PlannedArrival order) {
		// とりあえず、IDはここで割り当ててみる
		// 超適当、同期化もしてない
		order.setId(String.valueOf(currentId++));
		orders.add(order);
	}

}
