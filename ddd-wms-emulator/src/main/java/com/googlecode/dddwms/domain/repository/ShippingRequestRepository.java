package com.googlecode.dddwms.domain.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.domain.model.ShippingRequest;
import com.googlecode.dddwms.util.Predicate;

public class ShippingRequestRepository {
	
	private static ShippingRequestRepository _instance = new ShippingRequestRepository();

	private Logger log = LoggerFactory.getLogger(ShippingRequestRepository.class);

	private AtomicLong shippingId = new AtomicLong();

	private Map<Long, ShippingRequest> requests = new HashMap<Long, ShippingRequest>();

	public static ShippingRequestRepository getInstance() {
		return _instance;
	}

	public ShippingRequest forId(long id) {
		return requests.get(Long.valueOf(id));
	}

	public long nextId() {
		return shippingId.incrementAndGet();
	}

	public void add(ShippingRequest request) {
		log.info("ShippingRequest added:{}", request.id());
		requests.put(Long.valueOf(request.id()), request);
	}

	// TODO for UT
	public void init() {
		requests.clear();
	}

	public boolean exists(long id) {
		return requests.containsKey(Long.valueOf(id));
	}
	
	public Set<ShippingRequest> filter(Predicate<ShippingRequest> criteria) {
	    Set<ShippingRequest> resultSet = new HashSet<ShippingRequest>();
	    for (ShippingRequest request : requests.values()) {
            if (criteria.apply(request)) {
                resultSet.add(request);
            }
        }
	    return resultSet;
	}

}
