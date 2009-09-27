package wms.domain.services;

import java.util.ArrayList;
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
            for (Shelf shelf : warehouse.shelves()) {
                for (Transfer transfer : shelf.getEntries()) {
                    items.add(transfer.getItem());
                }
            }
        }
        return items;

        // TODO:ごりっとsynchronizedにしてみてますが、同期とかとりあえず考えなくていいすか？　digitalsoul
        // TODO:Aggregateのルートからたどるとこんなことになっちゃいますけど、いいんでしたっけ？ digitalsoul
        // TODO:結局物品がどの倉庫に入っているかとかっていう情報も必要ですよね？ digitalsoul
    }
}
