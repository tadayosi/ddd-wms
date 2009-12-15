package com.googlecode.dddwms.domain.model;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

/**
 * 倉庫の単体テストケース
 * 
 * @author kentaro
 * 
 */
@RunWith(JDaveRunner.class)
public class WarehouseSpec extends Specification<Warehouse> {

    /**
     * 空の倉庫
     * 
     * @author kentaro
     */
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

        /**
         * 物品を格納した後は空でなくなる
         */
        public void isNoLongerEmptyAfterStoreAnItem() {
            Item bottledWater = new Item();
            bottledWater.setName("エビアン");
            warehouse.store(bottledWater);
            specify(warehouse, must.not().be.empty());
        }

        /**
         * 空の倉庫からtakeすると例外が発生
         */
        public void complainsOnTake() {
            specify(new Block() {
                @Override
                public void run() throws Throwable {
                    Item bottledWater = new Item();
                    bottledWater.setName("エビアン");
                    warehouse.take(bottledWater);
                }
                // TODO 例外クラスをまともに
            }, should.raise(RuntimeException.class));
        }
    }

    /**
     * エビアンを1つだけ格納した倉庫
     * 
     * @author kentaro
     */
    public class WarehouseContainsOneItem {
        private Warehouse warehouse;

        public Warehouse create() {
            warehouse = new Warehouse();
            Item bottledWater = new Item();
            bottledWater.setName("エビアン");
            warehouse.store(bottledWater);
            return warehouse;
        }

        /**
         * 物品をすべて取り去った後は空に戻る
         */
        public void isEmptyAfterTakeAllItems() {
            Item bottledWater = new Item();
            bottledWater.setName("エビアン");
            warehouse.take(bottledWater);
            specify(warehouse, must.be.empty());
        }
    }
}
