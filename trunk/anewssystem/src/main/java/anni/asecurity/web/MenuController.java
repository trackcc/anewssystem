package anni.asecurity.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import anni.asecurity.domain.Menu;
import anni.asecurity.domain.Role;
import anni.asecurity.domain.User;

import anni.asecurity.manager.MenuManager;
import anni.asecurity.manager.RoleManager;

import anni.asecurity.menu.MenuFactory;
import anni.asecurity.menu.TreeFactory;

import anni.core.tree.LongTreeController;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class MenuController extends LongTreeController<Menu, MenuManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(MenuController.class);

    /**
     * outlook型菜单工厂.
     */
    private MenuFactory menuFactory = null;

    /**
     * 建造菜单树的工厂.
     */
    private TreeFactory treeFactory = null;

    /**
     * role dao.
     */
    private RoleManager roleManager = null;

    /** * constructor. */
    public MenuController() {
        //setEditView("/asecurity/menu/editMenu");
        //setListView("/asecurity/menu/listMenu");
    }

    /**
     * @param menuFactoryIn MenuFactory.
     */
    public void setMenuFactory(MenuFactory menuFactoryIn) {
        menuFactory = menuFactoryIn;
    }

    /**
     * @param treeFactoryIn TreeFactory.
     */
    public void setTreeFactory(TreeFactory treeFactoryIn) {
        treeFactory = treeFactoryIn;
    }

    /**
     * @param roleManager role manager.
     */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /**
     * 向模型中设置关联数据.
     *
     * @param model ModelAndView中的数据模型
     */
    protected void referenceData(Map model) {
        // logger.info(entityDao.loadTopMenus());
        model.put("parents", entityDao.loadTops("theSort", "asc"));
    }

    /**
     * 绑定选中的parent menu.
     *
     * @param request 请求
     * @param command 需要绑定的command
     * @param binder 绑定工具
     * @throws Exception 异常
     */
    protected void preBind(HttpServletRequest request, Object command,
        ServletRequestDataBinder binder) throws Exception {
        binder.setDisallowedFields(new String[] {"parent_id"});

        Menu menu = (Menu) command;

        try {
            Long id = getLongParam("parent_id", -1L);

            if (id != -1L) {
                Menu parent = entityDao.get(id);

                // check dead lock -- 不允许将自表外键关系设置成环状
                if (menu.isDeadLock(parent)) {
                    binder.getBindingResult()
                          .rejectValue("parent", "不能把父节点设置为子节点的叶子",
                        new Object[0], "不能把父节点设置为子节点的叶子");
                } else {
                    menu.setParent(parent);
                }
            }
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
    }

    /**
     * 删除失败返回的信息.
     *
     * @param id 失败记录的id
     * @return 失败信息
     */
    protected String onRemoveSelectedFailure(Long id) {
        return "〈" + getEntityDao().get(id).getName() + "〉下还有其他菜单，删除失败";
    }

    /**
     * 从数据库中创建菜单树.
     *
     * @throws Exception 异常
     */
    public void createMenu() throws Exception {
        //mv.setViewName("/admin/menu");
        User user = (User) request.getSession().getAttribute("loginUser");

        try {
            mv.addObject("repository",
                menuFactory.creator(MenuFactory.OUTLOOK_LIKE_MENU,
                    user.getId()));
        } catch (Exception ex) {
            logger.error(ex, ex);
        }

        mv.setViewName("asecurity/menu/createMenu");
    }

    /**
     * 修改菜单权限的对应关系.
     *
     * @throws Exception 异常
     */
    public void listMenuRole() throws Exception {
        //mv.setViewName("/admin/listMenuRole");
        mv.addObject("roles", roleManager.getAll());

        String roleId = getStrParam("roleId", "");

        if (StringUtils.isNotBlank(roleId)
                && StringUtils.isNumeric(roleId)) {
            mv.addObject("roleId", roleId);
        } else {
            mv.addObject("roleId", "");
        }

        creatorTree();
        mv.setViewName("asecurity/menu/listMenuRole");
    }

    /**
     * 创建树型.
     */
    private void creatorTree() {
        int treeType = getIntParam("type", TreeFactory.CHECKBOX_TREE);

        Long roleId = getLongParam("roleId", -1L);
        Long menuId = getLongParam("menuId", -1L);
        String treeText = (String) treeFactory.creator(treeType, roleId,
                menuId);
        mv.addObject("treeText", treeText);
    }

    /**
     * 保存菜单权限的对应关系.
     *
     * @throws Exception 异常
     */
    public void saveMenuRole() throws Exception {
        mv.setViewName("forward:/menu/listMenuRole.htm");

        String roleId = getStrParam("roleId", "");

        if (StringUtils.isBlank(roleId) || !StringUtils.isNumeric(roleId)) {
            mv.addObject("roleId", "");
            saveMessage("请选择角色组");

            return;
        }

        String itemselect = getStrParam("itemselect", "");
        String[] itemlist = StringUtils.split(itemselect, ",");

        Set<Menu> menus = null;

        if (itemlist != null) {
            menus = new HashSet<Menu>(itemlist.length);

            for (int k = 0; k < itemlist.length; k++) {
                Long id = Long.valueOf(itemlist[k]);
                Menu menu = entityDao.get(id);
                menus.add(menu);
            }
        }

        Role role = roleManager.get(Long.valueOf(roleId));

        // logger.info(menus);
        // logger.info(role);
        if (role != null) {
            role.setMenus(menus);
            roleManager.save(role);
        }

        // mv.addObject("roleId", roleId);
        mv.setViewName("forward:/menu/listMenuRole.htm?roleId=" + roleId);
    }

    /** * index. */
    public void index() {
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
