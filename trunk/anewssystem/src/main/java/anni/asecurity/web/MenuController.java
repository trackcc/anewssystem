package anni.asecurity.web;

import anni.asecurity.domain.Menu;

import anni.asecurity.manager.MenuManager;

import anni.core.tree.LongTreeController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class MenuController extends LongTreeController<Menu, MenuManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(MenuController.class);

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("asecurity/menu/index");
    }

    /**
     * all.
     *
     * @return 返回不需要转化成json的属性数组
     */
    @Override
    public String[] getExcludesForAll() {
        return new String[] {
            "parent", "roles", "theSort", "class", "root", "checked"
        };
    }

    /**
     * children.
     *
     * @return 返回不需要转化成json的属性数组
     */
    @Override
    public String[] getExcludesForChildren() {
        return new String[] {
            "parent", "roles", "theSort", "class", "root", "children",
            "checked"
        };
    }
}
