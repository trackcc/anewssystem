package anni.core.tree;

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
 * @since 2007-09-15
 * @version 1.0
 */
public class LongTreeHibernateDaoTest extends TestCase {
    LongTreeHibernateDao dao;
    SessionFactory mockSessionFactory;
    Session mockSession;
    Query mockQuery;
    HibernateTemplate mockHibernateTemplate;
    List mockList;
    Criteria mockCriteria;

    protected void setUp() {
        dao = new Dao();
        mockSessionFactory = createMock(SessionFactory.class);
        mockSession = createMock(Session.class);
        mockQuery = createMock(Query.class);
        mockHibernateTemplate = EasyMock.createMock(HibernateTemplate.class);
        mockList = createMock(List.class);
        mockCriteria = createMock(Criteria.class);
    }

    protected void prepareCreateOrGet() {
        Criteria mockCriteria = createMock(Criteria.class);
        List<Node> list = new ArrayList<Node>();
        String hql = "from Node";
        Object[] args = new Object[] {"name"};

        EasyMock.expect(mockHibernateTemplate.isAllowCreate())
                .andReturn(true);
        EasyMock.expect(mockHibernateTemplate.getSessionFactory())
                .andReturn(mockSessionFactory).anyTimes();
        EasyMock.expect(mockHibernateTemplate.getEntityInterceptor())
                .andReturn(null);
        EasyMock.expect(mockHibernateTemplate.getJdbcExceptionTranslator())
                .andReturn(null);
        EasyMock.expect(mockSessionFactory.openSession())
                .andReturn(mockSession);
        EasyMock.expect(mockSession.getSessionFactory())
                .andReturn(mockSessionFactory).anyTimes();
        EasyMock.expect(mockSession.createCriteria(Node.class))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.add(isA(org.hibernate.criterion.Criterion.class)))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.list()).andReturn(list);
        EasyMock.expect(mockSession.getFlushMode())
                .andReturn(FlushMode.AUTO);
        mockSession.saveOrUpdate(EasyMock.isA(Node.class));
        mockSession.flush();

        expect(mockSessionFactory.openSession()).andReturn(mockSession);

        EasyMock.replay(mockHibernateTemplate);
        EasyMock.replay(mockSessionFactory);
        EasyMock.replay(mockSession);
        EasyMock.replay(mockCriteria);

        // start testing
        dao.setHibernateTemplate(mockHibernateTemplate);
        dao.setSessionFactory(mockSessionFactory);
    }

    // 测试saveOrUpdate的情况
    public void testCreateOrGet() {
        prepareCreateOrGet();

        Object obj = dao.createOrGet("name");
        assertNotNull(obj);
    }

    // 测试name为空格的情况
    public void testCreateOrGet2() {
        prepareCreateOrGet();

        Object obj = dao.createOrGet("");
        assertNull(obj);
    }

    // 测试name为null的情况
    public void testCreateOrGet3() {
        prepareCreateOrGet();

        Object obj = dao.createOrGet(null);
        assertNull(obj);
    }

    // 找到同名记录的情况
    public void testCreateOrGet4() {
        Criteria mockCriteria = createMock(Criteria.class);
        List<Node> list = new ArrayList<Node>();
        list.add(new Node());

        String hql = "from Node";
        Object[] args = new Object[] {"name"};

        EasyMock.expect(mockHibernateTemplate.isAllowCreate())
                .andReturn(true);
        EasyMock.expect(mockHibernateTemplate.getSessionFactory())
                .andReturn(mockSessionFactory).anyTimes();
        EasyMock.expect(mockHibernateTemplate.getEntityInterceptor())
                .andReturn(null);
        EasyMock.expect(mockHibernateTemplate.getJdbcExceptionTranslator())
                .andReturn(null);
        EasyMock.expect(mockSessionFactory.openSession())
                .andReturn(mockSession);
        EasyMock.expect(mockSession.getSessionFactory())
                .andReturn(mockSessionFactory).anyTimes();
        EasyMock.expect(mockSession.createCriteria(Node.class))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.add(isA(org.hibernate.criterion.Criterion.class)))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.list()).andReturn(list);
        EasyMock.expect(mockSession.getFlushMode())
                .andReturn(FlushMode.AUTO);
        mockSession.saveOrUpdate(EasyMock.isA(Node.class));
        mockSession.flush();

        expect(mockSessionFactory.openSession()).andReturn(mockSession);

        EasyMock.replay(mockHibernateTemplate);
        EasyMock.replay(mockSessionFactory);
        EasyMock.replay(mockSession);
        EasyMock.replay(mockCriteria);

        // start testing
        dao.setHibernateTemplate(mockHibernateTemplate);
        dao.setSessionFactory(mockSessionFactory);

        Object obj = dao.createOrGet("name");
        assertNotNull(obj);
    }

    // 测试saveOrUpdate出错情况一，接口或抽象类服务初始化
    public void testCreateOrGet5() {
        dao = new Dao1();

        Criteria mockCriteria = createMock(Criteria.class);
        List<Bad1> list = new ArrayList<Bad1>();
        String hql = "from Bad1";
        Object[] args = new Object[] {"name"};

        EasyMock.expect(mockHibernateTemplate.isAllowCreate())
                .andReturn(true);
        EasyMock.expect(mockHibernateTemplate.getSessionFactory())
                .andReturn(mockSessionFactory).anyTimes();
        EasyMock.expect(mockHibernateTemplate.getEntityInterceptor())
                .andReturn(null);
        EasyMock.expect(mockHibernateTemplate.getJdbcExceptionTranslator())
                .andReturn(null);
        EasyMock.expect(mockSessionFactory.openSession())
                .andReturn(mockSession);
        EasyMock.expect(mockSession.getSessionFactory())
                .andReturn(mockSessionFactory).anyTimes();
        EasyMock.expect(mockSession.createCriteria(Bad1.class))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.add(isA(org.hibernate.criterion.Criterion.class)))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.list()).andReturn(list);
        EasyMock.expect(mockSession.getFlushMode())
                .andReturn(FlushMode.AUTO);
        mockSession.saveOrUpdate(EasyMock.isA(Bad1.class));
        mockSession.flush();

        expect(mockSessionFactory.openSession()).andReturn(mockSession);

        EasyMock.replay(mockHibernateTemplate);
        EasyMock.replay(mockSessionFactory);
        EasyMock.replay(mockSession);
        EasyMock.replay(mockCriteria);

        // start testing
        dao.setHibernateTemplate(mockHibernateTemplate);
        dao.setSessionFactory(mockSessionFactory);

        Object obj = dao.createOrGet("name");
        assertNull(obj);
    }

    // 测试saveOrUpdate出错情况二，私有构造方法无法调用
    public void testCreateOrGet6() {
        dao = new Dao2();

        Criteria mockCriteria = createMock(Criteria.class);
        List<Bad2> list = new ArrayList<Bad2>();
        String hql = "from Bad1";
        Object[] args = new Object[] {"name"};

        EasyMock.expect(mockHibernateTemplate.isAllowCreate())
                .andReturn(true);
        EasyMock.expect(mockHibernateTemplate.getSessionFactory())
                .andReturn(mockSessionFactory).anyTimes();
        EasyMock.expect(mockHibernateTemplate.getEntityInterceptor())
                .andReturn(null);
        EasyMock.expect(mockHibernateTemplate.getJdbcExceptionTranslator())
                .andReturn(null);
        EasyMock.expect(mockSessionFactory.openSession())
                .andReturn(mockSession);
        EasyMock.expect(mockSession.getSessionFactory())
                .andReturn(mockSessionFactory).anyTimes();
        EasyMock.expect(mockSession.createCriteria(Bad2.class))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.add(isA(org.hibernate.criterion.Criterion.class)))
                .andReturn(mockCriteria);
        EasyMock.expect(mockCriteria.list()).andReturn(list);
        EasyMock.expect(mockSession.getFlushMode())
                .andReturn(FlushMode.AUTO);
        mockSession.saveOrUpdate(EasyMock.isA(Bad2.class));
        mockSession.flush();

        expect(mockSessionFactory.openSession()).andReturn(mockSession);

        EasyMock.replay(mockHibernateTemplate);
        EasyMock.replay(mockSessionFactory);
        EasyMock.replay(mockSession);
        EasyMock.replay(mockCriteria);

        // start testing
        dao.setHibernateTemplate(mockHibernateTemplate);
        dao.setSessionFactory(mockSessionFactory);

        Object obj = dao.createOrGet("name");
        assertNull(obj);
    }

    public void testLoadTops() {
        LongTreeNode node = new LongTreeNode();
        node.setName("name");

        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.getSessionFactory())
            .andReturn(mockSessionFactory);

        expect(mockSession.createQuery(
                "from anni.core.tree.LongTreeHibernateDaoTest$Node where parent is null"))
            .andReturn(mockQuery);
        //expect(mockSession.get(LongTreeNode.class, 1L)).andReturn(node);
        mockSession.flush();
        expect(mockQuery.list()).andReturn(new ArrayList());
        replay(mockSessionFactory);
        replay(mockSession);
        replay(mockQuery);

        // start testing
        dao.setHibernateTemplate(mockHibernateTemplate);
        dao.setSessionFactory(mockSessionFactory);

        List list = dao.loadTops();
        assertNotNull(list);
    }

    public void testLoadTops2() {
        LongTreeNode node = new LongTreeNode();
        node.setName("name");

        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.getSessionFactory())
            .andReturn(mockSessionFactory);

        expect(mockSession.createQuery(
                "from anni.core.tree.LongTreeHibernateDaoTest$Node where parent is null order by name asc"))
            .andReturn(mockQuery);
        //expect(mockSession.get(LongTreeNode.class, 1L)).andReturn(node);
        mockSession.flush();
        expect(mockQuery.list()).andReturn(new ArrayList());
        replay(mockSessionFactory);
        replay(mockSession);
        replay(mockQuery);

        // start testing
        dao.setHibernateTemplate(mockHibernateTemplate);
        dao.setSessionFactory(mockSessionFactory);

        List list = dao.loadTops("name", "asc");
        assertNotNull(list);
    }

    public static class Node extends LongTreeNode<Node> {
    }

    public static class Dao extends LongTreeHibernateDao<Node> {
    }

    public abstract static class Bad1 extends LongTreeNode<Bad1> {
    }

    public static class Dao1 extends LongTreeHibernateDao<Bad1> {
    }

    public static class Bad2 extends LongTreeNode<Bad2> {
        public Bad2() throws Exception {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();

            for (StackTraceElement element : elements) {
                if (element.getMethodName().equals("createOrGet")) {
                    throw new IllegalAccessException("false");
                }
            }
        }
    }

    public static class Dao2 extends LongTreeHibernateDao<Bad2> {
    }
}
