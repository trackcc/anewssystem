package anni.core.web.prototype;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import anni.core.dao.*;

import anni.core.page.Page;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.springframework.dao.DataIntegrityViolationException;


public class BaseControllerTest extends TestCase {
    BaseController<Object, EntityDao<Object>> controller = null;

    @Override
    protected void setUp() {
        controller = new BaseController<Object, EntityDao<Object>>();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testEntityDao() {
        EntityDao<Object> dao = createMock(EntityDao.class);
        controller.setEntityDao(dao);
        assertEquals(dao, controller.getEntityDao());
    }

    public void testSetViews() {
        controller.setListView(null);
        controller.setEditView(null);
        controller.setSuccessView(null);
        controller.setShowView(null);
    }

    public void testIndex() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);
        Map<Object, Object> map = new HashMap<Object, Object>();

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/index.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/index.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterMap()).andReturn(map);
        expect(request.getParameter("ec_eti")).andReturn(null);
        expect(dao.pagedQuery(1, 15, null, map)).andReturn(new Page());

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testCreate() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);
        Map<Object, Object> map = new HashMap<Object, Object>();

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/create.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/create.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterMap()).andReturn(map);
        expect(request.getParameter("ec_eti")).andReturn(null);
        expect(dao.pagedQuery(1, 15, null, map)).andReturn(new Page());

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testInsert() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);
        Enumeration<Object> e = createMock(Enumeration.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/insert.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/insert.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterNames()).andReturn(e);
        expect(e.hasMoreElements()).andReturn(false);
        dao.save(anyObject());
        expect(session.getAttribute("messages")).andReturn(null);
        session.setAttribute(same("messages"), isA(List.class));

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);
        replay(e);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testEdit() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);
        Object obj = new Object();

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/edit.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/edit.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameter("id")).andReturn("1").anyTimes();
        expect(dao.get(1L)).andReturn(obj);

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testShow() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);
        Object obj = new Object();

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/show.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/show.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameter("id")).andReturn("1").anyTimes();
        expect(dao.get(1L)).andReturn(obj);

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testUpdate() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);
        Enumeration<Object> e = createMock(Enumeration.class);
        Object obj = new Object();

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/update.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/update.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterNames()).andReturn(e);
        expect(e.hasMoreElements()).andReturn(false);
        dao.save(anyObject());
        expect(session.getAttribute("messages")).andReturn(null);
        session.setAttribute(same("messages"), isA(List.class));
        expect(request.getParameter("id")).andReturn("1").anyTimes();
        expect(dao.get(1L)).andReturn(obj);

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);
        replay(e);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testQuery() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);
        Map<Object, Object> map = new HashMap<Object, Object>();

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/query.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/query.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterMap()).andReturn(map);
        expect(request.getParameter("ec_eti")).andReturn(null);
        expect(dao.pagedQuery(1, 15, null, map)).andReturn(new Page());

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testRemoveAll() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/removeAll.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/removeAll.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterValues("itemlist"))
            .andReturn(new String[] {"1"});
        dao.removeById(1L);
        expect(session.getAttribute("messages")).andReturn(null);
        session.setAttribute(same("messages"), isA(List.class));

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testRemove() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/remove.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/remove.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameter("id")).andReturn("1").anyTimes();
        dao.removeById(1L);
        expect(session.getAttribute("messages")).andReturn(null);
        session.setAttribute(same("messages"), isA(List.class));

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testRemoveAll2() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/removeAll.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/removeAll.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterValues("itemlist")).andReturn(null);
        dao.removeById(1L);
        expect(session.getAttribute("messages")).andReturn(null);
        session.setAttribute(same("messages"), isA(List.class));

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testRemove2() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/remove.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/remove.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameter("id")).andReturn(null).anyTimes();
        dao.removeById(-1L);
        expect(session.getAttribute("messages")).andReturn(null);
        session.setAttribute(same("messages"), isA(List.class));

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testRemoveAll3() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/removeAll.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/removeAll.htm");
        expect(request.getServletPath()).andReturn("/index.htm").anyTimes();
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterValues("itemlist"))
            .andReturn(new String[] {"1"});
        dao.removeById(1L);
        expectLastCall()
            .andThrow(new DataIntegrityViolationException("test"));
        expect(session.getAttribute("messages")).andReturn(null).anyTimes();
        session.setAttribute(same("messages"), isA(List.class));
        expectLastCall().anyTimes();
        expect(dao.get(1L)).andReturn("test test");

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testRemove3() throws Exception {
        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/remove.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/remove.htm");
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
        expect(dao.get(1L)).andReturn("test test");

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testUploadImageToFile() throws Exception {
        controller = new TestBaseController();

        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/upload.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/upload.htm");
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
        expect(dao.get(1L)).andReturn("test test");
        response.sendError(404);

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public void testGetStrParam() throws Exception {
        controller = new TestBaseController();

        EntityDao<Object> dao = createMock(EntityDao.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getServletContext()).andReturn(context).anyTimes();
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/getStr.htm");
        expect(request.getRequestURI()).andReturn("/index.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/getStr.htm");
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
        expect(dao.get(1L)).andReturn("test test");
        response.sendError(404);
        expect(request.getParameter("test")).andReturn(null);

        replay(dao);
        replay(request);
        replay(response);
        replay(session);
        replay(context);

        controller.setEntityDao(dao);
        //controller.setControllerClassNameHandlerMapping
        controller.handleRequestInternal(request, response);
        verify();
    }

    public class TestBaseController extends BaseController<Object, EntityDao<Object>> {
        public void upload() throws Exception {
            uploadImageToFile("/", "test");
        }

        public void getStr() throws Exception {
            getStrParam("test", null);
        }
    }
}
