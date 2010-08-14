package com.googlecode.dddwms.domain.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.domain.model.ArrivalRequest;

public class ArrivalRequestRepository {

    private static ArrivalRequestRepository _instance = new ArrivalRequestRepository();

    private Logger log = LoggerFactory
            .getLogger(ArrivalRequestRepository.class);

    private AtomicLong arrivalId = new AtomicLong();

    private Map<Long, ArrivalRequest> requests = new HashMap<Long, ArrivalRequest>();

    public static ArrivalRequestRepository getInstance() {
        return _instance;
    }

    public ArrivalRequest forId(long id) {
        return requests.get(Long.valueOf(id));
    }

    public long nextId() {
        return arrivalId.incrementAndGet();
    }

    public void add(ArrivalRequest request) {
        log.info("ArrivalRequest added:{}", request.id());
        requests.put(Long.valueOf(request.id()), request);
    }

    // TODO for UT
    public void init() {
        requests.clear();
    }

    public boolean exists(long id) {
        return requests.containsKey(Long.valueOf(id));
    }

}
