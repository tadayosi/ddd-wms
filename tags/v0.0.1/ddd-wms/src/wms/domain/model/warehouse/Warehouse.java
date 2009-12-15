package wms.domain.model.warehouse;

import static wms.infrastructure.utils.SupportUtils.required;

import java.util.HashSet;
import java.util.Set;

import wms.domain.model.shared.Entity;

/**
 * 倉庫
 * Aggregate Root?
 * @author kentaro
 */
public class Warehouse implements Entity<Warehouse> {
    private String id;
    private Set<Shelf> shelves = new HashSet<Shelf>();

    /**
     * 倉庫オブジェクトを構築します。
     * @param id ID
     */
    public Warehouse(String id) {
        required(id);
        this.id = id;
    }

    /**
     * IDを取得します。
     * @return ID
     */
    public String id() {
        return id;
    }

    public Set<Shelf> shelves() {
        Set<Shelf> copy = new HashSet<Shelf>();
        for (Shelf shelf : shelves) {
            copy.add(shelf);
        }
        return copy;
        // TODO:difensiveコピーとか・・・とりあえずごりっと実装
        // Transfer以降はイミュータブルのはず？
    }

    public void addShelf(Shelf shelf) {
        shelves.add(shelf);
    }

    public boolean sameIdentityAs(Warehouse other) {
        return this.id.equals(other.id);
    }

}
