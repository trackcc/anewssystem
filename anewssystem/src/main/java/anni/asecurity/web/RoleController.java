package anni.asecurity.web;

import java.util.List;

import anni.asecurity.domain.Resource;
import anni.asecurity.domain.Role;

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

    /** * @param resourceManager Resource Manager. */
    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
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
     * @return excludes.
     */
    @Override
    public String[] getExcludes() {
        return new String[] {"resources", "menus", "users"};
    }
}
