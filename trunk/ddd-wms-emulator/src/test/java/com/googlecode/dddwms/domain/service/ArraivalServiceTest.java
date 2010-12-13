package com.googlecode.dddwms.domain.service;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.dddwms.domain.model.Warehouse;
import com.googlecode.dddwms.domain.repository.WarehouseRepository;
import com.googlecode.dddwms.messagebean.ArrivalItemsMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalRequestMessageBean;

/**
 * 入荷サービス　テスト
 */
public class ArraivalServiceTest {

    private WmsService service = new WmsService();// TODO:入荷サービスへの置き換え;
    private Warehouse mockWarehouse;
    private static final long SETUP_ITEM_ID = 2L;
    private static final int SETUP_ITEM_AMOUNT = 3;

    /**
     * 倉庫初期化
     * itemId:2　=>　amount:3
     */
    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        WarehouseRepository _warehouseRepository = WarehouseRepository
                .getInstance();
        // mockWarehouse = mock(Warehouse.class);
        mockWarehouse = _warehouseRepository.get();
        Item itemTest = new Item(SETUP_ITEM_ID);
        //mockWarehouse.arrive(SETUP_ITEM_ID, SETUP_ITEM_AMOUNT);
        mockWarehouse.arrive(itemTest, SETUP_ITEM_AMOUNT);
        _warehouseRepository.set(mockWarehouse);

        Field _warehouseRepoField = service.getClass().getDeclaredField(
                "warehouseRepository");
        _warehouseRepoField.setAccessible(true);
        _warehouseRepoField.set(service, _warehouseRepository);
    }

    /**
     * 入荷受付：追加
     * Given: 入荷依頼が既に行われていること
     *        倉庫に該当する物品が登録されていること
     * When:  入荷を受け付けた場合
     * Then:  倉庫の物品が加算される
     */
    @Test
    public void shouldHandleArrivalMessageWhenItemAdded() throws Exception {

        // テスト前の物品数
        Item itemTest = new Item(SETUP_ITEM_ID);
        //int _registeredAmount = mockWarehouse.count(SETUP_ITEM_ID);
        int _registeredAmount = mockWarehouse.count(itemTest);

        
        // 追加する物品数
        int _addAmount = 4;

        // 入荷受付
        long _internalId = acceptArraivalRequest(SETUP_ITEM_ID, _addAmount);

        // 入荷
        ArrivalMessageBean _internalMessage = new ArrivalMessageBean();
        _internalMessage.id = _internalId;
        service.handleArrival(_internalMessage);

        // when(mockWarehouse.count(SETUP_ITEM_ID)).thenReturn(
        // _addedAmount + SETUP_ITEM_AMOUNT);
        //assertEquals(_registeredAmount + _addAmount, mockWarehouse.count(SETUP_ITEM_ID));
        assertEquals(_registeredAmount + _addAmount, mockWarehouse.count(itemTest));

    }

    private long acceptArraivalRequest(final long addedId, final int addedAmount) {
        ArrivalItemsMessageBean _item = new ArrivalItemsMessageBean();
        _item.id = addedId;
        _item.amount = addedAmount;

        ArrivalRequestMessageBean _request = new ArrivalRequestMessageBean();
        ArrayList<ArrivalItemsMessageBean> _items = new ArrayList<ArrivalItemsMessageBean>();

        // 入荷受付アイテムの登録
        _items.add(_item);

        // 入荷済み物品に対してaddedAmount個、入荷受付
        _request.time = new Date();
        _request.items = _items;
        return service.handleArrivalRequest(_request);
    }
}
