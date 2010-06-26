package com.googlecode.dddwms.domain.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ArrivalRequest {
	
	private final long _id;
	
	private final Date _time;
	
	private Map<Long, Integer> amounts = new HashMap<Long, Integer>();
	
	private ArrivalRequestStatus _status;
	
	public ArrivalRequest(long id, Date time) {
		_id = id;
		_time = time;
		_status = ArrivalRequestStatus.WAIT_FOR_ARRIVAL;
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

	public ArrivalRequestStatus status() {
		return _status;
	}
	
	public Map<Long, Integer> amounts() {
		// diffencive copy
		return new HashMap<Long, Integer>(amounts);
	}

	public void arrived() {
		_status = ArrivalRequestStatus.ARRIVED;
	}

}
