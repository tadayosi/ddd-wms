package wms.domain.model.warehouse;

import static wms.infrastructure.utils.SupportUtils.required;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import wms.domain.model.basics.Size;
import wms.domain.model.events.Transfer;
import wms.domain.model.shared.Entity;

public class Shelf extends Location implements Entity<Shelf> {
    private String id;
    private Set<Transfer> entries = new HashSet<Transfer>();

    /**
     * @deprecated
     */
    public Shelf() {
        // TODO:IDは必須
    }

    /**
     * 棚を構築します。
     * @param id ID
     */
    public Shelf(String id) {
        required(id);
        this.id = id;
    }

    public void addEntry(Transfer entry) {
        entries.add(entry);
    }

    public Set<Transfer> getEntries() {
        // defensive copy
        Set<Transfer> copy = new HashSet<Transfer>();
        for (Iterator<Transfer> it = entries.iterator(); it.hasNext();) {
            copy.add(it.next());
        }
        return copy;
    }

    public boolean sameIdentityAs(Shelf other) {
        return equals(other);
    }

    @Override
    public boolean available(Size size, int ammount) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Shelf other = (Shelf) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
