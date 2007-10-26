package anni.aerp.web;

import anni.aerp.domain.ErpMaterial;

import anni.aerp.manager.ErpMaterialManager;

import anni.core.grid.LongGridController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年10月24日 下午 18时19分15秒687
 */
public class ErpMaterialController extends LongGridController<ErpMaterial, ErpMaterialManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(ErpMaterialController.class);

    /** * constructor. */
    public ErpMaterialController() {
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp/erpmaterial/index");
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {"hibernateLazyInitializer", "erpMaterial"};
    }
}
