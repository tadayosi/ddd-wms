package wms.interfaces;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wms.infrastructure.persistence.memory.StockOrderRepositoryOnMemory;

import com.google.common.collect.Lists;


public class AcceptActionTest {

	private AcceptOrderAction target = new AcceptOrderAction();
	
	@Before
	public void setUp() {
		List<StockOrderCommand> orders = Lists.newArrayList();
		StockOrderCommand order1 = new StockOrderCommand("Medium", 5);
		StockOrderCommand order2 = new StockOrderCommand("Large", 10);
		StockOrderCommand order3 = new StockOrderCommand("Small", 2);

		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		target.orders = orders;
		target.eat = new Date();
		target.setRepository(new StockOrderRepositoryOnMemory());
	}
	
	@Test
	public void testNormal() {
		target.process();
	}
	
}
