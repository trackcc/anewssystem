package anni.asecurity.menu;

import net.sf.navigator.menu.MenuRepository;
import net.sf.navigator.util.LoadableResourceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.context.support.WebApplicationObjectSupport;


/**
 * 读取menu-config.xml中的配置信息.
 *
 * @author dancewing
 * @author Lingo
 * @since 2007-03-25
 * @version 1.0
 */
public class MenuLoader extends WebApplicationObjectSupport {
    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(MenuLoader.class);

    /**
     * Configuration file for menus.
     */
    private String menuConfig = "/WEB-INF/menu-config.xml";

    /**
     * 菜单资源库.
     */
    private MenuRepository repository;

    /**
     * Set the Menu configuration file.
     *
     * @param menuConfigIn the file containing the Menus/Items
     */
    public void setMenuConfig(String menuConfigIn) {
        menuConfig = menuConfigIn;
    }

    /**
     * Initialization of the Menu Repository.
     * 可能抛出异常：
     * throws org.springframework.context.ApplicationContextException
     */
    protected void initApplicationContext() {
        if (log.isDebugEnabled()) {
            log.debug("Starting struts-menu initialization");
        }

        try {
            repository = new MenuRepository();
            repository.setLoadParam(menuConfig);
            repository.setServletContext(getServletContext());
            repository.load();
            getServletContext()
                .setAttribute(MenuRepository.MENU_REPOSITORY_KEY,
                repository);

            if (log.isDebugEnabled()) {
                log.debug("struts-menu initialization successful");
            }
        } catch (LoadableResourceException lre) {
            //throw new ApplicationContextException(
            //    "Failure initializing struts-menu: " + lre.getMessage());
            logger.error(lre, lre);
        } catch (IllegalStateException ex) {
            logger.error(ex, ex);
        }
    }

    /**
     * 返回资源库.
     *
     * @return MenuReposotory
     */
    public MenuRepository getRepository() {
        return repository;
    }
}
