package anni.asecurity.web;

import java.util.List;

import anni.asecurity.domain.Resource;
import anni.asecurity.domain.Role;

import anni.asecurity.manager.ResourceManager;
import anni.asecurity.manager.RoleManager;

import anni.core.web.prototype.BaseLongController;
import anni.core.web.prototype.StreamView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.util.WebUtils;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class RoleController extends BaseLongController<Role, RoleManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(RoleController.class);

    /** * resource manager. */
    private ResourceManager resourceManager = null;

    /** * constructor. */
    public RoleController() {
        setEditView("/asecurity/role/editRole");
        setListView("/asecurity/role/listRole");
    }

    /** * @param resourceManager Resource Manager. */
    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    /**
     * 显示角色对应的资源列表.
     *
     * @throws Exception 异常
     */
    public void selectResources() throws Exception {
        // mv.setViewName("/admin/selectResources");
        Long roleId = getLongParam("roleId", 0L);

        if (roleId != 0L) {
            WebUtils.setSessionAttribute(request, "roleId", roleId);
        }

        Long id = (Long) WebUtils.getRequiredSessionAttribute(request,
                "roleId");
        List<Resource> resources = resourceManager.getAll();

        Role role = getEntityDao().get(id);

        if (role != null) {
            for (Resource resource : resources) {
                if (role.getResources().contains(resource)) {
                    resource.setAuthorized(true);
                }
            }
        }

        mv.addObject("role", role);
        mv.addObject("resources", resources);
        mv.setViewName("/asecurity/role/selectResources");
    }

    /**
     * 授权与撤消授权.
     *
     * @throws Exception 异常
     */
    public void authResources() throws Exception {
        mv.setViewName("forward:/role/selectResources.htm");

        boolean auth = getBooleanParam("auth", false);

        // String[] itemlist = StringUtils.split(getStrParam("itemlist", ""),
        //         ",");
        String[] itemlist = getStrParams("itemlist");

        Long roleId = (Long) WebUtils.getRequiredSessionAttribute(request,
                "roleId");
        Role role = getEntityDao().get(roleId);

        if (role != null) {
            if (auth) {
                for (String id : itemlist) {
                    Resource resource = resourceManager.get(Long.valueOf(
                                id));

                    if (!role.getResources().contains(resource)) {
                        role.getResources().add(resource);
                    }
                }
            } else {
                for (String id : itemlist) {
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
            for (String id : itemlist) {
                Resource resource = resourceManager.get(Long.valueOf(id));
                logger.info(auth);
                getEntityDao().saveRoleInCache(resource);
            }
        }
    }

    /** * index. */
    public void index() {
        mv.setViewName("asecurity/role/index");
    }

    /**
     * onInsert.
     *
     * @throws Exception 写入response可能发生异常
     */
    @Override
    public void onInsert() throws Exception {
        logger.info(params());
        response.getWriter().print("{success:true,info:\"success\"}");
        mv.setView(new StreamView("application/json"));
    }

    /**
         * onUpdate.
     *
     * @throws Exception 写入response可能发生异常
         */
    @Override
    public void onUpdate() throws Exception {
        logger.info(params());
        response.getWriter().print("{success:true,info:\"success\"}");
        mv.setView(new StreamView("application/json"));
    }
}
