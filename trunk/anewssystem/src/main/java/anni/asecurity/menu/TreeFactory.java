package anni.asecurity.menu;

import java.text.MessageFormat;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import anni.asecurity.domain.Menu;
import anni.asecurity.domain.Role;

import anni.asecurity.manager.MenuManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.orm.ObjectRetrievalFailureException;


/**
 * 菜单树工厂.
 * 根据扩展后的树模板，生成附加了radio或checkbox的菜单树
 *
 * @author dancewing
 * @author Lingo
 * @since 2007-03-25
 * @version 1.0
 */
public class TreeFactory {
    /**
     * radio tree.
     */
    public static final int RADIO_TREE = 1;

    /**
     * checkbox tree.
     */
    public static final int CHECKBOX_TREE = 2;

    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(TreeFactory.class);

    /**
     * 模板.
     */
    private static MessageFormat tree = new MessageFormat(
            "<div dojoType=\"TreeNode\" title=\"{0}\" value=\"{1}\" ischecked=\"{2}\" type=\"{3}\">");

    /**
     * menuDao.
     */
    private MenuManager menuManager = null;

    /**
     * @param menuManager MenuManager.
     */
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    /**
     * 生成树.
     *
     * @param type 树的类型
     * @param roleId 角色id
     * @param menuId 菜单id 这个参数我还不知道有什么用
     * @return Object 结果是个字符串
     */
    public Object creator(int type, Long roleId, Long menuId) {
        List list = menuManager.loadTops("seq", "asc");
        StringBuffer sb = new StringBuffer();

        for (int k = 0; k < list.size(); k++) {
            Menu menu = (Menu) list.get(k);
            sb.append(buildTree(menu, type, roleId, menuId));
        }

        return sb.toString();
    }

    /**
     * 根据menu建造一棵树.
     * 以此菜单为顶级菜单，与它直接关联的生成二级菜单
     *
     * @param menu 菜单
     * @param type 树类型
     * @param roleId 角色id
     * @param menuId 菜单id 不知道有什么用
     * @return String 结果字符串
     */
    private String buildTree(Menu menu, int type, Long roleId, Long menuId) {
        StringBuffer sb = new StringBuffer();

        String[] args = getArgs(menu, type, roleId, menuId);

        sb.append(tree.format(args));

        Set set = menu.getChildren();
        Iterator i = set.iterator();

        while (i.hasNext()) {
            Menu temp = (Menu) i.next();
            sb.append(buildTree(temp, type, roleId, menuId));
        }

        sb.append("</div>");

        return sb.toString();
    }

    /**
     * 生成模板需要使用的参数.
     *
     * @param menu 菜单
     * @param type 树的类型
     * @param roleId 角色id
     * @param menuId 菜单id
     * @return 保存参数的String array
     */
    private String[] getArgs(Menu menu, int type, Long roleId, Long menuId) {
        String[] args = new String[4];

        args[0] = menu.getName();
        args[1] = String.valueOf(menu.getId());
        args[2] = "false";

        if (type == CHECKBOX_TREE) {
            if (roleId != null) {
                try {
                    for (Role role : menu.getRoles()) {
                        if (role.getId().equals(roleId)) {
                            args[2] = "true";
                        }
                    }
                } catch (ObjectRetrievalFailureException e) {
                    logger.error(e);
                }
            }
        } else {
            if ((menuId != null) && (menuId.equals(menu.getId()))) {
                args[2] = "true";
            }
        }

        args[3] = String.valueOf(type);

        return args;
    }
}
