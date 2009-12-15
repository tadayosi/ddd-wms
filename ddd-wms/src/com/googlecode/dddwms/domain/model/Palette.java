package com.googlecode.dddwms.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * パレット.
 * 
 * @author kentaro
 * 
 */
@Deprecated
public class Palette {

    // 物品
    private List<Item> items = new ArrayList<Item>();

    /**
     * 物品を載せる
     * 
     * @param item
     */
    public void palletize(Item item) {
        items.add(item);
    }

    /**
     * 物品が乗っているかを判定する。
     * 
     * @return 載っていなければtrue
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

}
