package com.googlecode.dddwms.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static com.googlecode.dddwms.lang.ShippingRequestMessageBuilder.id;
import static com.googlecode.dddwms.lang.ShippingRequestMessageBuilder.amount;
import static com.googlecode.dddwms.lang.ShippingRequestMessageBuilder.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.dddwms.domain.model.ArrivalRequest;
import com.googlecode.dddwms.domain.model.ArrivalRequestStatus;
import com.googlecode.dddwms.domain.model.ShippingRequest;
import com.googlecode.dddwms.domain.model.ShippingRequestStatus;
import com.googlecode.dddwms.domain.model.Warehouse;
import com.googlecode.dddwms.domain.repository.ArrivalRequestRepository;
import com.googlecode.dddwms.domain.repository.ShippingRequestRepository;
import com.googlecode.dddwms.domain.repository.WarehouseRepository;
import com.googlecode.dddwms.lang.ShippingRequestMessageBuilder;
import com.googlecode.dddwms.messagebean.ArrivalItemsMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalRequestMessageBean;
import com.googlecode.dddwms.messagebean.ShipMessageBean;
import com.googlecode.dddwms.messagebean.ShippingItemsMessageBean;
import com.googlecode.dddwms.messagebean.ShippingRequestMessageBean;

public class WmsServiceTest {

    @Before
    public void doBeforeEachTest() {
        ArrivalRequestRepository.getInstance().init();
        ShippingRequestRepository.getInstance().init();
        WarehouseRepository.getInstance().init();
    }

    /**
     * 入荷依頼受付
     * Given: なし 
     * When: 入荷依頼を受け付けた場合 
     * Then: 入荷依頼エンティティが登録される
     *       入荷依頼エンティティの状態が"入荷待ち"である
     */
    @Test
    public void shouldHandleArrivalRequestMessage() throws Exception {

        Date now = new Date();

        // message
        ArrivalRequestMessageBean messageBean = createArrivalRequestMessage(now);

        // execute
        long id = arrivalRequest(messageBean);

        // verify
        ArrivalRequest request = ArrivalRequestRepository.getInstance().forId(
                id);
        assertEquals(id, request.id());
        assertEquals(now, request.time());
        assertEquals(3, request.amountOf(1L));
        assertEquals(ArrivalRequestStatus.WAIT_FOR_ARRIVAL, request.status());

    }

    /**
     * 入荷受付：新規
     * Given: 入荷依頼が既に行われていること 
     *        倉庫に該当する物品が登録されていないこと 
     * When:  入荷を受け付けた場合 
     * Then:  倉庫に物品が登録される 
     *        入荷依頼ステータスが"入荷済み"になる
     */
    @Test
    public void shouldHandleArrivalMessageWhenNewItem() throws Exception {

        // arrival request
        Date now = new Date();
        ArrivalRequestMessageBean messageBean = createArrivalRequestMessage(now);
        long requestId = arrivalRequest(messageBean);

        // message bean
        ArrivalMessageBean message = createArrivalMessage(requestId);

        // execute
        arrival(message);

        // verify
        Warehouse warehouse = WarehouseRepository.getInstance().get();
        assertEquals(3, warehouse.count(1L));

        ArrivalRequest request = ArrivalRequestRepository.getInstance().forId(
                requestId);
        assertEquals(ArrivalRequestStatus.ARRIVED, request.status());

    }

    /**
     * 入荷受付：追加
     * Given: 入荷依頼が既に行われていること
     *        倉庫に該当する物品が登録されていること
     * When:  入荷を受け付けた場合
     * Then:  倉庫の物品が加算される
     */
    // @Test
    public void shouldHandleArrivalMessageWhenItemAdded() throws Exception {
        fail("未実装");
    }

    /**
     * 出荷：正常（出荷指示・出荷）
     * Given: 出荷指示が行われていること
     * When:  出荷指示を受け付けた場合(現在時刻が、出荷指示の時刻よりも後）
     * Then:  出荷指示を受け付ける。出荷する(出荷済み）
     * TODO 　   
     */
    @Test
    public void shouldHandleShippingRequestAtTheTime() throws Exception {
        // setup
        WarehouseRepository warehouseRepository = WarehouseRepository
                .getInstance();
        {
            Warehouse warehouse = warehouseRepository.get();
            warehouse.arrive(2, 5);
            warehouseRepository.set(warehouse);
        }
        Date now = new Date();
        // 10秒前
        Date inputDate = new Date(now.getTime() - 10 * 1000);

        // message
        ShippingRequestMessageBean messageBean = createShippingRequestMessage(
                inputDate, 2L, 3);
        // execute
        long id = shippingRequest(messageBean);

        // verify
        ShippingRequest request = ShippingRequestRepository.getInstance()
                .forId(id);
        assertEquals(id, request.id());
        assertEquals(inputDate, request.time());
        assertEquals(3, request.amountOf(2L));
        assertEquals(ShippingRequestStatus.SHIPPED, request.status());

        // verify
        Warehouse warehouse = warehouseRepository.get();
        // ５個入荷／３個出荷→残りは2個
        assertEquals(2, warehouse.count(2));
    }

