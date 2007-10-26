package anni.aerp.web;

import anni.aerp.domain.ErpCustomer;

import anni.aerp.manager.ErpCustomerManager;

import anni.core.grid.LongGridController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年10月24日 下午 21时41分08秒15
 */
public class ErpCustomerController extends LongGridController<ErpCustomer, ErpCustomerManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(ErpCustomerController.class);

    /** * constructor. */
    public ErpCustomerController() {
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp/erpcustomer/index");
    }
}
