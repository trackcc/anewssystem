package anni.core.web.freemarker;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.ext.jsp.TaglibFactory;

import freemarker.ext.servlet.ServletContextHashModel;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;


public class TaglibFreeMarkerViewResolverTest extends TestCase {
    TaglibFreeMarkerViewResolver resolver = null;

    @Override
    protected void setUp() {
        resolver = new TaglibFreeMarkerViewResolver();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDefault() {
        resolver.setServletContextModel(null);
        resolver.setTaglibFactory(null);
    }

    public void testBuildView() throws Exception {
        AbstractUrlBasedView view = resolver.buildView("test");
    }
}
