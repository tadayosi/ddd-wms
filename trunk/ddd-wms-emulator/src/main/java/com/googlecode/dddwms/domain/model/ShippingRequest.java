package com.googlecode.dddwms.domain.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShippingRequest {
	
	private final long _id;
	
	private final Date _time;
	
	private Map<Long, Integer> amounts = new HashMap<Long, Integer>();
	
	private ShippingRequestStatus _status;
	
	public ShippingRequest(long id, Date time) {
		_id = id;
		_time = time;
		_status = ShippingRequestStatus.WAIT_FOR_SHIPPING;
	}

	public long id() {
		return _id;
	}

	public Date time() {
		return _time;
	}

	public int amountOf(Long id) {
		return amounts.get(Long.valueOf(id)).intValue();
	}

	public void putAmount(Long id, int amount) {
		amounts.put(Long.valueOf(id), Integer.valueOf(amount));
	}

	public ShippingRequestStatus status() {
		return _status;
	}
	
	public Map<Long, Integer> amounts() {
		// diffencive copy
		return new HashMap<Long, Integer>(amounts);
	}

	public void shipped() {
		_status = ShippingRequestStatus.SHIPPED;
	}

    public void shipFrom(Warehouse warehouse) {
        for (Long key : amounts.keySet()) {
            warehouse.ship(key, amounts.get(key));
        }
        shipped();
    }


}