    /**
     * 出荷：正常 （出荷指示のみ）
     * Given: 出荷指示が行われていること
     * When:  出荷指示を受け付けた場合(現在時刻が、出荷指示の時刻よりも前）
     * Then:  出荷指示を受け付ける。出荷しない(出荷待ち）
     * TODO 　   
     */
    @Test
    public void shouldHandleShippingRequestStillHaveTime() throws Exception {
        // setup
        WarehouseRepository warehouseRepository = WarehouseRepository
                .getInstance();
        {
            Warehouse warehouse = warehouseRepository.get();
            warehouse.arrive(3, 5);
            warehouseRepository.set(warehouse);
        }
        Date now = new Date();
        // 10秒後
        Date inputDate = new Date(now.getTime() + 10 * 1000);

        // message
        ShippingRequestMessageBean messageBean = createShippingRequestMessage(
                inputDate, 3L, 3);
        // execute
        long id = shippingRequest(messageBean);

        // verify
        ShippingRequest request = ShippingRequestRepository.getInstance()
                .forId(id);
        assertEquals(id, request.id());
        assertEquals(inputDate, request.time());
        assertEquals(3, request.amountOf(3L));
        assertEquals(ShippingRequestStatus.WAIT_FOR_SHIPPING, request.status());

        // verify
        Warehouse warehouse = warehouseRepository.get();
        // ５個入荷／３個出荷→出荷待ちであるため、残りは5個のまま。
        assertEquals(5, warehouse.count(3));
    }

    /**
     * 出荷：物品数不足
     * Given: 該当する物品の個数が足りない
     * When:  出荷を受け付けた場合
     * Then:  （出荷失敗のメッセージが出力される）
     * TODO ふるまいを確認する
     */
    // @Test
    public void shouldHandleShippingRequestMessageWhenItemIsOutOfStock()
            throws Exception {
        fail("未実装");
    }

    /**
     * 出荷：物品不在
     * Given: 倉庫に該当する物品が存在しない
     * When:  出荷を受け付けた場合
     * Then:  （出荷失敗のメッセージが出力される）
     * TODO ふるまいを確認する
     */
    // @Test
    public void shouldHandleShippingRequestMessageWhenItemNotFound()
            throws Exception {
        fail("未実装");
    }

    /**
     * 出荷: 正常（出荷対象の依頼のみが存在）
     * 
     * Given: 出荷対象に当てはまる出荷依頼のみが存在している
     * Given: 出荷依頼が指し示す物品が、指定数だけ倉庫に存在している
     * When: 出荷を受け付けた場合
     * Then: 出荷対象の出荷指示の状態が「出荷済み（SHIPPED）」に遷移する
     * Then: 倉庫から出荷対象物品が指定数量だけ減少する
     */
    @Test
    public void shouldHandleShippingItemsMessage() throws Exception {
        // initialize warehouse
        WarehouseRepository warehouseRepository = WarehouseRepository
                .getInstance();
        Warehouse warehouse = warehouseRepository.get();
        warehouse.arrive(1, 5);
        warehouse.arrive(2, 5);
        warehouse.arrive(3, 10);
        warehouseRepository.set(warehouse);

        // ship request
        Date now = new Date();
        Date shippingTime = new Date(now.getTime() + 5 * 1000);
        Set<Long> expected = new HashSet<Long>();

        ShippingRequestMessageBean firstShipMessage = ShippingRequestMessageBuilder
                .newRequest(shippingTime, item(id(1), amount(3)), item(id(2),
                        amount(4)));
        long firstShipped = shippingRequest(firstShipMessage);
        expected.add(firstShipped);

        ShippingRequestMessageBean secondShipMessage = ShippingRequestMessageBuilder
                .newRequest(shippingTime, item(id(2), amount(1)), item(id(3),
                        amount(9)));
        long secondShipped = shippingRequest(secondShipMessage);
        expected.add(secondShipped);

        // ship
        Date specifiedShipTime = new Date(now.getTime() + 10 * 1000);
        ShipMessageBean shipMessage = createShipMessage(specifiedShipTime);
        List<Long> shipped = ship(shipMessage);

        // verify
        ShippingRequestRepository shippingRequestRepository = ShippingRequestRepository
                .getInstance();

        assertTrue(shipped.containsAll(expected));

        assertEquals(ShippingRequestStatus.SHIPPED, shippingRequestRepository
                .forId(firstShipped).status());
        assertEquals(ShippingRequestStatus.SHIPPED, shippingRequestRepository
                .forId(secondShipped).status());
        assertEquals(Integer.valueOf(2), warehouse.items().get(Long.valueOf(1)));
        assertEquals(Integer.valueOf(0), warehouse.items().get(Long.valueOf(2)));
        assertEquals(Integer.valueOf(1), warehouse.items().get(Long.valueOf(3)));
    }

