package anni.core.dao;

import java.util.List;

import anni.core.domain.NamedEntityBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 实现Named实体类操作的hibernate实现.
 *
 * @author Lingo
 * @since 2007-06-06
 * @param <T> Named实体类
 */
public class HibernateNamedEntityDao<T extends NamedEntityBean>
    extends ECHibernateEntityDao<T> implements NamedEntityDao<T> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(HibernateNamedEntityDao.class);

    /**
     * 如果数据库中不存在指定name的记录，就将此条记录插入数据库，并返回对应的实体类.
     * 如果数据库中已经存在指定name的记录，就返回对应的实体类
     *
     * @param name 数据字典的name
     * @return T 返回对应的实体类
     */
    public T createOrGet(String name) {
        // 输入的名称不应该为空.
        if ((name == null) || "".equals(name)) {
            return null;
        }

        // 根据名称查找记录
        //String hql = "from " + getEntityClass().getName()
        //    + " where name=?";
        //logger.trace(hql);

        //getSession().clear();
        List<T> list = findBy("name", name);

        // 如果找到记录，应该是只有一项目，就返回这条记录的实体类
        if (list.size() > 0) {
            return list.get(0);
        } else {
            try {
                // 如果没找到记录，需要把这个数据字典保存进数据库
                T namedEntityBean = (T) getEntityClass().newInstance();
                namedEntityBean.setName(name);
                save(namedEntityBean);

                return namedEntityBean;
            } catch (InstantiationException ex) {
                System.err.println(ex);
            } catch (IllegalAccessException ex) {
                System.err.println(ex);
            }

            return null;
        }
    }
}
