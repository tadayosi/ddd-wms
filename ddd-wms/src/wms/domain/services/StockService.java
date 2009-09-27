package wms.domain.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wms.domain.model.events.Transfer;
import wms.domain.model.items.Item;
import wms.domain.model.warehouse.Shelf;
import wms.domain.model.warehouse.Warehouse;
import wms.domain.repositories.WarehouseRepository;

/**
 * 在庫サービス.
 * <p>
 * 在庫確認に関連する処理を提供します。
 * </p>
 * 
 * @author digitalsoul
 */
public class StockService {

    /**
     * 物品一覧を取得します。
     * 
     * @return 物品一覧
     */
    public synchronized List<Item> showAllItems() {

        List<Item> items = new ArrayList<Item>();

        WarehouseRepository repository = WarehouseRepository.getInstance();
        List<Warehouse> warehouses = repository.allWarehouses();
        for (Warehouse warehouse : warehouses) {
            for (Iterator<Shelf> it = warehouse.shelves().iterator(); it
                    .hasNext();) {
                Shelf shelf = it.next();
                for (Iterator<Transfer> it2 = shelf.getEntries().iterator(); it2
                        .hasNext();) {
                    items.add(it2.next().getItem());
                }
            }
        }
        return items;

        // TODO:ごりっとsynchronizedにしてみてますが、同期とかとりあえず考えなくていいすか？　digitalsoul
        // TODO:Aggregateのルートからたどるとこんなことになっちゃいますけど、いいんでしたっけ？ digitalsoul
        // TODO:結局物品がどの倉庫に入っているかとかっていう情報も必要ですよね？ digitalsoul
    }
}
