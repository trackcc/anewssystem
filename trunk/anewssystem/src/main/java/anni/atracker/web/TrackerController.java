package anni.atracker.web;

import anni.core.json.JsonController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo
 * @since 2007-10-07
 */
public class TrackerController extends JsonController {
    /** * logger. */
    private static Log logger = LogFactory.getLog(TrackerController.class);

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("atracker/index");
    }
}
