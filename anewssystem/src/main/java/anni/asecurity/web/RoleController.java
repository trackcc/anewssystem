package anni.asecurity.web;

import java.util.LinkedHashSet;
import java.util.List;

import anni.asecurity.domain.Menu;
import anni.asecurity.domain.Resource;
import anni.asecurity.domain.Role;

import anni.asecurity.manager.MenuManager;
import anni.asecurity.manager.ResourceManager;
import anni.asecurity.manager.RoleManager;

import anni.core.grid.LongGridController;

import anni.core.json.JsonUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class RoleController extends LongGridController<Role, RoleManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(RoleController.class);

    /** * resource manager. */
    private ResourceManager resourceManager = null;

    /** * menu manager. */
    private MenuManager menuManager = null;

    /** * @param resourceManager Resource Manager. */
    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    /** * @param menuManager MenuManager. */
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    /**
     * 显示角色对应的资源列表.
     *
     * @throws Exception 异常
     */
    public void getResources() throws Exception {
        logger.info(params());

        long roleId = getLongParam("roleId", 0L);
        Role role = getEntityDao().get(roleId);
        logger.info(roleId);

        List<Resource> resources = resourceManager.getAll();

        if (role != null) {
            for (Resource resource : resources) {
                if (role.getResources().contains(resource)) {
                    resource.setAuthorized(true);
                }
            }
        }

        JsonUtils.write(resources, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 授权与撤消授权.
     *
     * @throws Exception 异常
     */
    public void auth() throws Exception {
        boolean isAuth = getBooleanParam("isAuth", false);
        String ids = getStrParam("ids", "");
        long roleId = getLongParam("roleId", 0L);
        Role role = getEntityDao().get(roleId);
        String[] arrays = ids.split(",");
        logger.info(roleId);
        logger.info(ids);

        if (role != null) {
            if (isAuth) {
                for (String id : arrays) {
                    Resource resource = resourceManager.get(Long.valueOf(
                                id));

                    if (!role.getResources().contains(resource)) {
                        role.getResources().add(resource);
                    }
                }
            } else {
                for (String id : arrays) {
                    Resource resource = resourceManager.get(Long.valueOf(
                                id));

                    if (role.getResources().contains(resource)) {
                        role.getResources().remove(resource);
                    }
                }
            }

            getEntityDao().save(role);
            getEntityDao().flush();

            // 修改资源
            for (String id : arrays) {
                Resource resource = resourceManager.get(Long.valueOf(id));

                getEntityDao().saveRoleInCache(resource);
            }
        }
    }

    /** * index. */
    public void index() {
        mv.setViewName("asecurity/role/index");
    }

    /**
     * 显示指定角色下可显示的菜单.
     *
     * @throws Exception 写入writer的时候，抛出异常
     */
    public void getMenuByRole() throws Exception {
        logger.info(params());

        long roleId = getLongParam("id", 0L);
        Role role = getEntityDao().get(roleId);
        List<Menu> list = menuManager.find(
                "from Menu where parent is null order by theSort asc,id desc");

        // 因为只有两级菜单，所以这里只需要写两个循环就可以判断哪些菜单被选中了
        // 不考虑多级情况，只从最直接的角度考虑
        for (Menu menu : list) {
            if (role.getMenus().contains(menu)) {
                menu.setChecked(true);

                for (Menu subMenu : menu.getChildren()) {
                    if (role.getMenus().contains(subMenu)) {
                        subMenu.setChecked(true);
                    }
                }
            }
        }

        // 现在checkbox tree的问题是无法在js里设置根节点，必须在json里做一个根节点
        // 如果不设置根节点，getChecked()方法返回的只有第一棵树的数据，疑惑中。
        // 为了他的限制，多写了下面这么多代码，真郁闷
        Menu root = new Menu();
        root.setId(0L);
        root.setName("选择菜单");
        root.setChildren(new LinkedHashSet<Menu>(list));
        root.setChecked(true);

        Menu[] menus = new Menu[] {root};

        JsonUtils.write(menus, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 选择角色对应的菜单.
     *
     * @throws Exception 写入response可能抛出异常吧？
     */
    public void selectMenu() throws Exception {
        logger.info(params());

        long roleId = getLongParam("roleId", 0L);
        logger.info(roleId);

        Role role = getEntityDao().get(roleId);

        if (role == null) {
            response.getWriter().print("{success:false}");

            return;
        }

        role.getMenus().clear();

        String ids = getStrParam("ids", "");
        logger.info(ids);

        for (String str : ids.split(",")) {
            try {
                long id = Long.parseLong(str);
                logger.info(id);

                Menu menu = menuManager.get(id);
                logger.info(menu);

                if (menu != null) {
                    role.getMenus().add(menu);
                }
            } catch (Exception ex) {
                logger.info(ex);
            }
        }

        getEntityDao().save(role);

        response.getWriter().print("{success:true}");
    }

    /**
     * @return excludes.
     */
    @Override
    public String[] getExcludes() {
        return new String[] {
            "resources", "menus", "users", "roles", "parent", "parentId",
            "allowEdit", "draggable", "allowDelete", "allowChildren",
            "image", "root", "url", "theSort", "cls", "qtip"
        };
    }
}
