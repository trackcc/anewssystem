package anni.aerp.web;

import anni.aerp.domain.ErpProduct;

import anni.aerp.manager.ErpProductManager;

import anni.core.grid.LongGridController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年10月24日 下午 21时04分09秒625
 */
public class ErpProductController extends LongGridController<ErpProduct, ErpProductManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(ErpProductController.class);

    /** * constructor. */
    public ErpProductController() {
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp/erpproduct/index");
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {"hibernateLazyInitializer", "erpProduct"};
    }
}
