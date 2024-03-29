package anni.core.dao;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import anni.core.page.Page;

import anni.core.utils.BeanUtils;

import org.apache.commons.beanutils.PropertyUtils;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.hibernate.impl.CriteriaImpl;

import org.hibernate.metadata.ClassMetadata;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;


/**
 * Hibernate Dao的泛型基类.
 * <p/>
 * 继承于Spring的<code>HibernateDaoSupport</code>,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 *
 * @author calvin
 * @author tin
 * @see HibernateDaoSupport
 * @see HibernateEntityDao
 */
@SuppressWarnings("unchecked")
public class HibernateGenericDao extends HibernateDaoSupport {
    /**
     * 根据ID获取对象.
     * 实际调用Hibernate的session.get()方法返回实体或其proxy对象.
     * 如果对象不存在，返回null.
     *
     * @param entityClass 实体类型
     * @param id 主键
     * @param <T> 实体类型
     * @return 获取的对象
     */
    public <T> T get(Class<T> entityClass, Serializable id) {
        return (T) getHibernateTemplate().get(entityClass, id);
    }

    /**
     * 根据ID获取对象.
     * 实际调用Hibernate的session.load()方法返回实体或其proxy对象.
     * 网上有的地方说，对象不存在就抛出ObjectNotFindException，但实际使用的时候总是返回proxy。
     * 而在是具体使用的时候会出问题，根本没办法判断这个id是否存在，真是没办法。
     *
     * @param entityClass 实体类型
     * @param id 主键
     * @param <T> 实体类型
     * @return 获取的对象
     */
    public <T> T load(Class<T> entityClass, Serializable id) {
        return (T) getHibernateTemplate().load(entityClass, id);
    }

    /**
     * 获取全部对象.
     *
     * @param entityClass 实体类型
     * @param <T> 实体类型
     * @return 所有对象列表
     */
    public <T> List<T> getAll(Class<T> entityClass) {
        return getHibernateTemplate().loadAll(entityClass);
    }

    /**
     * 获取全部对象,带排序字段与升降序参数.
     *
     * @param entityClass 实体类型
     * @param orderBy 排序字段
     * @param isAsc 是否正序排列
     * @param <T> 实体类型
     * @return 所有对象列表
     */
    public <T> List<T> getAll(Class<T> entityClass, String orderBy,
        boolean isAsc) {
        Assert.hasText(orderBy);

        if (isAsc) {
            return getHibernateTemplate()
                       .findByCriteria(DetachedCriteria.forClass(
                    entityClass).addOrder(Order.asc(orderBy)));
        } else {
            return getHibernateTemplate()
                       .findByCriteria(DetachedCriteria.forClass(
                    entityClass).addOrder(Order.desc(orderBy)));
        }
    }

    /**
     * 保存对象.
     *
     * @param o 保存的对象
     */
    public void save(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    /**
     * 删除对象.
     *
     * @param o 删除的对象
     */
    public void remove(Object o) {
        getHibernateTemplate().delete(o);
    }

    /**
     * 根据ID删除对象.
     *
     * @param entityClass 删除对象的类型
     * @param id 主键
     * @param <T> 删除对象的类型
     */
    public <T> void removeById(Class<T> entityClass, Serializable id) {
        remove(load(entityClass, id));
    }

    /**
     * 删除所有对象.
     *
     * @param all 删除对象的集合
     * @param <T> 删除对象的类型
     */
    public <T> void removeAll(Collection<T> all) {
        getHibernateTemplate().deleteAll(all);
    }

    /** * 刷新所有等待的保存，更新，删除操作，更新数据库. */
    public void flush() {
        getHibernateTemplate().flush();
    }

    /** * 删除Session缓存里的所有对象，取消所有等待的保存，更新，删除操作. */
    public void clear() {
        getHibernateTemplate().clear();
    }

    /**
     * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
     * 留意可以连续设置,如下：
     * <pre>
     * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
     * </pre>
     * 调用方式如下：
     * <pre>
     *        dao.createQuery(hql)
     *        dao.createQuery(hql,arg0);
     *        dao.createQuery(hql,arg0,arg1);
     *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
     * </pre>
     *
     * @param hql 查询语句
     * @param values 可变参数
     * @return 返回Query
     */
    public Query createQuery(String hql, Object... values) {
        Assert.hasText(hql);

        Query query = getSession().createQuery(hql);

        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }

        return query;
    }

