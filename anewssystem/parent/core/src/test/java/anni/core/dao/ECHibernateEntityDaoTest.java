package anni.core.dao;

import java.util.*;

import anni.core.dao.*;
import anni.core.dao.support.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.hibernate.*;

import org.hibernate.classic.Session;

import org.springframework.orm.hibernate3.HibernateTemplate;


/**
 * 演示了怎么用EasyMock完全模拟运行环境，测试dao.
 * 来自www.springside.org.cn
 *
 * @since 2007-03-14
 * @version 1.0
 */
public class ECHibernateEntityDaoTest extends TestCase {
    ECHibernateEntityDao hibernateEntityDao;
    SessionFactory mockSessionFactory;
    Session mockSession;
    Query mockQuery;
    HibernateTemplate mockHibernateTemplate;
    List mockList;
    Criteria mockCriteria;

    protected void setUp() {
        hibernateEntityDao = new ECHibernateEntityDao();
        mockSessionFactory = createMock(SessionFactory.class);
        mockSession = createMock(Session.class);
        mockQuery = createMock(Query.class);
        mockHibernateTemplate = EasyMock.createMock(HibernateTemplate.class);
        mockList = createMock(List.class);
        mockCriteria = createMock(Criteria.class);
    }

    public void testGet() {
        Object entity = new Object();

        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.getSessionFactory())
            .andReturn(mockSessionFactory);
        expect(mockSession.get(Object.class, 1L)).andReturn(entity);
        mockSession.flush();

        replay(mockSessionFactory);
        replay(mockSession);

        // start testing
        hibernateEntityDao.setHibernateTemplate(mockHibernateTemplate);
        hibernateEntityDao.setSessionFactory(mockSessionFactory);

