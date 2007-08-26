package anni.core.web.freemarker;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.ext.servlet.ServletContextHashModel;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;


public class ServletContextModelFactoryBeanTest extends TestCase {
    ServletContextModelFactoryBean bean = null;

    @Override
    protected void setUp() {
        bean = new ServletContextModelFactoryBean();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDefault() throws Exception {
        bean.setServletContext(null);
        assertEquals(ServletContextHashModel.class, bean.getObjectType());
        assertNotNull(bean.createInstance());
    }

    public void testServlet() throws Exception {
        ServletContextHashModel model = (ServletContextHashModel) bean
            .createInstance();
        ServletContextModelFactoryBean.DummyServlet servlet = (ServletContextModelFactoryBean.DummyServlet) model
            .getServlet();
        assertNotNull(servlet);
        servlet.service(null, null);
        assertNull(servlet.getServletContext());

        for (Enumeration e = servlet.getInitParameterNames();
                e.hasMoreElements();) {
            System.out.println(e.nextElement());
        }

        //assertNull(servlet.getInitParameterNames().nextElement());
        assertNull(servlet.getInitParameter("none"));
        assertEquals(servlet.getClass().getName(), servlet.getServletName());
    }
}
