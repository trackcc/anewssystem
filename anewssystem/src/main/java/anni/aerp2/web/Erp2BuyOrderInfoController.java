package anni.aerp2.web;

import anni.aerp2.domain.Erp2BuyOrderInfo;

import anni.aerp2.manager.Erp2BuyOrderInfoManager;

import anni.core.grid.LongGridController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年11月10日 下午 22时07分07秒984
 */
public class Erp2BuyOrderInfoController extends LongGridController<Erp2BuyOrderInfo, Erp2BuyOrderInfoManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(Erp2BuyOrderInfoController.class);

    /** * constructor. */
    public Erp2BuyOrderInfoController() {
        //setEditView("/aerp2/erp2buyorderinfo/editErp2BuyOrderInfo");
        //setListView("/aerp2/erp2buyorderinfo/listErp2BuyOrderInfo");
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp2/erp2buyorderinfo/index");
    }
}
