package anni.core.test;

import javax.servlet.ServletRequestEvent;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import org.springframework.transaction.support.TransactionSynchronizationManager;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * 为了支持PrototypeController写的对应测试类.
 * 包括：
 * 1.初始化可以使用WebApplicationContext的ctx，主要为了Struts-Menu不出错，但freemarker还是有警告
 * 2.支持dbunit对数据库数据进行初始化
 * 3.使用RequestContextListener支持request这种scope的bean
 * 4.使用ModelAndViewTestHelper提供对ModelAndView的简易测试方法
 * 5.使用OpenSessionInViewFilter的方法，避免出现lazy initialization exception
 * 6.另外使用了AbstractTransactionalDataSourceSpringContextTests，应该是可以控制数据库事务，但还没测试过
 *
 * @author Lingo
 * @since 2007-06-01
 * @version 1.0
 */
public class PrototypeControllerTest
    extends AbstractTransactionalDataSourceSpringContextTests {
    // static fields
    //
    /** * dbunit helper. */
    protected static final DbunitHelper DBUNIT_HELPER = new DbunitHelper();

    /** * listener. */
    protected static final RequestContextListener REQUEST_LISTENER = new RequestContextListener();

    /** * mv helper. */
    protected ModelAndViewTestHelper mvHelper = new ModelAndViewTestHelper();

    // fields
    //
    /** * controller. */
    protected Controller controller;

    /** * ctx. */
    protected XmlWebApplicationContext ctx;

    /** * ServletContext. */
    protected MockServletContext context = new MockServletContext("");

    /** * HttpServletRequest. */
    protected MockHttpServletRequest request = new MockHttpServletRequest(context,
            "GET", "");

    /** * HttpSession. */
    protected MockHttpSession session = null;

    /** * HttpServletResponse. */
    protected MockHttpServletResponse response = new MockHttpServletResponse();

    /** * ModelAndView. */
    protected ModelAndView mv = null;

    /** * ServletRequestEvent. */
    protected ServletRequestEvent event = null;

    /** * sessionFactory. */
    protected SessionFactory sessionFactory = null;

    /**
     * 构造方法，在里边进行ctx的初始化.
     */
    public PrototypeControllerTest() {
        /** * paths */
        String[] paths = getConfigLocations();

        ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(paths);
        ctx.setServletContext(context);
        ctx.refresh();

        ctx.getBeanFactory()
           .autowireBeanProperties(this,
            AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

        session = new MockHttpSession(context);
        request.setSession(session);
    }

    /**
     * spring测试时，获得xml的路径.
     *
     * @return String[] spring读取xml的路径
     * @see AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_TYPE);

        return new String[] {
            "classpath*:spring/*.xml", "classpath*:test/*.xml"
        };
    }

    /** * setter. */
    //public void setUserController(UserController userControllerIn) {
    //    userController = userControllerIn;
    //}

    /**
     * 事务之前的初始化.
     * 1.dbunit进行初始化
     * 2.使用RequestContextListener绑定controller
     * 3.open session in view
     *
     * 具体使用的时候要覆盖这个方法
     * <pre>@Override
     * protected void onSetUpBeforeTransaction() throws Exception {
     *     super.onSetUpBeforeTransaction();
     *     controller = (Controller) ctx.getBean("anni.cms.lag.web.UserController");
     * }</pre>
     * FIXME: 代码量还是嫌多了，怎么简化？
     *
     * @throws Exception 初始化的错误
     */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();

        DBUNIT_HELPER.doInit("dbunit.properties");
        DBUNIT_HELPER.setXlsPath("src/test/resources/xls/export.xls");
        DBUNIT_HELPER.setUp();

        event = new ServletRequestEvent(context, request);
        REQUEST_LISTENER.requestInitialized(event);

        /*
         * controller = (UserController) ctx.getBean(
         *         "anni.cms.lag.web.UserController");
         */

        /** * open session in view */
        sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");

        Session hibernateSession = SessionFactoryUtils.getSession(sessionFactory,
                true);
        hibernateSession.setFlushMode(FlushMode.AUTO);
        TransactionSynchronizationManager.bindResource(sessionFactory,
            new SessionHolder(hibernateSession));
    }

    /**
     * 做测试完成后的收尾工作.
     * 1.dbunit的收尾
     * 2.告诉RequestContextListener请求结束，估计会销毁绑定的controller
     * 3.关闭hibernate的session
     *
     * @throws Exception 收尾时可能发生的异常
     */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
        DBUNIT_HELPER.tearDown();

        REQUEST_LISTENER.requestDestroyed(event);

        /** * open session in view */
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
            .unbindResource(sessionFactory);
        SessionFactoryUtils.closeSession(sessionHolder.getSession());
    }

    /** * test one */
    // public void testOne() {
    //     assertTrue(true);
    //     assertNotNull(controller);
    // }
}
