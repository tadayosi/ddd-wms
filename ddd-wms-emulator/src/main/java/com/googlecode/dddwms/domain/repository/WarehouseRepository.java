package com.googlecode.dddwms.domain.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.domain.model.Warehouse;

public class WarehouseRepository {

	private static WarehouseRepository _instance = new WarehouseRepository();

	private Logger log = LoggerFactory.getLogger(WarehouseRepository.class);

	private Warehouse _warehouse = new Warehouse();

	public static WarehouseRepository getInstance() {
		return _instance;
	}

	public Warehouse get() {
		return _warehouse;
	}

	public void set(Warehouse warehouse) {
		log.info("warehouse updated - inside:{}", warehouse.items());
		_warehouse = warehouse;
	}

}
