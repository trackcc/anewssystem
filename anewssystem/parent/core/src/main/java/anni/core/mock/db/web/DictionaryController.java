package anni.core.mock.db.web;

import anni.core.mock.db.domain.Dictionary;
import anni.core.mock.db.manager.DictionaryManager;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年07月11日 下午 22时14分52秒984
 */
public class DictionaryController extends BaseLongController<Dictionary, DictionaryManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(DictionaryController.class);

    /** * index. */
    public void index() {
        logger.info("start");
    }
}
