package com.googlecode.dddwms.domain.model;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 倉庫を表すクラス。
 * @author kentaro
 *
 */
public class Warehouse {

    Map<Item, Integer> items = Maps.newHashMap();

    public boolean empty() {
        return items.isEmpty();
    }

    public void store(Item item) {
        if (items.containsKey(item)) {
            add(item, 1);
        } else {
            items.put(item, 1);
        }
    }

    public void take(Item item) {
        if (!items.containsKey(item)) {
            throw new RuntimeException("Thare is no specified item.");
        }
        if (containsOnlyLessThan(item, 1)) {
            throw new RuntimeException("Thare are fewer items than specified.");
        }
        remove(item, 1);
    }

    private void remove(Item item, int amount) {
        int afterRemoval = items.get(item) - amount;
        if (afterRemoval == 0) {
            items.remove(item);
        } else {
            items.put(item, afterRemoval);
        }
    }

    private boolean containsOnlyLessThan(Item item, int amount) {
        return items.get(item) < amount;
    }

    private void add(Item item, int amount) {
        items.put(item, items.get(item) + amount);
    }
}
