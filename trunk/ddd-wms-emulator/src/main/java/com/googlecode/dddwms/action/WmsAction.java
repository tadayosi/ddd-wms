package com.googlecode.dddwms.action;

import java.util.List;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.domain.service.WmsService;
import com.googlecode.dddwms.messagebean.ArrivalMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalRequestMessageBean;
import com.googlecode.dddwms.messagebean.ShipMessageBean;
import com.googlecode.dddwms.messagebean.ShippingRequestMessageBean;

public class WmsAction {

    private Logger log = LoggerFactory.getLogger(WmsAction.class);

    /** arrivalRequest */
    public long arrivalRequest(String jsonMessage) {
        log.info("arrivalRequest:{}", jsonMessage);

        ArrivalRequestMessageBean message = JSON.decode(jsonMessage,
                ArrivalRequestMessageBean.class);

        WmsService service = new WmsService();
        long requestId = service.handleArrivalRequest(message);

        return requestId;
    }

    /** arrival */
    public void arrival(String jsonMessage) {
        log.info("arrival:{}", jsonMessage);

        ArrivalMessageBean message = JSON.decode(jsonMessage,
                ArrivalMessageBean.class);

        WmsService service = new WmsService();
        service.handleArrival(message);

    }

    /** shippingRequest */
    public long shippingRequest(String jsonMessage) {
        log.info("shippingRequest:{}", jsonMessage);

        ShippingRequestMessageBean message = JSON.decode(jsonMessage,
                ShippingRequestMessageBean.class);

        WmsService service = new WmsService();
        long requestId = service.handleShippingRequest(message);

        return requestId;
    }

    /** ship */
    public List<Long> ship(String jsonMessage) {
        log.info("ship:{}", jsonMessage);
        
        ShipMessageBean message = JSON.decode(jsonMessage, ShipMessageBean.class);
        
        return new WmsService().handleShip(message);
    }
}
