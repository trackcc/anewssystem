package anni.asecurity.helper;

import java.util.Map;
import java.util.Set;

import anni.asecurity.domain.Role;
import anni.asecurity.domain.User;

import anni.asecurity.manager.RoleManager;
import anni.asecurity.manager.UserManager;

import anni.core.dao.support.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 用户grid的帮助类.
 *
 * @author Lingo
 * @since 2007-09-09
 */
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

    /**
     * 分页查询.
     *
     * @param conditions 分页和查询条件
     * @return 分页查询的结果
     */
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

    /**
     * 根据用户id查询对应的角色.
     *
     * @param userId long
     * @return 角色集合
     */
    public Set<Role> getRolesForUser(long userId) {
        logger.info(userId);

        return userManager.get(userId).getRoles();
    }

    /**
     * FIXME:查询所有用户.
     *
     * @param conditions 分页和查询条件
     * @return 分页查询的结果
     */
    public Page getUsersPage(Map conditions) {
        return pagedQuery(conditions);
    }

    /**
     * 验证或取消验证.
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @param isCancel 是否是取消验证
     */
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
