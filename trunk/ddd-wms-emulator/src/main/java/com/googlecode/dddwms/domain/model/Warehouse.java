package com.googlecode.dddwms.domain.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.domain.service.Item;

public class Warehouse {

    private Logger log = LoggerFactory.getLogger(Warehouse.class);

    private Map<Item, Integer> items = new HashMap<Item, Integer>();

    public int count(Item item) {
        return items.containsKey(item) ? Integer.valueOf(items.get(item)) : 0;
    }

    public void arrive(Item item, int amount) {

        int currentAmount = 0;
        if (items.containsKey(item)) {
            currentAmount = items.get(item).intValue();
        }

        int newAmount = currentAmount + amount;
        items.put(item, Integer.valueOf(newAmount));

        log.info("item arrived - item:{} amount:{}", item.id(), currentAmount
                + " -> " + newAmount);
    }

    public void ship(Item item, int amount) {

        // item not found
        if (!items.containsKey(item)) {
            log.error("item not found:{}", item.id());
            return;
        }

        int currentAmount = items.get(item).intValue();

        // item out of stock
        if (currentAmount < amount) {
            log.error(
                    "item out of stock - current amount:{} / order amount:{}",
                    currentAmount, amount);
            return;
        }

        int newAmount = currentAmount - amount;
        items.put(item, Integer.valueOf(newAmount));

        log.info("item shipped - item:{} amount:{}", item.id(), currentAmount + " -> "
                + newAmount);
    }

    public Map<Item, Integer> items() {
        return new HashMap<Item, Integer>(items);
    }

}
