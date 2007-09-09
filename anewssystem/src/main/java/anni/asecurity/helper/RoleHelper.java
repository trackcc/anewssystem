package anni.asecurity.helper;

import java.util.List;
import java.util.Map;

import anni.asecurity.domain.Resource;
import anni.asecurity.domain.Role;
import anni.asecurity.domain.User;

import anni.asecurity.manager.ResourceManager;
import anni.asecurity.manager.RoleManager;
import anni.asecurity.manager.UserManager;

import anni.core.dao.support.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 角色辅助类.
 *
 * @author Lingo
 * @since 2007-09-09
 */
public class RoleHelper {
    /** * logger. */
    private Log logger = LogFactory.getLog(RoleHelper.class);

    /** * resourceManager. */
    private ResourceManager resourceManager = null;

    /** * userManager. */
    private UserManager userManager = null;

    /** * roleManager. */
    private RoleManager roleManager = null;

    /** * @param userManager UserManager. */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /** * @param resourceManager ResourceManager. */
    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    /** * @param roleManager RoleManager. */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /**
     * 根据条件，分页查询.
     *
     * @param conditions 条件
     * @return 分页结果
     */
    public Page pagedQuery(Map conditions) {
        logger.info(conditions);

        int start = 0;
        int pageSize = 10;
        int pageNo = (start / pageSize) + 1;

        try {
            start = Integer.parseInt(conditions.get("start").toString());
            pageSize = Integer.parseInt(conditions.get("limit").toString());
            pageNo = (start / pageSize) + 1;
        } catch (Exception ex) {
            logger.info(ex);
        }

        return roleManager.pagedQuery("from Role", pageNo, pageSize);
    }

    /**
     * 根据用户，分页查询角色.
     *
     * @param conditions 条件
     * @return 分页结果
     */
    public Page getRoleForUserPage(Map conditions) {
        long userId = -1L;

        try {
            userId = Long.parseLong(conditions.get("userId").toString());
        } catch (Exception ex) {
            logger.info(ex);
        }

        User user = userManager.get(userId);

        Page page = pagedQuery(conditions);
        List<Role> list = (List<Role>) page.getResult();

        if (user != null) {
            for (Role role : list) {
                if (user.getRoles().contains(role)) {
                    role.setAuthorized(true);
                }
            }
        }

        return page;
    }

    /**
     * 根据角色id，授权或取消授权资源.
     *
     * @param roleId 角色id
     * @param resourceId 资源id
     * @param isCancel 是否取消
     */
    public void authResourceForRole(long roleId, long resourceId,
        boolean isCancel) {
        logger.info(roleId);
        logger.info(resourceId);
        logger.info(isCancel);

        Resource resource = resourceManager.get(resourceId);
        Role role = roleManager.get(roleId);

        if ((role != null) && (resource != null)) {
            if (isCancel) {
                // 取消授权
                if (role.getResources().contains(resource)) {
                    role.getResources().remove(resource);
                }
            } else {
                // 授权
                if (!role.getResources().contains(resource)) {
                    role.getResources().add(resource);
                }
            }

            roleManager.save(role);
            roleManager.flush();
            roleManager.saveRoleInCache(resource);
        }
    }
}
