package com.googlecode.dddwms.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.dddwms.domain.model.ArrivalRequest;
import com.googlecode.dddwms.domain.model.ArrivalRequestStatus;
import com.googlecode.dddwms.domain.model.Warehouse;
import com.googlecode.dddwms.domain.repository.ArrivalRequestRepository;
import com.googlecode.dddwms.domain.repository.WarehouseRepository;
import com.googlecode.dddwms.messagebean.ArrivalItemsMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalRequestMessageBean;
import com.googlecode.dddwms.messagebean.ShippingRequestMessageBean;

public class WmsServiceTest {

	@Before
	public void doBeforeEachTest() {
		ArrivalRequestRepository.getInstance().init();
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
		ArrivalRequest request = ArrivalRequestRepository.getInstance().forId(id);
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

		ArrivalRequest request = ArrivalRequestRepository.getInstance().forId(requestId);
		assertEquals(ArrivalRequestStatus.ARRIVED, request.status());

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
		fail("未実装");
	}

	/**
	 * 出荷：正常
	 * Given: 入荷が既に行われていること
	 * When:  出荷を受け付けた場合
	 * Then:  倉庫の該当する物品が減産される
	 * TODO 多分状況確認のため、出荷エンティティは必要になる
	 */
	@Test
	public void shouldHandleShippingRequestMessage() throws Exception {

		Date now = new Date();

		// setup
		WarehouseRepository warehouseRepository = WarehouseRepository.getInstance();
		{
			Warehouse warehouse = warehouseRepository.get();
			warehouse.arrive(1, 5);
			warehouseRepository.set(warehouse);
		}

		// message bean
		ShippingRequestMessageBean message = new ShippingRequestMessageBean();
		message.time = now;

		// item1
		ArrivalItemsMessageBean bean1 = new ArrivalItemsMessageBean();
		bean1.id = 1;
		bean1.amount = 4;

		// item list
		List<ArrivalItemsMessageBean> items = new ArrayList<ArrivalItemsMessageBean>();
		items.add(bean1);

		// execute
		WmsService service = new WmsService();
		service.handleShippingRequest(message);

		// verify
		Warehouse warehouse = warehouseRepository.get();
		// ５個入荷／４個出荷→残りは１個
		assertEquals(1, warehouse.count(1));
	}

	/**
	 * 出荷：物品数不足
	 * Given: 該当する物品の個数が足りない
	 * When:  出荷を受け付けた場合
	 * Then:  （出荷失敗のメッセージが出力される）
	 * TODO ふるまいを確認する
	 */
	@Test
	public void shouldHandleShippingRequestMessageWhenItemIsOutOfStock() throws Exception {
		fail("未実装");
	}

	/**
	 * 出荷：物品不在
	 * Given: 倉庫に該当する物品が存在しない
	 * When:  出荷を受け付けた場合
	 * Then:  （出荷失敗のメッセージが出力される）
	 * TODO ふるまいを確認する
	 */
	@Test
	public void shouldHandleShippingRequestMessageWhenItemNotFound() throws Exception {
		fail("未実装");
	}

	/**
	 * 物品数：１
	 * 
	 * 物品：
	 * 物品ID：1
	 * 個数：3
	 * 
	 */
	private static ArrivalRequestMessageBean createArrivalRequestMessage(Date time) {

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

}
