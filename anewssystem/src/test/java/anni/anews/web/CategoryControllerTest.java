package anni.anews.web;

import anni.anews.domain.Category;

import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CategoryControllerTest extends PrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(CategoryControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (CategoryController) ctx.getBean(
                "anni.anews.web.CategoryController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/category/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/category/listCategory");
    }

    public void testGetChildren() throws Exception {
        request.setRequestURI("/category/getChildren.htm");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();

        String content = response.getContentAsString();
        assertEquals("[]", content);
        assertNotNull(mv);
    }

    public void testGetChildren2() throws Exception {
        request.setRequestURI("/category/getChildren.htm");
        request.addParameter("node", "1");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();

        String content = response.getContentAsString();
        assertEquals("[]", content);
        assertNotNull(mv);
    }

    public void testInsertTree() throws Exception {
        request.setRequestURI("/category/insertTree.htm");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testInsertTree2() throws Exception {
        request.setRequestURI("/category/insertTree.htm");
        request.addParameter("data", "{id:-1,text:\"name1\",parentId:-1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testInsertTree3() throws Exception {
        request.setRequestURI("/category/insertTree.htm");
        request.addParameter("data", "{id:1,text:\"name1\",parentId:-1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testInsertTree4() throws Exception {
        request.setRequestURI("/category/insertTree.htm");
        request.addParameter("data", "{id:-1,text:\"name1\",parentId:1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    // id不为-1，但对应的category不存在的情况
    public void testInsertTree5() throws Exception {
        request.setRequestURI("/category/insertTree.htm");
        request.addParameter("data", "{id:100,text:\"name1\",parentId:1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testRemoveTree() throws Exception {
        request.setRequestURI("/category/removeTree.htm");
        request.addParameter("id", "-1L");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testRemoveTree2() throws Exception {
        request.setRequestURI("/category/removeTree.htm");
        request.addParameter("id", "1L");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testSortTree() throws Exception {
        request.setRequestURI("/category/sortTree.htm");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testSortTree2() throws Exception {
        request.setRequestURI("/category/sortTree.htm");
        request.addParameter("data",
            "[{id:1,parentId:-1},{id:2,parentId:-1},{id:3,parentId:1}]");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    /** * tree. */
    public void testTree() throws Exception {
        request.setRequestURI("/category/tree.htm");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }
}
