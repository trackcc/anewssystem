package anni.aerp.web;

import anni.aerp.domain.ErpRation;

import anni.aerp.manager.ErpRationManager;

import anni.core.grid.LongGridController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年10月25日 上午 00时06分58秒406
 */
public class ErpRationController extends LongGridController<ErpRation, ErpRationManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(ErpRationController.class);

    /** * constructor. */
    public ErpRationController() {
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp/erpration/index");
    }

    /** * @throws Exception for json. */
    /*
        public void save() throws Exception {
            logger.info(params());
            String data = getStrParam("data", "");
            JSONObject jsonObject = JSONObject.fromObject(data);
            long productId = jsonObject.getLong("productId");
            long materialId = jsonObject.getLong("materialId");
            ErpProduct erpProduct = erpProductManager.get(productId);
            ErpMaterial erpMaterial = erpMaterialManager.get(materialId);
            ErpRation entity = bindObject();
            entity.setErpProduct(erpProduct);
            entity.setErpMaterial(erpMaterial);
            getEntityDao().save(entity);
            response.getWriter().print("{success:true}");
        }
    */

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {"hibernateLazyInitializer", "erpRations"};
    }
}
