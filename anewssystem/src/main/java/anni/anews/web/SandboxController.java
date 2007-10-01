package anni.anews.web;

import anni.core.web.prototype.ExtendController;
import anni.core.web.prototype.StreamView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007-09-10
 */
public class SandboxController extends ExtendController {
    /** * logger. */
    private static Log logger = LogFactory.getLog(SandboxController.class);

    /**
     * 首页.
     */
    public void index() {
    }

    /**
     * welcome.
     */
    public void welcome() {
        logger.info("start");
    }

    /**
     * needLogin.
     */
    public void needLogin() {
        logger.info("start");
    }

    /**
     * DataGrid.
     */
    public void datagrid() {
    }

    /**
     * listMenu.
     */
    public void listMenu() {
        logger.info("start");
        logger.info(params());

        //Page page = menuManager.pagedQuery("from Menu", 1, 20);
        mv.setView(new StreamView("application/json"));
    }
}
