package anni.core.dao;

import java.io.Serializable;

import java.util.List;

import anni.core.page.Page;

import anni.core.utils.GenericsUtils;

import org.hibernate.Criteria;

import org.hibernate.criterion.Criterion;


/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * <pre>
 * public class UserManager extends HibernateEntityDao&lt;User&gt; {
 * }
 * </pre>
 *
 * @author calvin
 * @see HibernateGenericDao
 * @param <T> 实体类型
 */
@SuppressWarnings("unchecked")
public class HibernateEntityDao<T> extends HibernateGenericDao {
    /** * DAO所管理的Entity类型. */
    protected Class<T> entityClass;

    /**
     * 在构造函数中将泛型T.class赋给entityClass.
     */
    public HibernateEntityDao() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 取得entityClass.JDK1.4不支持泛型的子类可以抛开Class&lt;T&gt; entityClass,重载此函数达到相同效果.
     *
     * @return 使用的实体类型
     */
    protected Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 根据ID获取对象.
     *
     * @see HibernateGenericDao#get(Class,Object)
     * @param id 主键
     * @return 对象，可能为null
     */
    public T get(Serializable id) {
        return get(getEntityClass(), id);
    }

    /**
     * 返回的是proxy，如果数据库中不存在对应id，在调用过程中会报错.
     * 必须确定id存在才能使用。
     *
     * @see HibernateGenericDao#load(Class,Object)
     * @param id 主键
     * @return 返回对象
     */
    public T load(Serializable id) {
        return load(getEntityClass(), id);
    }

    /**
     * 获取全部对象.
     *
     * @see HibernateGenericDao#getAll(Class)
     * @return 所有对象
     */
    public List<T> getAll() {
        return getAll(getEntityClass());
    }

    /**
     * 获取全部对象,带排序参数.
     *
     * @see HibernateGenericDao#getAll(Class,String,boolean)
     * @param orderBy 排序字段
     * @param isAsc 是否正序
     * @return 所有对象
     */
    public List<T> getAll(String orderBy, boolean isAsc) {
        return getAll(getEntityClass(), orderBy, isAsc);
    }

    /**
     * 根据ID移除对象.
     *
     * @see HibernateGenericDao#removeById(Class,Serializable)
     * @param id 主键
     */
    public void removeById(Serializable id) {
        removeById(getEntityClass(), id);
    }

    /**
     * 清空表.
     */
    public void removeAll() {
        removeAll(getAll());
    }

    /**
     * 取得Entity的Criteria.
     *
     * @see HibernateGenericDao#createCriteria(Class,Criterion[])
     * @param criterions 查询条件
     * @return 构造的criteria
     */
    public Criteria createCriteria(Criterion... criterions) {
        return createCriteria(getEntityClass(), criterions);
    }

    /**
     * 取得Entity的Criteria,带排序参数.
     *
     * @see HibernateGenericDao#createCriteria(Class,String,boolean,Criterion[])
     * @param orderBy 排序字段
     * @param isAsc 是否正序
     * @param criterions 查询条件
     * @return 构造的criteria
     */
    public Criteria createCriteria(String orderBy, boolean isAsc,
        Criterion... criterions) {
        return createCriteria(getEntityClass(), orderBy, isAsc, criterions);
    }

    /**
     * 根据属性名和属性值查询对象.
     *
     * @see HibernateGenericDao#findBy(Class,String,Object)
     * @param propertyName 属性名
     * @param value 属性值
     * @return 符合条件的对象列表
     */
    public List<T> findBy(String propertyName, Object value) {
        return findBy(getEntityClass(), propertyName, value);
    }

    /**
     * 根据属性名和属性值查询对象,带排序参数.
     *
     * @see HibernateGenericDao#findBy(Class,String,Object,String,boolean)
     * @param propertyName 属性名
     * @param value 属性值
     * @param orderBy 排序字段
     * @param isAsc 是否正序
     * @return 符合条件的对象列表
     */
    public List<T> findBy(String propertyName, Object value,
        String orderBy, boolean isAsc) {
        return findBy(getEntityClass(), propertyName, value, orderBy, isAsc);
    }

    /**
     * 分页查询.
     *
     * @param pageNo 当前页数
     * @param pageSize 每页显示几条记录
     * @return 分页结果
     */
    public Page pagedQuery(int pageNo, int pageSize) {
        return pagedQuery(createCriteria(getEntityClass()), pageNo,
            pageSize);
    }

    /**
     * 根据属性名和属性值查询单个对象.
     *
     * @see HibernateGenericDao#findUniqueBy(Class,String,Object)
     * @param propertyName 属性名
     * @param value 属性值
     * @return 符合条件的唯一对象 or null
     */
    public T findUniqueBy(String propertyName, Object value) {
        return findUniqueBy(getEntityClass(), propertyName, value);
    }

    /**
     * 判断对象某些属性的值在数据库中唯一.
     *
     * @param entity 判断对象
     * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
     * @return 是否唯一
     * @see HibernateGenericDao#isUnique(Class,Object,String)
     */
    public boolean isUnique(Object entity, String uniquePropertyNames) {
        return isUnique(getEntityClass(), entity, uniquePropertyNames);
    }

    /**
     * 获得完成初始化的实体beans.
     *
     * @param id 主键
     * @return T 实体bean
     */
    public T initialize(Serializable id) {
        return initialize(getEntityClass(), id);
    }

    /**
     * 获得总数.
     * @return count
     */
    public Long getCount() {
        return getCount(getEntityClass());
    }

    /**
     * 获得总数.
     * @param name 字段名称
     * @param value 字段值
     * @return count
     */
    public Long getCount(String name, Object value) {
        return getCount(getEntityClass(), name, value);
    }
}
