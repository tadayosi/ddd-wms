package wms.controller.order;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import wms.domain.model.events.PlannedArrival;
import wms.domain.model.events.PlannedArrivalDetail;
import wms.domain.model.events.PlannedArrivalRepository;
import wms.domain.model.items.ItemRepository;
import wms.infrastructure.persistence.jdo.PlannedArrivalRepositoryJdo;
import wms.infrastructure.persistence.memory.ItemRepositoryInMemory;
import wms.infrastructure.persistence.memory.PlannedArrivalRepositoryInMemory;

public class CommitOrderController extends Controller {

    private ItemRepository itemRepository = new ItemRepositoryInMemory();
    private PlannedArrivalRepository plannedArrivalRepository =
        new PlannedArrivalRepositoryInMemory();
//        new PlannedArrivalRepositoryJdo();

    @Override
    protected Navigation run() {
        PlannedArrival arrival = new PlannedArrival();
        arrival.setEat(asDate("eat", "yyyyMMdd"));
        arrival.addDetail(new PlannedArrivalDetail(
            asInteger("ammount"),
            itemRepository.findBy(asString("type"))));

        plannedArrivalRepository.save(arrival);
        requestScope("plannedArrivalId", arrival.getId());
        return forward("commitOrder.jsp");
    }

}
