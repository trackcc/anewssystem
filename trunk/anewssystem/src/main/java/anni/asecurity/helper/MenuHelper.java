package anni.asecurity.helper;

import java.util.List;
import java.util.Map;

import anni.asecurity.domain.Menu;

import anni.asecurity.manager.MenuManager;

import anni.core.dao.support.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.util.ReflectionUtils;


/**
 * 使用dwr实现树的操作.
 *
 * @author Lingo
 * @since 2007-09-09
 */
public class MenuHelper {
    /** * logger. */
    private static Log logger = LogFactory.getLog(MenuHelper.class);

    /** * menuManager. */
    private MenuManager menuManager = null;

    /** * @param menuManager MenuManager. */
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    /** * 初始化菜单. */
    public List<Menu> init() {
        List<Menu> list = menuManager.find(
                "from Menu where parent is null order by theSort,id");

        return list;
    }

    public String[] getTags() {
        return new String[] {"id", "image", "name", "forward", "theSort"};
    }

    /**
     * listMenu.
     */
    public Page pagedQuery(Map conditions) {
        logger.info("start");

        logger.info(conditions);

        int start = 0;
        int pageSize = 15;
        int pageNo = (start / pageSize) + 1;

        try {
            start = Integer.parseInt(conditions.get("start").toString());
            pageSize = Integer.parseInt(conditions.get("limit").toString());
            pageNo = (start / pageSize) + 1;
        } catch (Exception ex) {
            logger.info(ex);
        }

        Page page = menuManager.pagedQuery("from Menu", pageNo, pageSize);

        return page;
    }

    /**
     * save.
     */
    public String save(long id, Menu menu) {
        Menu entity;
        boolean isUpdate = false;
        String str;
        String msg = "菜单失败！";

        entity = menuManager.get(id);

        if (entity == null) {
            str = "新增";
            entity = menu;
        } else {
            str = "修改";
            logger.info(entity);
            logger.info(menu);
            ReflectionUtils.shallowCopyFieldState(menu, entity);
        }

        menuManager.save(entity);
        msg = "菜单成功！";

        return str + msg;
    }

    /**
     * 删除
     * @param id
     */
    public String del(String ids) {
        boolean isDelete = false;
        String msg = "您选择的菜单删除成功！";

        for (String str : ids.split(",")) {
            long id = -1;

            try {
                id = Long.parseLong(str);
            } catch (NumberFormatException ex) {
                continue;
            }

            menuManager.removeById(id);
        }

        // msg = "您选择的菜单删除失败！";
        return msg;
    }
}
