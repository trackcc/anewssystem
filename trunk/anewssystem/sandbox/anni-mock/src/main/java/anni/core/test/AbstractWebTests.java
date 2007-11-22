package anni.core.test;

import javax.servlet.ServletRequestEvent;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockServletContext;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.AbstractTransactionalSpringContextTests;

import org.springframework.transaction.support.TransactionSynchronizationManager;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * 改造loadContext.
 *
 * @since 2007-03-19
 */
public class AbstractWebTests
    extends AbstractTransactionalSpringContextTests {
    ///////////////////////////////////////////////////////
    // static
    //
    /** * dbunit helper. */
    //protected static final DbunitHelper DBUNIT_HELPER = new DbunitHelper();

    /** * listener. */
    protected static final RequestContextListener REQUEST_LISTENER = new RequestContextListener();

    /** * ServletContext. */
    protected static MockServletContext SERVLET_CONTEXT = new MockServletContext(
            "");

    ///////////////////////////////////////////////////////
    // fields
    //
    /** * HttpSession. */
    protected MockHttpSession session = new MockHttpSession(SERVLET_CONTEXT);

    /** * controller. */
    protected Controller controller;

    /** * HttpServletRequest. */
    protected MockHttpServletRequest request = new MockHttpServletRequest(SERVLET_CONTEXT,
            "GET", "");

    /** * MultipartHttpServletRequest. */
    protected MockMultipartHttpServletRequest uploadRequest = new MockMultipartHttpServletRequest();

    /** * HttpServletResponse. */
    protected MockHttpServletResponse response = new MockHttpServletResponse();

    /** * ModelAndView. */
    protected ModelAndView mv = null;

    /** * ServletRequestEvent. */
    protected ServletRequestEvent event = null;

    /** * sessionFactory. */
    protected SessionFactory sessionFactory = null;

    /** * mv helper. */
    protected ModelAndViewTestHelper mvHelper = new ModelAndViewTestHelper();

    /**
     * spring测试时，获得xml的路径.
     *
     * @return xml的路径数组
     * @see AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);

        return new String[] {
            "classpath*:spring/*.xml", "classpath*:test/*.xml"
        };
    }

    /**
     * 重载生成ctx的方法.
     * 让我们生成一个XmlWebApplicationContext
     *
     * @param locations spring配置文件
     * @return ctx
     */
    @Override
    protected ConfigurableApplicationContext createApplicationContext(
        String[] locations) {
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocations(locations);
        context.setServletContext(AbstractWebTests.SERVLET_CONTEXT);

        // GenericApplicationContext context = new GenericApplicationContext();
        // customizeBeanFactory(context.getDefaultListableBeanFactory());
        // new XmlBeanDefinitionReader(context).loadBeanDefinitions(locations);
        context.refresh();

        return context;
    }

    /**
     * 事务之前的初始化.
     * 1.dbunit进行初始化
     * 2.使用RequestContextListener绑定controller
     * 3.open session in view
     *
     * 具体使用的时候要覆盖这个方法
     * @Override
     * protected void onSetUpBeforeTransaction() throws Exception {
     *     super.onSetUpBeforeTransaction();
     *     controller = (Controller) ctx.getBean("anni.cms.lag.web.UserController");
     * }
     * FIXME: 代码量还是嫌多了，怎么简化？
     *
     * @throws Exception 初始化的错误
     */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        /*
         *DBUNIT_HELPER.doInit("dbunit.properties");
         *DBUNIT_HELPER.setXlsPath("src/test/resources/xls/export.xls");
         *DBUNIT_HELPER.setUp();
         */
        request.setSession(session);
        event = new ServletRequestEvent(SERVLET_CONTEXT, request);
        REQUEST_LISTENER.requestInitialized(event);

        /*
         * controller = (UserController) applicationContext.getBean(
         *         "anni.cms.lag.web.UserController");
         */

        /** * open session in view */
        sessionFactory = (SessionFactory) applicationContext.getBean(
                "sessionFactory");

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
        /*
         *DBUNIT_HELPER.tearDown();
         */
        REQUEST_LISTENER.requestDestroyed(event);

        /** * open session in view */
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
            .unbindResource(sessionFactory);
        SessionFactoryUtils.closeSession(sessionHolder.getSession());
    }
}
