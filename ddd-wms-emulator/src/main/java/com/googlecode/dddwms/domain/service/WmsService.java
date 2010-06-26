package com.googlecode.dddwms.domain.service;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.domain.model.ArrivalRequest;
import com.googlecode.dddwms.domain.model.ArrivalRequestStatus;
import com.googlecode.dddwms.domain.model.Warehouse;
import com.googlecode.dddwms.domain.repository.ArrivalRequestRepository;
import com.googlecode.dddwms.domain.repository.WarehouseRepository;
import com.googlecode.dddwms.messagebean.ArrivalItemsMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalRequestMessageBean;
import com.googlecode.dddwms.messagebean.ShippingRequestMessageBean;

public class WmsService {

	private ArrivalRequestRepository arrivalRequestRepository = ArrivalRequestRepository.getInstance();

	private WarehouseRepository warehouseRepository = WarehouseRepository.getInstance();

	private Logger log = LoggerFactory.getLogger(WmsService.class);

	public long handleArrivalRequest(ArrivalRequestMessageBean message) {

		long arrivalId = arrivalRequestRepository.nextId();

		ArrivalRequest request = new ArrivalRequest(arrivalId, message.time);

		for (ArrivalItemsMessageBean item : message.items) {
			request.putAmount(item.id, item.amount);
		}

		arrivalRequestRepository.add(request);

		return arrivalId;
	}

	public void handleArrival(ArrivalMessageBean message) {

		// TODO テストケース未実装
		if (!arrivalRequestRepository.exists(message.id)) {
			log.error("arrival request not found:{}", message.id);
			return;
		}

		ArrivalRequest request = arrivalRequestRepository.forId(message.id);

		// TODO テストケース未実装
		if (request.status() == ArrivalRequestStatus.ARRIVED) {
			log.error("already arrived:{}", message.id);
			return;
		}

		Warehouse warehouse = warehouseRepository.get();
		for (Entry<Long, Integer> entry : request.amounts().entrySet()) {
			long itemId = entry.getKey().longValue();
			int amount = entry.getValue().intValue();
			warehouse.arrive(itemId, amount);
		}

		warehouseRepository.set(warehouse);
		request.arrived();
	}

	public void handleShippingRequest(ShippingRequestMessageBean message) {

		Warehouse warehouse = warehouseRepository.get();

		for (ArrivalItemsMessageBean item : message.items) {
			warehouse.ship(item.id, item.amount);
		}
	}

}
