package anni.core.web.freemarker;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import org.springframework.web.context.ServletContextAware;


/**
 * @version $Id: ServletContextModelFactoryBean.java,v 1.1 2005/07/04 15:02:14 turelto Exp $
 */
public class ServletContextModelFactoryBean extends AbstractFactoryBean
    implements ServletContextAware {
    /**
     * ServletContext.
     */
    private ServletContext servletContext;

    /**
     * @param servletContextIn ServletContext.s
     */
    public void setServletContext(ServletContext servletContextIn) {
        servletContext = servletContextIn;
    }

    /**
     * @return object type.
     */
    public Class getObjectType() {
        return ServletContextHashModel.class;
    }

    /**
     * @return Object ServletContextHashModel.
     * @throws ServletException ServletException
     */
    protected Object createInstance() throws ServletException {
        GenericServlet dummyServlet = new DummyServlet();
        dummyServlet.init(new DummyServletConfig(servletContext));

        return new ServletContextHashModel(dummyServlet,
            ObjectWrapper.DEFAULT_WRAPPER);
    }

    /**
     * 虚拟servlet.
     *
     * @since 2007-04-17
     */
    public static class DummyServlet extends GenericServlet {
        /**
         * 处理请求和响应.
         *
         * @param servletRequest ServletRequest
         * @param servletResponse ServletResponse
         */
        public void service(ServletRequest servletRequest,
            ServletResponse servletResponse) {
        }
    }

    /**
     * 虚拟servlet config.
     *
     * @since 2007-04-17
     */
    private static class DummyServletConfig implements ServletConfig {
        /**
         * ServletContext.
         */
        private ServletContext servletContext;

        /**
         * Hashtable.
         */
        private Hashtable hashtable = new Hashtable();

        /**
         * 构造方法.
         *
         * @param servletContextIn ServletContext
         */
        public DummyServletConfig(ServletContext servletContextIn) {
            servletContext = servletContextIn;
        }

        /**
         * @return String servlet name.
         */
        public String getServletName() {
            return DummyServlet.class.getName();
        }

        /**
         * @return ServletContext servlet context.
         */
        public ServletContext getServletContext() {
            return servletContext;
        }

        /**
         * @param distinguishedName String.
         * @return String always null
         */
        public String getInitParameter(String distinguishedName) {
            return null;
        }

        /**
         * @return Enumeration param names.
         */
        public Enumeration getInitParameterNames() {
            return hashtable.elements();
        }
    }
}
