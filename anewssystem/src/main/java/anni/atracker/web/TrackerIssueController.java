package anni.atracker.web;

import anni.atracker.domain.TrackerIssue;
import anni.atracker.domain.TrackerProject;

import anni.atracker.manager.TrackerIssueManager;
import anni.atracker.manager.TrackerProjectManager;

import anni.core.dao.support.Page;

import anni.core.grid.LongGridController;

import anni.core.json.JsonUtils;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


/**
 * @author Lingo.
 * @since 2007年10月07日 下午 18时23分59秒265
 */
public class TrackerIssueController extends LongGridController<TrackerIssue, TrackerIssueManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(TrackerIssueController.class);

    /** * trackerProjectManager. */
    private TrackerProjectManager trackerProjectManager = null;

    /** * @param trackerProjectManager TrackerProjectManager. */
    public void setTrackerProjectManager(
        TrackerProjectManager trackerProjectManager) {
        this.trackerProjectManager = trackerProjectManager;
    }

    /** * @throws Exception for json. */
    public void save() throws Exception {
        logger.info(params());

        String data = getStrParam("data", "");
        JSONObject jsonObject = JSONObject.fromObject(data);
        long projectId = jsonObject.getLong("trackerProject.id");
        TrackerProject trackerProject = trackerProjectManager.get(projectId);

        TrackerIssue entity = bindObject();
        entity.setTrackerProject(trackerProject);

        getEntityDao().save(entity);
        response.getWriter().print("{success:true}");
    }

    /**
     * 分页浏览记录.
     *
     * @throws Exception 异常
     */
    public void pagedQuery() throws Exception {
        logger.info(params());

        // 分页
        int pageSize = getIntParam("limit", 1);
        int start = getIntParam("start", 0);
        int pageNo = (start / pageSize) + 1;

        // 排序
        String sort = getStrParam("sort", null);
        String dir = getStrParam("dir", "asc");

        // 搜索
        String filterTxt = getStrParam("filterTxt", "").trim();
        String filterValue = getStrParam("filterValue", "").trim();

        // 根据项目id分类查询
        long projectId = getLongParam("projectId", -1L);

        Criteria criteria;

        if (sort != null) {
            boolean isAsc = dir.equalsIgnoreCase("asc");
            criteria = getEntityDao().createCriteria(sort, isAsc);
        } else {
            // 根据时间，倒序排列
            criteria = getEntityDao().createCriteria("updateDate", false);
            criteria.addOrder(Order.desc("id"));
        }

        if ((!filterTxt.equals("")) && (!filterValue.equals(""))) {
            criteria = criteria.add(Restrictions.like(filterTxt,
                        "%" + filterValue + "%"));
        }

        if (projectId != -1L) {
            criteria = criteria.add(Restrictions.eq("trackerProject.id",
                        projectId));
        }

        logger.info(criteria);

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {
            "hibernateLazyInitializer", "trackerIssues", "leaf",
            "allowEdit", "allowDelete", "theSort", "text", "summary"
        };
    }
}