    /**
     * 创建Criteria对象.
     *
     * @param entityClass 实体类型
     * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
     * @param <T> 实体类型
     * @return 构造的Criteria
     */
    public <T> Criteria createCriteria(Class<T> entityClass,
        Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);

        for (Criterion c : criterions) {
            criteria.add(c);
        }

        return criteria;
    }

    /**
     * 创建Criteria对象，带排序字段与升降序字段.
     *
     * @see #createCriteria(Class,Criterion[])
     *
     * @param entityClass 实体类型
     * @param orderBy 排序字段
     * @param isAsc 是否正序排列
     * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
     * @param <T> 实体类型
     * @return 构造的Criteria
     */
    public <T> Criteria createCriteria(Class<T> entityClass,
        String orderBy, boolean isAsc, Criterion... criterions) {
        Assert.hasText(orderBy);

        Criteria criteria = createCriteria(entityClass, criterions);

        if (isAsc) {
            criteria.addOrder(Order.asc(orderBy));
        } else {
            criteria.addOrder(Order.desc(orderBy));
        }

        return criteria;
    }

    /**
     * 根据hql查询,直接使用HibernateTemplate的find函数.
     *
     * @param hql 查询语句
     * @param values 可变参数,见{@link #createQuery(String,Object...)}
     * @return 查询结果
     */
    public List find(String hql, Object... values) {
        Assert.hasText(hql);

        return getHibernateTemplate().find(hql, values);
    }

    /**
     * 根据属性名和属性值查询对象.
     *
     * @param entityClass 实体类型
     * @param propertyName 属性名
     * @param value 属性值
     * @param <T> 实体类型
     * @return 符合条件的对象列表
     */
    public <T> List<T> findBy(Class<T> entityClass, String propertyName,
        Object value) {
        Assert.hasText(propertyName);

        return createCriteria(entityClass,
            Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 根据属性名和属性值查询对象,带排序参数.
     *
     * @param entityClass 实体类型
     * @param propertyName 属性名
     * @param value 属性值
     * @param orderBy 排序字段
     * @param isAsc 是否正序
     * @param <T> 实体类型
     * @return 符合条件的对象列表
     */
    public <T> List<T> findBy(Class<T> entityClass, String propertyName,
        Object value, String orderBy, boolean isAsc) {
        Assert.hasText(propertyName);
        Assert.hasText(orderBy);

        return createCriteria(entityClass, orderBy, isAsc,
            Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 根据属性名和属性值查询唯一对象.
     *
     * @param entityClass 实体类型
     * @param propertyName 属性名
     * @param value 属性值
     * @param <T> 实体类型
     * @return 符合条件的唯一对象 or null if not found.
     */
    public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
        Object value) {
        Assert.hasText(propertyName);

        return (T) createCriteria(entityClass,
            Restrictions.eq(propertyName, value)).uniqueResult();
    }

    /**
     * 分页查询函数，使用hql.
     *
     * FIXME: 如果出现group by，having子句，分页就会出现问题
     * @param hql 查询语句
     * @param pageNo 页号,从1开始
     * @param pageSize 每页包含多少条记录
     * @param values 不定参数，hql需要的参数
     * @return 分页结果
     */
    public Page pagedQuery(String hql, int pageNo, int pageSize,
        Object... values) {
        Assert.hasText(hql);
        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

        // Count查询
        String countQueryString = " select count (*) "
            + removeSelect(removeOrders(hql));
        List countlist = getHibernateTemplate()
                             .find(countQueryString, values);
        long totalCount = (Long) countlist.get(0);

        if (totalCount < 1) {
            return new Page();
        }

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        Query query = createQuery(hql, values);
        List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
                         .list();

        return new Page(startIndex, totalCount, pageSize, list);
    }

    /**
     * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
     *
     * @param criteria 查询条件
     * @param pageNo 页号,从1开始
     * @param pageSize 每页包含多少条记录
     * @return 含总记录数和当前页数据的Page对象
     */
    public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
        Assert.notNull(criteria);
        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

        CriteriaImpl impl = (CriteriaImpl) criteria;

        // 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
        Projection projection = impl.getProjection();
        List<CriteriaImpl.OrderEntry> orderEntries;

        try {
            orderEntries = (List) BeanUtils.forceGetProperty(impl,
                    "orderEntries");
            BeanUtils.forceSetProperty(impl, "orderEntries",
                new ArrayList());
        } catch (NoSuchFieldException e) {
            throw new InternalError(
                " Runtime Exception impossibility throw ");
        } catch (IllegalAccessException e) {
            throw new InternalError(
                " Runtime Exception impossibility throw ");
        }

        // 执行查询
        int totalCount = (Integer) criteria.setProjection(Projections
                .rowCount()).uniqueResult();

        // 将之前的Projection和OrderBy条件重新设回去
        criteria.setProjection(projection);

        if (projection == null) {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }

        try {
            BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
        } catch (NoSuchFieldException e) {
            throw new InternalError(
                " Runtime Exception impossibility throw ");
        } catch (IllegalAccessException e) {
            throw new InternalError(
                " Runtime Exception impossibility throw ");
        }

        // 返回分页对象
        if (totalCount < 1) {
            return new Page();
        }

        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List list = criteria.setFirstResult(startIndex)
                            .setMaxResults(pageSize).list();

        return new Page(startIndex, totalCount, pageSize, list);
    }

    /**
     * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
     *
     * @param entityClass 实体类型
     * @param pageNo 页号,从1开始
     * @param pageSize 每页包含多少条记录
     * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
     * @return 含总记录数和当前页数据的Page对象
     */
    public Page pagedQuery(Class entityClass, int pageNo, int pageSize,
        Criterion... criterions) {
        Criteria criteria = createCriteria(entityClass, criterions);

        return pagedQuery(criteria, pageNo, pageSize);
    }

    /**
     * 分页查询函数，根据entityClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
     *
     * @param entityClass 实体类型
     * @param pageNo 页号,从1开始
     * @param pageSize 每页包含多少条记录
     * @param orderBy 排序字段
     * @param isAsc 是否正序
     * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
     * @return 含总记录数和当前页数据的Page对象
     */
    public Page pagedQuery(Class entityClass, int pageNo, int pageSize,
        String orderBy, boolean isAsc, Criterion... criterions) {
        Criteria criteria = createCriteria(entityClass, orderBy, isAsc,
                criterions);

        return pagedQuery(criteria, pageNo, pageSize);
    }

    /**
     * 判断对象某些属性的值在数据库中是否唯一.
     *
     * @param entityClass 实体类型
     * @param entity 判断的对象
     * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
     * @param <T> 实体类型
     * @return 是否唯一
     */
    public <T> boolean isUnique(Class<T> entityClass, Object entity,
        String uniquePropertyNames) {
        Assert.hasText(uniquePropertyNames);

        Criteria criteria = createCriteria(entityClass)
                                .setProjection(Projections.rowCount());
        String[] nameList = uniquePropertyNames.split(",");

        try {
            // 循环加入唯一列
            for (String name : nameList) {
                logger.fatal(name);
                logger.fatal(PropertyUtils.getProperty(entity, name));
                criteria.add(Restrictions.eq(name,
                        PropertyUtils.getProperty(entity, name)));
            }

            // 以下代码为了如果是update的情况,排除entity自身.
            String idName = getIdName(entityClass);

            // 取得entity的主键值
            Serializable id = getId(entityClass, entity);

            // 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
            if (id != null) {
                criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
            }
        } catch (NoSuchMethodException e) {
            ReflectionUtils.handleReflectionException(e);
        } catch (IllegalAccessException e) {
            ReflectionUtils.handleReflectionException(e);
        } catch (InvocationTargetException e) {
            ReflectionUtils.handleReflectionException(e);
        }

        logger.fatal(criteria);
        logger.fatal(criteria.uniqueResult());

        return (Integer) criteria.uniqueResult() == 0;
    }

    /**
     * 取得对象的主键值,辅助函数.
     *
     * @param entityClass 实体类型
     * @param entity 实体对象
     * @return 返回主键值
     * @throws NoSuchMethodException 如果没有id的getter方法
     * @throws IllegalAccessException 如果没有权限访问
     * @throws InvocationTargetException 如果调用过程出错
     */
    public Serializable getId(Class entityClass, Object entity)
        throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        Assert.notNull(entity);
        Assert.notNull(entityClass);

        return (Serializable) PropertyUtils.getProperty(entity,
            getIdName(entityClass));
    }

    /**
     * 取得对象的主键名,辅助函数.
     *
     * @param clazz 类型
     * @return 主键字段名称
     */
    public String getIdName(Class clazz) {
        Assert.notNull(clazz);

        ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
        Assert.notNull(meta,
            "Class " + clazz + " not define in hibernate session factory.");

        String idName = meta.getIdentifierPropertyName();
        Assert.hasText(idName,
            clazz.getSimpleName() + " has no identifier property define.");

        return idName;
    }

    /**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     *
     * @param hql 原始查询语句
     * @return 删除掉select子句的查询语句
     */
    private static String removeSelect(String hql) {
        Assert.hasText(hql);

        int beginPos = hql.toLowerCase(Locale.CHINA).indexOf("from");
        Assert.isTrue(beginPos != -1,
            " hql : " + hql + " must has a keyword 'from'");

        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     *
     * @param hql 原始查询语句
     * @return 删除掉select子句的查询语句
     */
    private static String removeOrders(String hql) {
        Assert.hasText(hql);

        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            m.appendReplacement(sb, "");
        }

        m.appendTail(sb);

        return sb.toString();
    }

    /**
     * 消除与 Hibernate Session 的关联.
     * @param entity 实体对象
     */
    public void evit(Object entity) {
        getHibernateTemplate().evict(entity);
    }

    /**
     * 获得完成初始化的实体beans.
     *
     * @param clz 类型
     * @param id 主键
     * @param <T> 实体类型
     * @return T 实体bean
     */
    public <T> T initialize(Class<T> clz, Serializable id) {
        T entity = get(clz, id);
        Hibernate.initialize(entity);

        return entity;
    }

    /**
     * 获得总数.
     * @param clz 实体类
     * @param <T> 实体类型
     * @return count
     */
    public <T> Long getCount(Class<T> clz) {
        Criteria c = getSession().createCriteria(clz)
                         .setProjection(Projections.rowCount());

        return (Long) c.uniqueResult();
    }

    /**
     * 获得总数.
     * @param clz 实体类
     * @param name 字段名称
     * @param value 字段值
     * @param <T> 实体类型
     * @return count
     */
    public <T> Long getCount(Class<T> clz, String name, Object value) {
        Criteria c = getSession().createCriteria(clz)
                         .add(Restrictions.eq(name, value))
                         .setProjection(Projections.rowCount());

        return (Long) c.uniqueResult();
    }

    /**
     * 获得总数.
     * @param hql 查询语句
     * @return count
     */
    public Long getCount(String hql) {
        return (Long) getHibernateTemplate().find(hql).get(0);
    }

    /**
     * 获得总数.
     * @param hql 查询语句
     * @param values 参数
     * @return count
     */
    public Long getCount(String hql, Object... values) {
        return (Long) getHibernateTemplate().find(hql, values).get(0);
    }
}
