package anni.asecurity.menu;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import anni.asecurity.domain.Menu;

import anni.asecurity.manager.MenuManager;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * outlook型菜单工厂.
 * 前台显示使用menu.js，无法在xhtml下正常显示
 *
 * @author dancewing
 * @author Lingo
 * @since 2007-03-25
 * @version 1.0
 */
public class MenuFactory {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(MenuFactory.class);

    /**
     * outlook型菜单.
     */
    public static final int OUTLOOK_LIKE_MENU = 1;

    /**
     * dojo树型菜单.
     */
    public static final int DOJO_TREE_MENU = 2;

    /**
     * MenuManager.
     */
    private MenuManager menuManager = null;

    /**
     * MenuLoader.
     */
    private MenuLoader menuLoader = null;

    /**
     * 根据菜单类型和用户id创建菜单.
     *
     * @param menuType 菜单类型
     * @param userId 用户id
     * @return Object 返回字符串
     */
    public Object creator(int menuType, Long userId) {
        List list = menuManager.loadUserMenus(userId);
        logger.info("userId " + userId);
        logger.info(list);

        if (menuType == OUTLOOK_LIKE_MENU) {
            MenuRepository repository = new MenuRepository();
            repository.setDisplayers(menuLoader.getRepository()
                                               .getDisplayers());

            List topMenus = menuManager.loadTops("seq", "asc");

            for (int k = 0; k < topMenus.size(); k++) {
                Menu menu = (Menu) topMenus.get(k);

                if (list.contains(menu.getId())) {
                    logger.info(list + ", contains " + menu.getId());

                    MenuComponent mc = new MenuComponent();
                    mc.setName(menu.getName());
                    mc.setTitle(menu.getName());
                    mc.setPage(menu.getUrl());
                    mc.setTarget("dblselect");
                    mc.setParent(null);
                    mc.setImage(menu.getImage());

                    Set set = menu.getChildren();
                    Iterator i = set.iterator();

                    while (i.hasNext()) {
                        Menu sub = (Menu) i.next();

                        if (list.contains(sub.getId())) {
                            MenuComponent subMc = new MenuComponent();
                            subMc.setName(sub.getName());
                            subMc.setTitle(sub.getName());
                            subMc.setPage(sub.getUrl());
                            subMc.setTarget("dblselect");
                            subMc.setImage(sub.getImage());
                            subMc.setParent(mc);

                            repository.addMenu(subMc);
                        }
                    }

                    repository.addMenu(mc);
                }
            }

            return repository;
        } else {
            logger.info("不支持菜单类型：" + menuType);
        }

        return null;
    }

    /**
     * @param menuManager MenuManager.
     */
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    /**
     * @param menuLoaderIn MenuLoader.
     */
    public void setMenuLoader(MenuLoader menuLoaderIn) {
        menuLoader = menuLoaderIn;
    }
}
