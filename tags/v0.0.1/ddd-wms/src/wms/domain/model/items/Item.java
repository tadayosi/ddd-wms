package wms.domain.model.items;

import static wms.infrastructure.utils.SupportUtils.required;

import wms.domain.model.basics.Size;
import wms.domain.model.shared.Entity;

/**
 * 物品
 * @author kentaro
 */
public class Item implements Entity<Item> {

    private String id;
    private Size size;

    /**
     * デフォルトコンストラクタ
     * @deprecated 
     */
    public Item() {
        // TODO:エンティティであれば、IDは必須の方が良いのでは？ digitalsoul
    }

    /**
     * 物品オブジェクトを構築します。
     * @param size サイズ
     * @deprecated
     */
    public Item(Size size) {
        this.size = size;
        // TODO:エンティティであれば、IDは必須の方が良いのでは？ digitalsoul
    }

    /**
     * 物品オブジェクトを構築します。
     * @param id ID
     * @param size サイズ
     */
    public Item(String id, Size size) {
        required(id, size);
        this.id = id;
        this.size = size;
    }

    /**
     * IDを取得します。
     * @return ID
     */
    public String id() {
        return id;
    }

    /**
     * サイズを取得します。
     * @return
     */
    public Size getSize() {
        return size;
    }

    public boolean sameIdentityAs(Item other) {
        if (other == null) {
            throw new IllegalArgumentException("Item shouldn't be null.");
        }
        return other.id.equals(this.id);
    }
}
