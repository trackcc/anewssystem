package anni.core.web.freemarker;

import javax.servlet.ServletContext;

import freemarker.ext.jsp.TaglibFactory;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import org.springframework.web.context.ServletContextAware;


/**
 * @version $Id: TaglibFactoryBean.java,v 1.1 2005/07/04 15:02:14 turelto Exp $
 */
public class TaglibFactoryBean extends AbstractFactoryBean
    implements ServletContextAware {
    /**
     * ServletContext.
     */
    private ServletContext servletContext;

    /**
     * @param servletContextIn ServletContext.
     */
    public void setServletContext(ServletContext servletContextIn) {
        servletContext = servletContextIn;
    }

    /**
     * @return Class object type.
     */
    public Class getObjectType() {
        return TaglibFactory.class;
    }

    /**
     * @return Object instance.
     */
    protected Object createInstance() {
        return new TaglibFactory(servletContext);
    }
}
