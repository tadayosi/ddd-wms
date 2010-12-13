package com.googlecode.dddwms.domain.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.dddwms.domain.service.Item;

public class ShippingRequest {

    private final long _id;

    private final Date _time;

    private Map<Item, Integer> items = new HashMap<Item, Integer>();

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

    public int amountOf(Item item) {
        return items.get(item).intValue();
    }

    public void putAmount(Item item, int amount) {
        items.put(item, Integer.valueOf(amount));
    }

    public ShippingRequestStatus status() {
        return _status;
    }

    public Map<Item, Integer> amounts() {
        // Defensive copy
        return new HashMap<Item, Integer>(items);
    }

    public void shipped() {
        _status = ShippingRequestStatus.SHIPPED;
    }

    public void shipFrom(Warehouse warehouse) {
        for (Item key : items.keySet()) {
            warehouse.ship(key, items.get(key));
        }
        shipped();
    }

}
