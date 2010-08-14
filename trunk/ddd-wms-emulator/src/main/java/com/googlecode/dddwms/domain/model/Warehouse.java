package com.googlecode.dddwms.domain.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Warehouse {

    private Logger log = LoggerFactory.getLogger(Warehouse.class);

    private Map<Long, Integer> items = new HashMap<Long, Integer>();

    public int count(long itemId) {
        Long key = keyFor(itemId);
        return items.containsKey(key) ? Integer.valueOf(items.get(key)) : 0;
    }

    public void arrive(long itemId, int amount) {

        Long key = keyFor(itemId);

        int currentAmount = 0;
        if (items.containsKey(key)) {
            currentAmount = items.get(key).intValue();
        }

        int newAmount = currentAmount + amount;
        items.put(key, Integer.valueOf(newAmount));

        log.info("item arrived - item:{} amount:{}", itemId, currentAmount
                + " -> " + newAmount);
    }

    public void ship(long id, int amount) {
        Long key = keyFor(id);

        // item not found
        if (!items.containsKey(key)) {
            log.error("item not found:{}", id);
            return;
        }

        int currentAmount = items.get(key).intValue();

        // item out of stock
        if (currentAmount < amount) {
            log.error(
                    "item out of stock - current amount:{} / order amount:{}",
                    currentAmount, amount);
            return;
        }

        int newAmount = currentAmount - amount;
        items.put(key, Integer.valueOf(newAmount));

        log.info("item shipped - item:{} amount:{}", id, currentAmount + " -> "
                + newAmount);
    }

    public Map<Long, Integer> items() {
        return new HashMap<Long, Integer>(items);
    }

    private static Long keyFor(long itemId) {
        return Long.valueOf(itemId);
    }

}
