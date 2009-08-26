package wms.controller.order;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class OrderInputController extends Controller {

    @Override
    protected Navigation run() {
        return forward("orderInput.jsp");
    }

}
