package anni.core.web.prototype;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import anni.core.page.Page;


/**
 * 针对单个Entity对象的操作定义.不依赖于具体ORM实现方案.
 * 来自www.springside.org.cn
 *
 * @author calvin
 * @since 2007-03-30
 * @version 1.0
 * @param <T> 实体类
 */
public interface EntityDao<T> {
    /**
     * 根据id获得实例.
     *
     * @param id 实现了Serializable接口的id
     * @return T 范型
     */
    T get(Serializable id);

    /**
     * 获得所有实例.
     *
     * @return List 实例列表
     */
    List<T> getAll();

    /**
     * 保存对象.
     *
     * @param o Object
     */
    void save(Object o);

    /**
     * 删除对象.
     *
     * @param o Object
     */
    void remove(Object o);

    /**
     * 根据id删除对象.
     *
     * @param id 实现了Serializable接口的id
     */
    void removeById(Serializable id);

    /**
     * 分页查找.
     *
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return Page
     */
    Page pagedQuery(int pageNo, int pageSize);

    /**
     * 根据过滤条件和排序条件，分页查找.
     *
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @param filterMap 过滤条件
     * @param sortMap 排序条件
     * @return Page
     */
    Page pagedQuery(int pageNo, int pageSize, Map filterMap, Map sortMap);
}
