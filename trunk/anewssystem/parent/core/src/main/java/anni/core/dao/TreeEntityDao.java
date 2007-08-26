package anni.core.dao;

import java.util.List;

import anni.core.domain.tree.TreeEntityBean;


/**
 * 专门对实现了TreeEntityBean进行操作的dao接口.
 *
 * @author Lingo
 * @since 2007-06-06
 * @param <T> 树形实体类
 */
public interface TreeEntityDao<T extends TreeEntityBean>
    extends NamedEntityDao<T> {
    /**
     * 读取顶端的队列.
     *
     * @return List.
     */
    public List<T> loadTops();

    /**
     * 读取顶级树节点.
     *
     * @param columnName 列名
     * @param sort asc/desc
     * @return List
     */
    public List<T> loadTops(String columnName, String sort);
}
