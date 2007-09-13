package anni.anews.web;

import anni.asecurity.domain.Menu;

import anni.asecurity.manager.MenuManager;

import anni.core.dao.support.Page;

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

    /** * menuManager. */
    private MenuManager menuManager = null;

    /** * @param menuManager MenuManager. */
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    /**
     * 首页.
     */
    public void index() {
    }

    /**
     * welcome.
     */
    public void welcome() {
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

        Page page = menuManager.pagedQuery("from Menu", 1, 20);
        mv.setView(new StreamView("application/json"));
    }
}