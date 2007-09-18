package anni.core.tree;

import java.io.Serializable;

import java.util.*;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.mock.web.*;

import org.springframework.web.servlet.ModelAndView;


public class LongTreeControllerTest extends TestCase {
    protected static Log logger = LogFactory.getLog(LongTreeControllerTest.class);

    // stub
    protected TestController controller;
    protected Node node;
    protected Dao dao;

    // mock
    protected MockServletContext context = new MockServletContext("");
    protected MockHttpServletRequest request = new MockHttpServletRequest(context,
            "GET", "");
    protected MockHttpSession session = null;
    protected MockHttpServletResponse response = new MockHttpServletResponse();
    protected ModelAndView mv = null;

    @Override
    protected void setUp() {
        controller = new TestController();
        node = new Node();
        dao = new Dao();
    }

    @Override
    protected void tearDown() {
        controller = null;
    }

    public void testDefault() {
        assertNotNull(controller);
        //
        assertEquals(Node.class, controller.getEntityClass());
        assertEquals("node", controller.getCommandName(node));

        //
        controller.setEntityDao(dao);
        assertEquals(dao, controller.getEntityDao());
        //
        assertEquals("yyyy-MM-dd", controller.getDatePattern());
    }

    public void testExcludes() {
        assertEquals(3, controller.getExcludesForAll().length);
        assertEquals("class", controller.getExcludesForAll()[0]);
        assertEquals("root", controller.getExcludesForAll()[1]);
        assertEquals("parent", controller.getExcludesForAll()[2]);

        assertEquals(4, controller.getExcludesForChildren().length);
        assertEquals("class", controller.getExcludesForChildren()[0]);
        assertEquals("root", controller.getExcludesForChildren()[1]);
        assertEquals("parent", controller.getExcludesForChildren()[2]);
        assertEquals("children", controller.getExcludesForChildren()[3]);
    }

    public void testGetAllTree() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("/test/getAllTree.htm");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
        response.getWriter().flush();
        assertEquals("[{\"parentId\":0,\"theSort\":0,\"cls\":\"\",\"leaf\":false,\"qtip\":\"\",\"allowDelete\":true,\"allowEdit\":true,\"draggable\":true,\"id\":0,\"text\":\"\",\"allowChildren\":true,\"name\":\"\",\"children\":[{\"parentId\":0,\"theSort\":0,\"cls\":\"\",\"leaf\":true,\"qtip\":\"\",\"allowDelete\":true,\"allowEdit\":true,\"draggable\":true,\"id\":0,\"text\":\"\",\"allowChildren\":true,\"name\":\"\",\"children\":[]}]}]",
            response.getContentAsString());
    }

    public void testGetChildren() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/getChildren.htm");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
        response.getWriter().flush();
        assertEquals("[{\"text\":\"\",\"parentId\":0,\"theSort\":0,\"allowChildren\":true,\"leaf\":false,\"cls\":\"\",\"qtip\":\"\",\"allowDelete\":true,\"name\":\"\",\"draggable\":true,\"allowEdit\":true,\"id\":0}]",
            response.getContentAsString());
    }

    public void testGetChildren2() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/getChildren.htm");
        request.addParameter("node", "1");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
        response.getWriter().flush();
        assertEquals("[{\"text\":\"\",\"parentId\":0,\"theSort\":0,\"allowChildren\":true,\"leaf\":true,\"cls\":\"\",\"qtip\":\"\",\"allowDelete\":true,\"name\":\"\",\"draggable\":true,\"allowEdit\":true,\"id\":1}]",
            response.getContentAsString());
    }

    public void testLoadData() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/loadData.htm");
        request.addParameter("id", "1");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
        response.getWriter().flush();
        assertEquals("[{\"text\":\"\",\"parentId\":0,\"theSort\":0,\"allowChildren\":true,\"leaf\":true,\"cls\":\"\",\"qtip\":\"\",\"allowDelete\":true,\"name\":\"\",\"draggable\":true,\"allowEdit\":true,\"id\":1}]",
            response.getContentAsString());
    }

    public void testLoadData2() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/loadData.htm");
        request.addParameter("id", "-1");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
        response.getWriter().flush();
        assertEquals("", response.getContentAsString());
    }

    public void testInsertTree() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/insertTree.htm");
        request.addParameter("data",
            "{\"id\":0,\"name\":\"text\",\"parentId\":0}");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
        assertEquals("{success:true,id:3}", response.getContentAsString());
    }

    public void testInsertTree2() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/insertTree.htm");
        request.addParameter("data", "{\"id\":1,\"parentId\":0}");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
        assertEquals("{success:true,id:1}", response.getContentAsString());
    }

    public void testRemove() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/removeTree.htm");
        request.addParameter("id", "0");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
    }

    public void testRemove2() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/removeTree.htm");
        request.addParameter("id", "-1");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
    }

    public void testSortTree() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/sortTree.htm");
        request.addParameter("data", "[{id:-1},{id:1}]");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
    }

    public void testUpdateTree() throws Exception {
        controller.setEntityDao(dao);
        request.setRequestURI("test/updateTree.htm");
        request.addParameter("data", "{id:1,name:\"test\"}]");
        mv = controller.handleRequest(request, response);
        assertNull(mv.getViewName());
        assertEquals("{success:true,info:\"success\"}",
            response.getContentAsString());
    }

    // stub
    public static class Node extends LongTreeNode<Node> {
    }

    public static class Dao extends LongTreeHibernateDao<Node> {
        List<Node> all = new ArrayList<Node>();
        List<Node> list = new ArrayList<Node>();
        Node node;
        Node node2;
        Node node3;

        public Dao() {
            node = new Node();
            node2 = new Node();
            node3 = new Node();
            node3.setId(1L);
            node.getChildren().add(node2);
            all.add(node);
            list.add(node3);
        }

        @Override
        public List<Node> find(String hql, Object... args) {
            if (hql.equals(
                        "from Node where parent is null order by theSort asc, id desc")) {
                return all;
            } else {
                return list;
            }
        }

        @Override
        public Node get(Serializable id) {
            if (new Long(1L).equals(id)) {
                return node3;
            } else {
                return null;
            }
        }

        @Override
        public void save(Object o) {
            Node entity = (Node) o;

            if (entity != node3) {
                entity.setId(3L);
            }
        }

        @Override
        public void removeById(Serializable id) {
            //
        }
    }

    public static class TestController extends LongTreeController<Node, Dao> {
    }
}
