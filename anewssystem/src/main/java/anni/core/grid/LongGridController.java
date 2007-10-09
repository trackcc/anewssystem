package anni.core.grid;

import java.util.ArrayList;
import java.util.List;

import anni.core.dao.hibernate.HibernateEntityDao;
import anni.core.dao.support.Page;

import anni.core.json.JsonController;
import anni.core.json.JsonUtils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


/**
 * 操作表格的基类.
 *
 * @author Lingo
 * @since 2007-09-19
 * @param <T> LongGridBean的子类
 * @param <D> HibernateEntityDao的子类
 */
public class LongGridController<T extends LongGridBean, D extends HibernateEntityDao<T>>
    extends JsonController<T, D> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(LongGridController.class);

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

        Criteria criteria;

        if (sort != null) {
            boolean isAsc = dir.equalsIgnoreCase("asc");
            criteria = getEntityDao().createCriteria(sort, isAsc);
        } else {
            criteria = getEntityDao().createCriteria();
        }

        if ((!filterTxt.equals("")) && (!filterValue.equals(""))) {
            criteria = criteria.add(Restrictions.like(filterTxt,
                        "%" + filterValue + "%"));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 使用json绑定pojo，再将绑定的pojo返回.
     *
     * @return T entity
     * @throws Exception 解析时，可能抛出异常
     */
    protected T bindObject() throws Exception {
        String data = getStrParam("data", null);
        JSONObject jsonObject = JSONObject.fromObject(data);
        T entity = null;

        try {
            long id = jsonObject.getLong("id");
            entity = getEntityDao().get(id);
        } catch (JSONException ex) {
            logger.info(ex);
        }

        if (entity == null) {
            logger.info("insert");
            // 新增
            entity = JsonUtils.json2Bean(jsonObject, getEntityClass(),
                    getExcludes(), getDatePattern());
            entity.setId(null);
        } else {
            logger.info("update");
            // 修改
            JsonUtils.json2Bean(jsonObject, entity, getExcludes(),
                getDatePattern());
        }

        return entity;
    }

    /**
     * 保存，新增或修改.
     *
     * @throws Exception 异常
     */
    public void save() throws Exception {
        logger.info(params());

        T entity = bindObject();

        getEntityDao().save(entity);
        response.getWriter().print("{success:true}");
    }

    /**
     * 读取数据.
     *
     * @throws Exception 异常
     */
    public void loadData() throws Exception {
        logger.info(params());

        long id = getLongParam("id", 0L);
        T entity = getEntityDao().get(id);

        if (entity != null) {
            List<T> list = new ArrayList<T>();
            list.add(entity);
            JsonUtils.write(list, response.getWriter(), getExcludes(),
                getDatePattern());
        }
    }

    /**
     * 删除记录.
     *
     * @throws Exception 异常
     */
    public void remove() throws Exception {
        logger.info(params());

        String ids = getStrParam("ids", "");

        for (String str : ids.split(",")) {
            try {
                long id = Long.parseLong(str);
                getEntityDao().removeById(id);
            } catch (NumberFormatException ex) {
                continue;
            }
        }

        response.getWriter().print("{success:true}");
    }

    /** * @return excludes. */
    public String[] getExcludes() {
        return new String[0];
    }
}
