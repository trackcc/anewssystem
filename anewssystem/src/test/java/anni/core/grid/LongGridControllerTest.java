package anni.core.grid;

import java.io.Serializable;

import java.util.*;

import anni.core.dao.hibernate.HibernateEntityDao;
import anni.core.dao.support.Page;

import anni.core.test.MockCriteria;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.LogFactory;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;

import org.hibernate.transform.ResultTransformer;

import org.springframework.mock.web.*;

import org.springframework.web.servlet.ModelAndView;


public class LongGridControllerTest extends TestCase {
    protected static Log logger = LogFactory.getLog(LongGridBeanTest.class);

    // stub
    protected GridController controller;
    protected GridDao dao;
    protected Grid grid;

    // mock
    protected MockServletContext context = new MockServletContext("");
    protected MockHttpServletRequest request = new MockHttpServletRequest(context,
            "GET", "");
    protected MockHttpSession session = null;
    protected MockHttpServletResponse response = new MockHttpServletResponse();
    protected ModelAndView mv = null;

    @Override
    protected void setUp() {
        controller = new GridController();
        dao = new GridDao();
        grid = new Grid();

        //
        controller.setEntityDao(dao);
    }

    @Override
    protected void tearDown() {
        controller = null;
        dao = null;
        grid = null;
    }

    public void testTrue() {
        assertNotNull(controller);
    }

    public void testGetExcludes() {
        assertEquals(0, controller.getExcludes().length);
    }

    public void testPagedQuery() throws Exception {
        request.setRequestURI("/grid/pagedQuery.htm");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{\"totalPageCount\":0,\"result\":[],\"totalCount\":0,\"pageSize\":15,\"currentPageNo\":1}",
            response.getContentAsString());
        assertTrue(true);
    }

    public void testPagedQuery2() throws Exception {
        request.setRequestURI("/grid/pagedQuery.htm");
        request.addParameter("sort", "id");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{\"totalPageCount\":0,\"result\":[],\"totalCount\":0,\"pageSize\":15,\"currentPageNo\":1}",
            response.getContentAsString());
        assertTrue(true);
    }

    public void testPagedQuery3() throws Exception {
        request.setRequestURI("/grid/pagedQuery.htm");
        request.addParameter("filterTxt", "name");
        request.addParameter("filterValue", "lingo");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{\"totalPageCount\":0,\"result\":[],\"totalCount\":0,\"pageSize\":15,\"currentPageNo\":1}",
            response.getContentAsString());
        assertTrue(true);
    }

    public void testPagedQuery4() throws Exception {
        request.setRequestURI("/grid/pagedQuery.htm");
        request.addParameter("filterTxt", "name");
        //request.addParameter("filterValue", "lingo");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{\"totalPageCount\":0,\"result\":[],\"totalCount\":0,\"pageSize\":15,\"currentPageNo\":1}",
            response.getContentAsString());
        assertTrue(true);
    }

    public void testSave() throws Exception {
        request.setRequestURI("/grid/save.htm");
        request.addParameter("data",
            "{id:1,name:'admin',resType:'URL',resString:'/admin/**',descn:''}");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{success:true}", response.getContentAsString());
    }

    public void testSave2() throws Exception {
        request.setRequestURI("/grid/save.htm");
        request.addParameter("data",
            "{id:-1,name:'admin',resType:'URL',resString:'/admin/**',descn:''}");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{success:true}", response.getContentAsString());
    }

    public void testSave3WithoutId() throws Exception {
        request.setRequestURI("/grid/save.htm");
        request.addParameter("data",
            "{name:'admin',resType:'URL',resString:'/admin/**',descn:''}");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{success:true}", response.getContentAsString());
    }

    public void testLoadData() throws Exception {
        request.setRequestURI("/grid/loadData.htm");
        request.addParameter("id", "1");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("[{\"name\":\"test\",\"id\":1}]",
            response.getContentAsString());
    }

    public void testLoadData2() throws Exception {
        request.setRequestURI("/grid/loadData.htm");
        request.addParameter("id", "0");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("", response.getContentAsString());
    }

    public void testRemove() throws Exception {
        request.setRequestURI("/grid/remove.htm");
        request.addParameter("id", "0");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{success:true}", response.getContentAsString());
    }

    public void testRemove2() throws Exception {
        request.setRequestURI("/grid/remove.htm");
        request.addParameter("id", "a");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();
        assertEquals("{success:true}", response.getContentAsString());
    }

    // stub
    public static class Grid extends LongGridBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class GridDao extends HibernateEntityDao<Grid> {
        public Grid get(Serializable id) {
            if (new Long(1L).equals(id)) {
                Grid grid = new Grid();
                grid.setId(1L);
                grid.setName("test");

                return grid;
            } else {
                return null;
            }
        }

        public Criteria createCriteria(Criterion... criterions) {
            return new MockCriteria();
        }

        public Criteria createCriteria(String orderBy, boolean isAsc,
            Criterion... criterions) {
            return new MockCriteria();
        }

        public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
            return new Page();
        }

        public void save(Object o) {
        }

        public void removeById(Serializable id) {
        }
    }

    public static class GridController extends LongGridController<Grid, GridDao> {
    }
}
