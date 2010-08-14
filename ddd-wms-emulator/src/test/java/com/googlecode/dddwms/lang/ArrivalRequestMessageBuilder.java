package com.googlecode.dddwms.lang;

import java.util.Arrays;
import java.util.Date;

import com.googlecode.dddwms.messagebean.ArrivalItemsMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalRequestMessageBean;

public class ArrivalRequestMessageBuilder {

    public static ArrivalRequestMessageBean newRequest(Date date,
            ArrivalItemsMessageBean... items) {
        ArrivalRequestMessageBean bean = new ArrivalRequestMessageBean();
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

    public static ArrivalItemsMessageBean item(long id, int amount) {
        ArrivalItemsMessageBean item = new ArrivalItemsMessageBean();
        item.id = id;
        item.amount = amount;
        return item;
    }

}
