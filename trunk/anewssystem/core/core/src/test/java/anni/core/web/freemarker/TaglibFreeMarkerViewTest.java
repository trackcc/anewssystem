package anni.core.web.freemarker;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.ext.jsp.TaglibFactory;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.Configuration;
import freemarker.template.Template;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;


public class TaglibFreeMarkerViewTest extends TestCase {
    TaglibFreeMarkerView view = null;

    @Override
    protected void setUp() {
        view = new TaglibFreeMarkerView();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDefault() {
        view.setServletContextModel(null);
        view.setTaglibFactory(null);
    }

    public void testDoRender() throws Exception {
        TaglibFactory taglib = EasyMock.createMock(TaglibFactory.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        Configuration configuration = EasyMock.createMock(Configuration.class);
        Template template = EasyMock.createMock(Template.class);

        request.setAttribute(same("Request"),
            isA(HttpRequestHashModel.class));
        request.setAttribute(same("JspTaglibs"), isA(TaglibFactory.class));
        expect(request.getSession(false)).andReturn(session);
        EasyMock.expect(configuration.getObjectWrapper()).andReturn(null)
                .times(2);
        expect(request.getAttribute(
                "org.springframework.web.servlet.DispatcherServlet.LOCALE_RESOLVER"))
            .andReturn(null);
        expect(request.getLocale()).andReturn(Locale.CHINA);
        EasyMock.expect(configuration.getTemplate(null, Locale.CHINA))
                .andReturn(template);
        expect(response.getWriter())
            .andReturn(new PrintWriter(new ByteArrayOutputStream()));
        template.process(isA(Map.class), isA(PrintWriter.class));
        EasyMock.replay(taglib);
        replay(request);
        replay(session);
        replay(response);
        EasyMock.replay(configuration);
        EasyMock.replay(template);

        view.setConfiguration(configuration);
        view.setServletContextModel(null);
        view.setTaglibFactory(taglib);
        view.doRender(new HashMap(), request, response);
        EasyMock.verify();
        verify();
    }
}
