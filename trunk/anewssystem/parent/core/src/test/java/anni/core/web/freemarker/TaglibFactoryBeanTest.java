package anni.core.web.freemarker;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.ext.jsp.TaglibFactory;

import freemarker.ext.servlet.ServletContextHashModel;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;


public class TaglibFactoryBeanTest extends TestCase {
    TaglibFactoryBean bean = null;

    @Override
    protected void setUp() {
        bean = new TaglibFactoryBean();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDefault() {
        bean.setServletContext(null);
        assertEquals(TaglibFactory.class, bean.getObjectType());
        assertNotNull(bean.createInstance());
    }
}
