package anni.asecurity.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import anni.asecurity.domain.Role;
import anni.asecurity.domain.User;

import anni.asecurity.manager.RoleManager;
import anni.asecurity.manager.UserManager;

import anni.core.dao.support.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UserHelper {
    /** * logger. */
    private Log logger = LogFactory.getLog(UserHelper.class);

    /** * roleManager. */
    private RoleManager roleManager = null;

    /** * userManager. */
    private UserManager userManager = null;

    /** * @param userManager UserManager. */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /** * @param roleManager RoleManager. */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public Page pagedQuery(Map conditions) {
        logger.info(conditions);

        int start = 0;
        int pageSize = 10;
        int pageNo = (start / pageSize) + 1;
        long deptId = 0L;

        try {
            start = Integer.parseInt(conditions.get("start").toString());
            pageSize = Integer.parseInt(conditions.get("limit").toString());
            pageNo = (start / pageSize) + 1;
            deptId = Long.parseLong(conditions.get("deptId").toString());
        } catch (Exception ex) {
            logger.info(ex);
        }

        Page page = null;

        if (deptId != 0L) {
            page = userManager.pagedQuery("from User where dept.id=?",
                    pageNo, pageSize, deptId);
        } else {
            page = userManager.pagedQuery("from User", pageNo, pageSize);
        }

        return page;
    }

    public Set<Role> getRolesForUser(long userId) {
        logger.info(userId);

        return userManager.get(userId).getRoles();
    }

    public Page getUsersPage(Map conditions) {
        return pagedQuery(conditions);
    }

    public void authRoleForUser(long userId, long roleId, boolean isCancel) {
        logger.info(userId);
        logger.info(roleId);
        logger.info(isCancel);

        User user = userManager.get(userId);
        Role role = roleManager.get(roleId);

        if (user != null) {
            if (isCancel) {
                // 取消授权
                if (user.getRoles().contains(role)) {
                    user.getRoles().remove(role);
                }
            } else {
                // 授权
                if (!user.getRoles().contains(role)) {
                    user.getRoles().add(role);
                }
            }

            userManager.save(user);
        }
    }
}
