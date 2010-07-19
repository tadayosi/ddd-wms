package com.googlecode.dddwms.lang;

import java.util.Arrays;
import java.util.Date;

import com.googlecode.dddwms.messagebean.ArrivalItemsMessageBean;
import com.googlecode.dddwms.messagebean.ShippingItemsMessageBean;
import com.googlecode.dddwms.messagebean.ShippingRequestMessageBean;

public class ShippingRequestMessageBuilder {
    public static ShippingRequestMessageBean newRequest(Date date, ShippingItemsMessageBean... items) {
        ShippingRequestMessageBean bean = new ShippingRequestMessageBean();
        bean.time = date;
        bean.items = Arrays.asList(items);
        return bean;
    }
    
    public static long id(long id) {
        return id;
    }
    
    public static int amount(int amount) {
        return amount;
    }
    
    public static ShippingItemsMessageBean item(long id, int amount) {
        ShippingItemsMessageBean item = new ShippingItemsMessageBean();
        item.id = id;
        item.amount = amount;
        return item;
    }
}
