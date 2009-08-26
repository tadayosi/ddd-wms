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
public class PlannedArrivalRepositoryInMemory implements PlannedArrivalRepository {

	private Set<PlannedArrival> orders = Sets.newHashSet();

	private long currentId = 0;
	
	public void save(PlannedArrival order) {
		// とりあえず、IDはここで割り当ててみる
		// 超適当、同期化もしてない
		order.setId(currentId++);
		orders.add(order);
	}

}
