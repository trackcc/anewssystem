package anni.asecurity.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import anni.asecurity.domain.Menu;
import anni.asecurity.domain.User;

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

    /**
     * 初始化菜单.
     *
     * @return List
     */
    public List<Menu> init() {
        List<Menu> list = menuManager.find(
                "from Menu where parent is null order by theSort,id");

        return list;
    }

    /**
     * @return String[] 列表显示的字段.
     */
    public String[] getTags() {
        return new String[] {"id", "image", "name", "forward", "theSort"};
    }

    /**
     * listMenu.
     *
     * @param conditions 查询条件
     * @return 分页结果
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
     *
     * @param id 主键
     * @param menu 需要保存的menu
     * @return 成功信息
     */
    public String save(long id, Menu menu) {
        Menu entity;

        // boolean isUpdate = false;
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
     * 删除.
     *
     * @param ids 需要删除的id组成的数组
     * @return 成功信息
     */
    public String del(String ids) {
        //boolean isDelete = false;
        String msg = "您选择的菜单删除成功！";

        for (String str : ids.split(",")) {
            try {
                long id = Long.parseLong(str);
                menuManager.removeById(id);
            } catch (NumberFormatException ex) {
                logger.info(ex);
            }
        }

        // msg = "您选择的菜单删除失败！";
        return msg;
    }

    /**
     * 根据登录用户，生成需要的菜单数据.
     *
     * @param session 需要从HttpSession里获得的登录用户的id
     * @return Map
     */
    public Map<String, List<MenuItem>> getMenus(HttpSession session) {
        List<Menu> list = menuManager.find(
                "from Menu where parent is null order by theSort,id");
        Map<String, List<MenuItem>> map = new LinkedHashMap<String, List<MenuItem>>();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return map;
        }

        List userMenus = menuManager.loadUserMenus(loginUser.getId());

        for (Menu menu : list) {
            if (!userMenus.contains(menu.getId())) {
                continue;
            }

            List<MenuItem> menuList = new ArrayList<MenuItem>();
            map.put(menu.getName(), menuList);

            for (Menu child : menu.getChildren()) {
                if (!userMenus.contains(child.getId())) {
                    continue;
                }

                MenuItem menuItem = new MenuItem();
                menuItem.setImage(child.getImage());
                menuItem.setTitle(child.getName());
                menuItem.setHref(child.getUrl());
                menuItem.setParentImg(menu.getImage());
                menuList.add(menuItem);
            }
        }

        return map;
    }

    /**
     * Accordion.js需要的数据结构.
     *
     */
    public static class MenuItem {
        /** * 图片. */
        private String image;

        /** * 标题. */
        private String title;

        /** * 链接. */
        private String href;

        /** * 上级菜单. */
        private String parentImg;

        /** * @return image. */
        public String getImage() {
            return image;
        }

        /** * @param image String. */
        public void setImage(String image) {
            this.image = image;
        }

        /** * @return title. */
        public String getTitle() {
            return title;
        }

        /** * @param title String. */
        public void setTitle(String title) {
            this.title = title;
        }

        /** * @return href. */
        public String getHref() {
            return href;
        }

        /** * @param href String. */
        public void setHref(String href) {
            this.href = href;
        }

        /** * @return parentImg. */
        public String getParentImg() {
            return parentImg;
        }

        /** * @param parentImg String. */
        public void setParentImg(String parentImg) {
            this.parentImg = parentImg;
        }
    }
}
