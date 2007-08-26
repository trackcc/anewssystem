package anni.core.web.prototype;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import anni.core.dao.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.dao.DataIntegrityViolationException;


public class ExtendControllerTest extends TestCase {
    ExtendController controller = null;

    @Override
    protected void setUp() {
        controller = new ExtendController();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testAddValidator() {
        controller.addValidator(null);
        controller.saveMessage(null);
    }

    public void testRendText() throws Exception {
        HttpServletResponse response = createMock(HttpServletResponse.class);
        response.setCharacterEncoding("UTF-8");
        expect(response.getWriter())
            .andReturn(new PrintWriter(new ByteArrayOutputStream()));
        replay(response);

        controller.rendText(response, "test");
        verify();
    }

    public void testGetStrParam() throws Exception {
        controller = new TestExtendController();

        EntityDao dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);
        Map map = new HashMap();

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/getInt.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/getInt.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameter("id")).andReturn("1").anyTimes();
        dao.removeById(1L);
        expectLastCall()
            .andThrow(new DataIntegrityViolationException("test"));
        expect(session.getAttribute("messages")).andReturn(null).anyTimes();
        session.setAttribute(same("messages"), isA(List.class));
        expectLastCall().anyTimes();
        expect(dao.get(1L)).andReturn(null);
        response.sendError(404);
        expect(request.getParameter("test")).andReturn(null);
        expect(request.getParameterValues("test"))
            .andReturn(new String[] {"1"});

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        // controller.setEntityDao(dao);
        // controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public class TestExtendController extends ExtendController {
        public void getInt() throws Exception {
            getIntParam("test", -1);
            getIntParams("test");
        }
    }
}
