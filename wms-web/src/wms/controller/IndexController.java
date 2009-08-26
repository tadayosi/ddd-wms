package wms.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class IndexController extends Controller {

    @Override
    protected Navigation run() {
        return forward("index.jsp");
    }

}
