package wms.domain.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wms.domain.model.warehouse.Warehouse;

/**
 * 倉庫リポジトリ.
 * 
 * @author digitalsoul
 * @see Warehouse
 */
public final class WarehouseRepository {

    // シングルトンインスタンス
    private static WarehouseRepository _instance;
    // TODO: ところでリポジトリってファイナルなシングルトンでいいんすか？DIコンテナに任せますか？

    // ストレージ
    private Map<String, Warehouse> storage = Collections
            .synchronizedMap(new HashMap<String, Warehouse>());

    /**
     * プライベートコンストラクタ.
     */
    private WarehouseRepository() {
    }

    /**
     * ファクトリメソッド.
     * @return 倉庫リポジトリ
     */
    public static synchronized WarehouseRepository getInstance() {
        // lazy initialize
        if (_instance == null) {
            _instance = new WarehouseRepository();
        }
        return _instance;
    }

    /**
     * 倉庫を追加します。
     * @param warehouse 倉庫オブジェクト
     */
    public void add(Warehouse warehouse) {
        storage.put(warehouse.id(), warehouse);
    }

    /**
     * 倉庫一覧取得.
     * @return 倉庫一覧
     */
    public synchronized List<Warehouse> allWarehouses() {
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        for (Warehouse warehouse : storage.values()) {
            warehouses.add(warehouse);
        }
        return warehouses;
        // TODO:ImmutableListとか使った方がいいですか？ digitalsoul
    }

}