        Object obj = hibernateEntityDao.get(1L);
        assertNotNull(obj);
        assertEquals(entity, obj);
    }

    public void testGetAll() {
        List list = new ArrayList<Object>();

        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.getSessionFactory())
            .andReturn(mockSessionFactory);
        expect(mockSession.createCriteria(Object.class))
            .andReturn(mockCriteria);
        mockSession.flush();
        expect(mockCriteria.list()).andReturn(list);

        replay(mockSessionFactory);
        replay(mockSession);
        replay(mockCriteria);

        // start testing
        hibernateEntityDao.setHibernateTemplate(mockHibernateTemplate);
        hibernateEntityDao.setSessionFactory(mockSessionFactory);

        List result = hibernateEntityDao.getAll();
        assertNotNull(result);
        assertEquals(list, result);
    }

    public void testSave() {
        Object obj = new Object();
        mockHibernateTemplate.saveOrUpdate(obj);
        EasyMock.replay(mockHibernateTemplate);

        hibernateEntityDao.setHibernateTemplate(mockHibernateTemplate);

        hibernateEntityDao.save(obj);
        EasyMock.verify();
    }

    public void testRemove() {
        Object obj = new Object();
        mockHibernateTemplate.delete(obj);
        EasyMock.replay(mockHibernateTemplate);

        hibernateEntityDao.setHibernateTemplate(mockHibernateTemplate);

        hibernateEntityDao.remove(obj);
        EasyMock.verify();
    }

    public void testRemoveById() {
        Object obj = new Object();
        EasyMock.expect(mockHibernateTemplate.get(Object.class, 1L))
                .andReturn(obj);
        mockHibernateTemplate.delete(obj);
        EasyMock.replay(mockHibernateTemplate);

        hibernateEntityDao.setHibernateTemplate(mockHibernateTemplate);

        hibernateEntityDao.removeById(1L);
        EasyMock.verify();
    }

    public void testPagedQuery() {
        int totalNumber = 326;
        int pageNo = 12;
        int pageSize = 25;
        String countQueryString = "select count (*) from java.lang.Object t";
        String selectQueryString = "from java.lang.Object t";
        Object[] args = {"fri%"};

        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.getSessionFactory())
            .andReturn(mockSessionFactory);
        expect(mockSession.createQuery(countQueryString))
            .andReturn(mockQuery);
        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.getSessionFactory())
            .andReturn(mockSessionFactory);
        expect(mockSession.createQuery(selectQueryString))
            .andReturn(mockQuery);
        mockSession.flush();
        expect(mockQuery.list()).andReturn(mockList);
        expect(mockQuery.setFirstResult(275)).andReturn(mockQuery);
        expect(mockQuery.setMaxResults(25)).andReturn(mockQuery);
        expect(mockQuery.list()).andReturn(mockList);
        expect(mockList.get(0)).andReturn((long) totalNumber);
        expect(mockList.size()).andReturn(totalNumber);

        replay(mockSessionFactory);
        replay(mockSession);
        replay(mockQuery);
        replay(mockList);

        hibernateEntityDao.setSessionFactory(mockSessionFactory);

        Page page = hibernateEntityDao.pagedQuery(pageNo, pageSize);
        verify();

        assertEquals(14, page.getTotalPageCount());
        assertEquals(12, page.getCurrentPageNo());
        assertTrue(page.hasNextPage());
        assertTrue(page.hasPreviousPage());
    }

    public void testPagedQuery2() {
        int totalNumber = 0;
        int pageNo = 1;
        int pageSize = 10;
        String countQueryString = "select count (*) from java.lang.Object t where t.a like '%b%'";
        String selectQueryString = "from java.lang.Object t where t.a like '%b%' order by c desc";
        Object[] args = {"fri%"};
        Map filterMap = new HashMap();
        filterMap.put("a", "b");

        Map sortMap = new HashMap();
        sortMap.put("c", "desc");

        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.getSessionFactory())
            .andReturn(mockSessionFactory);
        expect(mockSession.createQuery(countQueryString))
            .andReturn(mockQuery);
        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.getSessionFactory())
            .andReturn(mockSessionFactory);
        expect(mockSession.createQuery(selectQueryString))
            .andReturn(mockQuery);
        mockSession.flush();
        expect(mockQuery.list()).andReturn(mockList);
        expect(mockQuery.setFirstResult(275)).andReturn(mockQuery);
        expect(mockQuery.setMaxResults(25)).andReturn(mockQuery);
        expect(mockQuery.list()).andReturn(mockList);
        expect(mockList.get(0)).andReturn((long) totalNumber);
        expect(mockList.size()).andReturn(totalNumber);

        replay(mockSessionFactory);
        replay(mockSession);
        replay(mockQuery);
        replay(mockList);

        hibernateEntityDao.setSessionFactory(mockSessionFactory);

        Page page = hibernateEntityDao.pagedQuery(pageNo, pageSize,
                filterMap, sortMap);
        verify();

        assertEquals(0, page.getTotalPageCount());
        assertEquals(1, page.getCurrentPageNo());
        assertFalse(page.hasNextPage());
        assertFalse(page.hasPreviousPage());
    }

    public void testFindBy() {
        Criteria mockCriteria = createMock(Criteria.class);
        List list = new ArrayList<Object>();
        String hql = "from Object";
        Object[] args = {"a"};

        EasyMock.expect(mockHibernateTemplate.isAllowCreate())
                .andReturn(true);
        EasyMock.expect(mockHibernateTemplate.getSessionFactory())
                .andReturn(mockSessionFactory);
        EasyMock.expect(mockHibernateTemplate.getEntityInterceptor())
                .andReturn(null);
        EasyMock.expect(mockHibernateTemplate.getJdbcExceptionTranslator())
                .andReturn(null);
        EasyMock.expect(mockSessionFactory.openSession())
                .andReturn(mockSession);
        EasyMock.expect(mockSession.getSessionFactory())
                .andReturn(mockSessionFactory);
        EasyMock.expect(mockSession.createCriteria(Object.class))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.add(isA(org.hibernate.criterion.Criterion.class)))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.list()).andReturn(list);
        EasyMock.replay(mockHibernateTemplate);
        EasyMock.replay(mockSessionFactory);
        EasyMock.replay(mockSession);
        EasyMock.replay(mockCriteria);

        // start testing
        hibernateEntityDao.setHibernateTemplate(mockHibernateTemplate);

        List result = hibernateEntityDao.findBy(hql, args);
        EasyMock.verify();
    }
}
