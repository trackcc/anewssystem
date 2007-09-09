package anni.asecurity.helper;

import java.util.List;
import java.util.Map;

import anni.asecurity.domain.Resource;
import anni.asecurity.domain.Role;

import anni.asecurity.manager.ResourceManager;
import anni.asecurity.manager.RoleManager;

import anni.core.dao.support.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ��Դ������.
 *
 * @author Lingo
 * @since 2007-09-09
 */
public class ResourceHelper {
    /** * logger. */
    private Log logger = LogFactory.getLog(ResourceHelper.class);

    /** * resourceManager. */
    private ResourceManager resourceManager = null;

    /** * roleManager. */
    private RoleManager roleManager = null;

    /** * @param resourceManager ResourceManager. */
    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    /** * @param roleManager RoleManager. */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /**
     * ���������ҳ��ѯ.
     *
     * @param conditions ���
     * @return ��ҳ���
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

        return resourceManager.pagedQuery("from Resource", pageNo, pageSize);
    }

    /**
     * ��ݽ�ɫ����ҳ��ѯ��Դ.
     *
     * @param conditions ���
     * @return ��ҳ���
     */
    public Page getResourceForRolePage(Map conditions) {
        long roleId = -1L;

        try {
            roleId = Long.parseLong(conditions.get("id").toString());
        } catch (Exception ex) {
            logger.info(ex);
        }

        Page page = pagedQuery(conditions);
        List<Resource> list = (List<Resource>) page.getResult();
        Role role = roleManager.get(roleId);

        if (role != null) {
            for (Resource resource : list) {
                if (role.getResources().contains(resource)) {
                    resource.setAuthorized(true);
                }
            }
        }

        return page;
    }
}
