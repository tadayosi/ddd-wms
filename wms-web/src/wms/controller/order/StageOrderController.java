package wms.controller.order;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class StageOrderController extends Controller {

    
    @Override
    protected Navigation run() {
        // TODO 何かしらチェックが必要だと思う
        // 現在までの実績エントリの集計＋EATまでの予定エントリの集計＋入荷数量 <= 倉庫の最大容量か？
        
        return forward("confirmOrder.jsp");
    }

}