    /**
     * 出荷: 正常（時間前の依頼が混在）
     * 
     * Given: 出荷対象の出荷依頼が存在している
     * Given: 出荷対象外の出荷依頼（出荷時刻が現在時刻後の依頼）が存在している
     * Given: 出荷依頼が指し示す物品が、指定数だけ倉庫に存在している
     * When: 出荷を受け付けた場合
     * Then: 出荷対象の出荷のみ状態が「出荷済み（SHIPPED）」に遷移する
     * Then: 倉庫から出荷対象物品が指定数量だけ減少する
     */
    @Test
    public void shouldHandleShippingItemsMessageWhenFutureRequestExist()
            throws Exception {
        // initialize warehouse
        WarehouseRepository warehouseRepository = WarehouseRepository
                .getInstance();
        Warehouse warehouse = warehouseRepository.get();
        warehouse.arrive(1, 5);
        warehouse.arrive(2, 5);
        warehouse.arrive(3, 10);
        warehouseRepository.set(warehouse);

        // ship request
        Date now = new Date();
        Date futureTime = new Date(now.getTime() + 1000 * 1000);

        ShippingRequestMessageBean firstShipMessage = ShippingRequestMessageBuilder
                .newRequest(futureTime, item(id(1), amount(3)), item(id(2),
                        amount(4)));
        long willBeShipped = shippingRequest(firstShipMessage);

        Date shippingTime = new Date(now.getTime() + 5 * 1000);
        ShippingRequestMessageBean secondShipMessage = ShippingRequestMessageBuilder
                .newRequest(shippingTime, item(id(2), amount(1)), item(id(3),
                        amount(9)));
        long beingShipped = shippingRequest(secondShipMessage);

        // ship
        Date specifiedShipTime = new Date(now.getTime() + 10 * 1000);
        ShipMessageBean shipMessage = createShipMessage(specifiedShipTime);
        List<Long> shipped = ship(shipMessage);

        // verify
        ShippingRequestRepository shippingRequestRepository = ShippingRequestRepository
                .getInstance();

        assertEquals(Long.valueOf(beingShipped), Long.valueOf(shipped.get(0)));

        assertEquals(ShippingRequestStatus.WAIT_FOR_SHIPPING,
                shippingRequestRepository.forId(willBeShipped).status());
        assertEquals(ShippingRequestStatus.SHIPPED, shippingRequestRepository
                .forId(beingShipped).status());
        assertEquals(Integer.valueOf(5), warehouse.items().get(Long.valueOf(1)));
        assertEquals(Integer.valueOf(4), warehouse.items().get(Long.valueOf(2)));
        assertEquals(Integer.valueOf(1), warehouse.items().get(Long.valueOf(3)));
    }

    /**
     * 物品数：１
     * 
     * 物品：
     * 物品ID：1
     * 個数：3
     * 
     */
    private static ArrivalRequestMessageBean createArrivalRequestMessage(
            Date time) {

        // message bean
        ArrivalRequestMessageBean messageBean = new ArrivalRequestMessageBean();
        messageBean.time = time;

        // items
        ArrivalItemsMessageBean itemBean1 = new ArrivalItemsMessageBean();
        itemBean1.id = 1L;
        itemBean1.amount = 3;

        List<ArrivalItemsMessageBean> items = new ArrayList<ArrivalItemsMessageBean>();
        items.add(itemBean1);
        messageBean.items = items;
        return messageBean;
    }

    private static long arrivalRequest(ArrivalRequestMessageBean messageBean) {
        WmsService service = new WmsService();
        long id = service.handleArrivalRequest(messageBean);
        return id;
    }

    private static ArrivalMessageBean createArrivalMessage(long requestId) {
        ArrivalMessageBean message = new ArrivalMessageBean();
        message.id = requestId;
        return message;
    }

    private static void arrival(ArrivalMessageBean message) {
        WmsService service = new WmsService();
        service.handleArrival(message);
    }

    /**
     * 物品数：１（出荷指示用）
     * 
     * 物品：
     * 物品ID：1
     * 個数：3
     * 
     */
    private static ShippingRequestMessageBean createShippingRequestMessage(
            Date time, long id, int amount) {

        // message bean
        ShippingRequestMessageBean messageBean = new ShippingRequestMessageBean();
        messageBean.time = time;

        // items
        ShippingItemsMessageBean itemBean1 = new ShippingItemsMessageBean();
        itemBean1.id = id;
        itemBean1.amount = amount;

        List<ShippingItemsMessageBean> items = new ArrayList<ShippingItemsMessageBean>();
        items.add(itemBean1);
        messageBean.items = items;
        return messageBean;
    }

    private static ShipMessageBean createShipMessage(Date specifiedShipTime) {
        // message bean
        ShipMessageBean messageBean = new ShipMessageBean();
        messageBean.specifiedShipTime = specifiedShipTime;
        return messageBean;
    }

    private static long shippingRequest(ShippingRequestMessageBean messageBean) {
        WmsService service = new WmsService();
        long id = service.handleShippingRequest(messageBean);
        return id;
    }

    private static List<Long> ship(ShipMessageBean messageBean) {
        WmsService service = new WmsService();
        List<Long> shipped = service.handleShip(messageBean);
        return shipped;
    }

}
