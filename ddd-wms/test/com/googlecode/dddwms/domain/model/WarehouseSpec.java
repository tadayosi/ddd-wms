package com.googlecode.dddwms.domain.model;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;


/**
 * 倉庫の単体テストケース
 * @author kentaro
 *
 */
@RunWith(JDaveRunner.class)
public class WarehouseSpec extends Specification<Warehouse>{
	
	public class EmptyWarehouse {
		private Warehouse warehouse;
		
		public Warehouse create() {
			warehouse = new Warehouse();
			return warehouse;
		}
		
		/**
		 * 倉庫は最初は空
		 */
		public void isEmptyWhenCreated() {
			specify(warehouse, must.be.empty());
		}
	}
	
}
