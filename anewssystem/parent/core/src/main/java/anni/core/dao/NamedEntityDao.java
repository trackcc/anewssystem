package anni.core.dao;

import anni.core.domain.NamedEntityBean;


/**
 * 专门对实现了NamedEntityBean进行操作的dao接口.
 *
 * @author Lingo
 * @since 2007-06-06
 * @param <T> Named实体类
 */
public interface NamedEntityDao<T extends NamedEntityBean>
    extends EntityDao<T> {
    /**
     * 如果数据库中不存在指定name的记录，就将此条记录插入数据库，并返回对应的实体类.
     * 如果数据库中已经存在指定name的记录，就返回对应的实体类
     *
     * @param name 数据字典的name
     * @return T 返回对应的实体类
     */
    T createOrGet(String name);
}
