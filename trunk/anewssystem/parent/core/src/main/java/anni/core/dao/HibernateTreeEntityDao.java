package anni.core.dao;

import java.util.List;

import anni.core.domain.tree.TreeEntityBean;


/**
 * 实现Named实体类操作的hibernate实现.
 *
 * @author Lingo
 * @since 2007-06-06
 * @param <T> Named实体类
 */
public class HibernateTreeEntityDao<T extends TreeEntityBean<T>>
    extends HibernateNamedEntityDao<T> implements TreeEntityDao<T> {
    /**
     * 读取顶端的队列.
     *
     * @return List.
     */
    public List<T> loadTops() {
        return find("from " + getEntityClass().getName()
            + " where parent is null");
    }

    /**
     * 读取顶级树节点.
     *
     * @param columnName 列名
     * @param sort asc/desc
     * @return List
     */
    public List<T> loadTops(String columnName, String sort) {
        return find("from " + getEntityClass().getName()
            + " where parent is null order by " + columnName + " " + sort);
    }
}
