package com.googlecode.dddwms.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.domain.model.ArrivalRequest;
import com.googlecode.dddwms.domain.model.ArrivalRequestStatus;
import com.googlecode.dddwms.domain.model.ShippingRequest;
import com.googlecode.dddwms.domain.model.ShippingRequestStatus;
import com.googlecode.dddwms.domain.model.Warehouse;
import com.googlecode.dddwms.domain.repository.ArrivalRequestRepository;
import com.googlecode.dddwms.domain.repository.ShippingRequestRepository;
import com.googlecode.dddwms.domain.repository.WarehouseRepository;
import com.googlecode.dddwms.messagebean.ArrivalItemsMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalRequestMessageBean;
import com.googlecode.dddwms.messagebean.ShipMessageBean;
import com.googlecode.dddwms.messagebean.ShippingItemsMessageBean;
import com.googlecode.dddwms.messagebean.ShippingRequestMessageBean;
import com.googlecode.dddwms.util.AbstractPredicate;

public class WmsService {

    private ArrivalRequestRepository arrivalRequestRepository = ArrivalRequestRepository
            .getInstance();

    private WarehouseRepository warehouseRepository = WarehouseRepository
            .getInstance();

    private ShippingRequestRepository shippingRequestRepository = ShippingRequestRepository
            .getInstance();

    private Logger log = LoggerFactory.getLogger(WmsService.class);

    public long handleArrivalRequest(ArrivalRequestMessageBean message) {

        long arrivalId = arrivalRequestRepository.nextId();
        ArrivalRequest request = new ArrivalRequest(arrivalId, message.time);
        
        Item arrivalItem = null;
        for (ArrivalItemsMessageBean item : message.items) {
            arrivalItem = new Item(item.id);
            request.putAmount(arrivalItem , item.amount );
        }

        arrivalRequestRepository.add(request);

        return arrivalId;
    }

    public void handleArrival(ArrivalMessageBean message) {

        // TODO テストケース未実装
        if (!arrivalRequestRepository.exists(message.id)) {
            log.error("arrival request not found:{}", message.id);
            return;
        }

        ArrivalRequest request = arrivalRequestRepository.forId(message.id);

        // TODO テストケース未実装
        if (request.status() == ArrivalRequestStatus.ARRIVED) {
            log.error("already arrived:{}", message.id);
            return;
        }

        Warehouse warehouse = warehouseRepository.get();
        for (Entry<Item, Integer> entry : request.amounts().entrySet()) {
            Item itemId = entry.getKey();
            int amount = entry.getValue().intValue();
            warehouse.arrive(itemId, amount);
        }

        warehouseRepository.set(warehouse);
        request.arrived();
    }

    public long handleShippingRequest(ShippingRequestMessageBean message) {

        long shippingId = shippingRequestRepository.nextId();
        ShippingRequest request = new ShippingRequest(shippingId, message.time);

        Item shippingItem = null;
        for (ShippingItemsMessageBean item : message.items) {
            shippingItem = new Item(item.id);
            request.putAmount(shippingItem, item.amount);
        }

        shippingRequestRepository.add(request);

        // 指定時刻に達しているか
        Date now = new Date();
        if (request.time().after(now)) {
            log.info("ShippingId:{} still have shippingRequestTime:{}  ",
                    shippingId, request.time().toString() + " <=> now:"
                            + now.toString());
        } else {
            Warehouse warehouse = warehouseRepository.get();
            for (Entry<Item, Integer> entry : request.amounts().entrySet()) {
                //long itemId = entry.getKey().longValue();
                Item itemId = entry.getKey();
                int amount = entry.getValue().intValue();
                warehouse.ship(itemId, amount);
            }
            request.shipped();
            shippingRequestRepository.add(request);
        }

        return shippingId;
    }

    public List<Long> handleShip(ShipMessageBean shipMessageBean) {

        final Date specifiedShipTime = shipMessageBean.specifiedShipTime;

        Set<ShippingRequest> targets = shippingRequestRepository
                .filter(new AbstractPredicate<ShippingRequest>() {

                    @Override
                    public boolean apply(ShippingRequest element) {
                        return element.time().before(specifiedShipTime);
                    }
                }.and(new AbstractPredicate<ShippingRequest>() {
                    @Override
                    public boolean apply(ShippingRequest element) {
                        return element.status() == ShippingRequestStatus.WAIT_FOR_SHIPPING;
                    }
                }));

        Warehouse warehouse = warehouseRepository.get();
        List<Long> shipped = new ArrayList<Long>();

        for (ShippingRequest request : targets) {
            request.shipFrom(warehouse);
            shipped.add(request.id());
        }

        return shipped;
    }

}
