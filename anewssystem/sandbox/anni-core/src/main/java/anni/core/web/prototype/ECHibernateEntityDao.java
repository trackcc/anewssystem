package anni.core.web.prototype;

import java.util.List;
import java.util.Map;
import java.util.Set;

import anni.core.dao.HibernateEntityDao;

import anni.core.page.Page;

import org.hibernate.Query;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;


/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * <pre>
 * public class UserManager extends HibernateEntityDao&lt;User&lt; {
 * }
 * </pre>
 * 来自www.springside.org.cn
 *
 * @author calvin
 * @param <T> 实体类
 */
public class ECHibernateEntityDao<T> extends HibernateEntityDao<T>
    implements EntityDao<T> {
    /**
     * 分页查找.
     *
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return Page
     */
    public Page pagedQuery(int pageNo, int pageSize) {
        return pagedQuery(pageNo, pageSize, null, null);
    }

    /**
     * 根据过滤条件和排序条件，分页查找.
     *
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @param filterMap 过滤条件
     * @param sortMap 排序条件
     * @return Page
     */
    public Page pagedQuery(int pageNo, int pageSize, Map filterMap,
        Map sortMap) {
        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

        // Count查询
        String countQueryString = "select count (*) from "
            + getEntityClass().getName() + " t";
        String selectQueryString = "from " + getEntityClass().getName()
            + " t";
        countQueryString += makeFilter(filterMap);
        selectQueryString += (makeFilter(filterMap) + makeSort(sortMap));

        List countlist = getHibernateTemplate().find(countQueryString);
        long totalCount = (Long) countlist.get(0);

        if (totalCount < 1) {
            return new Page();
        }

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        Query query = getSession().createQuery(selectQueryString);
        List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
                         .list();

        return new Page(startIndex, totalCount, pageSize, list);
    }

    /**
     * 根据过滤条件，拼接hql需要的where语句.
     * name与value的关系是name like '%value'
     * 如果filterMap为空，返回空字符串，避免返回null
     *
     * @param filterMap 过滤条件
     * @return String 返回结果
     */
    protected String makeFilter(Map filterMap) {
        if (!CollectionUtils.isEmpty(filterMap)) {
            StringBuffer buff = new StringBuffer(" where ");

            for (Map.Entry entry : (Set<Map.Entry>) filterMap.entrySet()) {
                buff.append("t.").append(entry.getKey()).append(" like '%")
                    .append(entry.getValue()).append("%' and ");
            }

            buff.delete(buff.length() - " and ".length(), buff.length());

            return buff.toString();
        } else {
            return "";
        }
    }

    /**
     * 根据排序条件，拼接hql需要的order by语句.
     * 如果sortMap为空，返回空字符串，避免返回null
     *
     * @param sortMap 排序条件
     * @return String 返回结果
     */
    protected String makeSort(Map sortMap) {
        if (!CollectionUtils.isEmpty(sortMap)) {
            StringBuffer buff = new StringBuffer(" order by ");

            for (Map.Entry entry : (Set<Map.Entry>) sortMap.entrySet()) {
                buff.append("t.").append(entry.getKey()).append(" ")
                    .append(entry.getValue()).append(",");
            }

            buff.delete(buff.length() - 1, buff.length());

            return buff.toString();
        } else {
            return "";
        }
    }
}
