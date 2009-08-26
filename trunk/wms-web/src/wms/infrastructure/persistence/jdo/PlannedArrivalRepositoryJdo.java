package wms.infrastructure.persistence.jdo;

import javax.jdo.PersistenceManager;

import org.slim3.jdo.PMF;

import wms.domain.model.events.PlannedArrival;
import wms.domain.model.events.PlannedArrivalRepository;

public class PlannedArrivalRepositoryJdo implements PlannedArrivalRepository {

    @Override
    public void save(PlannedArrival order) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(order);
        } finally {
            pm.close();
        }
    }

}
