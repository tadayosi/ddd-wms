package wms.domain.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import wms.domain.model.basics.Size;
import wms.domain.model.events.Transfer;
import wms.domain.model.items.Item;
import wms.domain.model.warehouse.Shelf;
import wms.domain.model.warehouse.Warehouse;
import wms.domain.repositories.WarehouseRepository;

/**
 * 在庫サービスの単体テストクラス.
 * 
 * @author digitalsoul
 */
public class StockServiceTest {

    /**
     * Given:複数の倉庫に物品が入っている状態で
     * When:全物品一覧取得がコールされたら
     * Then:全ての物品の一覧が取得される
     */
    @Test
    public void shouldReturnAllItems() {

        // === 前提条件 ===
        // 物品
        Item item1 = new Item("ITEM1", Size.L);
        Item item2 = new Item("ITEM2", Size.M);
        Item item3 = new Item("ITEM3", Size.S);
        Item item4 = new Item("ITEM4", Size.S);

        // 移動オブジェクトに設定
        Transfer t1 = new Transfer();
        t1.setItem(item1);
        Transfer t2 = new Transfer();
        t2.setItem(item2);
        Transfer t3 = new Transfer();
        t3.setItem(item3);
        Transfer t4 = new Transfer();
        t4.setItem(item4);

        // 棚に設定
        Shelf shelf1 = new Shelf("SHELF1");
        shelf1.addEntry(t1);
        shelf1.addEntry(t2);
        Shelf shelf2 = new Shelf("SHELF2");
        shelf2.addEntry(t3);
        shelf2.addEntry(t4);

        // 倉庫に設定
        Warehouse warehouse1 = new Warehouse("WAREHOUSE1");
        warehouse1.addShelf(shelf1);
        Warehouse warehouse2 = new Warehouse("WAREHOUSE2");
        warehouse2.addShelf(shelf2);

        // リポジトリに設定
        WarehouseRepository repository = WarehouseRepository.getInstance();
        repository.add(warehouse1);
        repository.add(warehouse2);

        // === 前物品一覧取得 ===
        StockService service = new StockService();
        List<Item> result = service.showAllItems();
        // ID順にソート
        Collections.sort(result, new Comparator<Item>() {
            public int compare(Item o1, Item o2) {
                return o1.id().compareTo(o2.id());
            }
        });
        // TODO:とりあえずここでソートしてるけどさ・・・ digitalsoul

        // === 結果検証 ===
        Assert.assertEquals("サイズ確認", 4, result.size());
        Assert.assertEquals("物品確認1", "ITEM1", result.get(0).id());
        Assert.assertEquals("物品確認2", "ITEM2", result.get(1).id());
        Assert.assertEquals("物品確認3", "ITEM3", result.get(2).id());
        Assert.assertEquals("物品確認4", "ITEM4", result.get(3).id());

    }
}
