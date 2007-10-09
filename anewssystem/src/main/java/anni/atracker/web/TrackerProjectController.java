package anni.atracker.web;

import java.util.ArrayList;
import java.util.List;

import anni.atracker.domain.TrackerProject;

import anni.atracker.manager.TrackerProjectManager;

import anni.core.json.JsonController;
import anni.core.json.JsonUtils;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年10月07日 下午 18时23分59秒265
 */
public class TrackerProjectController extends JsonController<TrackerProject, TrackerProjectManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(TrackerProjectController.class);

    /** * @throws Exception for json. */
    public void getAllTree() throws Exception {
        logger.info("start");

        List<TrackerProject> list = getEntityDao()
                                        .find("from TrackerProject order by theSort asc,id desc");
        JsonUtils.write(list, response.getWriter(), getExcludes(),
            "yyyy-MM-dd");
    }

    /** * @throws Exception for json. */
    public void loadData() throws Exception {
        long id = getLongParam("id", -1L);
        TrackerProject entity = getEntityDao().get(id);

        if (entity != null) {
            List<TrackerProject> list = new ArrayList<TrackerProject>();
            list.add(entity);
            JsonUtils.write(list, response.getWriter(), getExcludes(),
                "yyyy-MM-dd");
        }
    }

    /** * @throws Exception for json. */
    public void insertTree() throws Exception {
        String data = getStrParam("data", "");
        TrackerProject node = JsonUtils.json2Bean(data,
                TrackerProject.class, getExcludes(), "yyyy-MM-dd");

        TrackerProject entity = getEntityDao().get(node.getId());

        if (entity == null) {
            // 添加
            // 只有在id = null的情况下，才执行insert，否则执行update
            node.setId(null);
            getEntityDao().save(node);
            entity = node;
        } else {
            // 修改
            entity.setName(node.getName());
            getEntityDao().save(entity);
        }

        response.getWriter()
                .print("{success:true,id:" + entity.getId() + "}");
    }

    /** * @throws Exception for json */
    public void removeTree() throws Exception {
        long id = getLongParam("id", -1L);

        if (id != -1L) {
            getEntityDao().removeById(id);
        }
    }

    /** * @throws Exception for json. */
    public void sortTree() throws Exception {
        String data = getStrParam("data", "");
        List<TrackerProject> list = JsonUtils.json2List(data,
                TrackerProject.class, getExcludes(), "yyyy-MM-dd");

        for (int i = 0; i < list.size(); i++) {
            TrackerProject node = list.get(i);
            Long id = node.getId();

            TrackerProject entity = getEntityDao().get(id);

            if (entity != null) {
                entity.setTheSort(i);
                getEntityDao().save(entity);
            }
        }
    }

    /** * @throws Exception for json. */
    public void updateTree() throws Exception {
        String data = getStrParam("data", "");
        JSONObject jsonObject = JSONObject.fromObject(data);

        TrackerProject entity = getEntityDao().get(jsonObject.getLong("id"));
        JsonUtils.json2Bean(jsonObject, entity, getExcludes(), "yyyy-MM-dd");

        getEntityDao().save(entity);

        response.getWriter().print("{success:true,info:\"success\"}");
    }

    /** * @return excludes. */
    public String[] getExcludes() {
        return new String[] {"trackerIssues", "theSort"};
    }
}
