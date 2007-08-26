package anni.core.mock.db.web;

import anni.core.mock.db.domain.Category;
import anni.core.mock.db.manager.CategoryManager;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年07月11日 下午 22时14分52秒984
 */
public class CategoryController extends BaseLongController<Category, CategoryManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(CategoryController.class);

    /** * index. */
    public void index() {
        logger.info("start");
    }
}
