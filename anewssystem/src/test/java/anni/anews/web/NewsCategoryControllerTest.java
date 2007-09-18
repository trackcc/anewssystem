package anni.anews.web;

import anni.anews.domain.NewsCategory;

import anni.core.test.AbstractPrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsCategoryControllerTest
    extends AbstractPrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(NewsCategoryControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (NewsCategoryController) ctx.getBean(
                "anni.anews.web.NewsCategoryController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/newscategory/index.htm");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/anews/newscategory/index");
    }

    public void testGetChildren() throws Exception {
        request.setRequestURI("/newscategory/getChildren.htm");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();

        String content = response.getContentAsString();
        assertEquals("[]", content);
        assertNotNull(mv);
    }

    public void testGetChildren2() throws Exception {
        request.setRequestURI("/newscategory/getChildren.htm");
        request.addParameter("node", "1");
        mv = controller.handleRequest(request, response);
        response.getWriter().flush();

        String content = response.getContentAsString();
        assertEquals("[]", content);
        assertNotNull(mv);
    }

    public void testInsertTree() throws Exception {
        request.setRequestURI("/newscategory/insertTree.htm");
        request.addParameter("data", "{id:-1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testInsertTree2() throws Exception {
        request.setRequestURI("/newscategory/insertTree.htm");
        request.addParameter("data", "{id:-1,name:\"name1\",parentId:-1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testInsertTree3() throws Exception {
        request.setRequestURI("/newscategory/insertTree.htm");
        request.addParameter("data", "{id:1,name:\"name1\",parentId:-1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testInsertTree4() throws Exception {
        request.setRequestURI("/newscategory/insertTree.htm");
        request.addParameter("data", "{id:-1,name:\"name1\",parentId:1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testInsertTree5() throws Exception {
        request.setRequestURI("/newscategory/insertTree.htm");
        request.addParameter("data", "{id:100,name:\"name1\",parentId:1}");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testRemoveTree() throws Exception {
        request.setRequestURI("/newscategory/removeTree.htm");
        request.addParameter("id", "-1L");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testRemoveTree2() throws Exception {
        request.setRequestURI("/newscategory/removeTree.htm");
        request.addParameter("id", "1L");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testSortTree() throws Exception {
        request.setRequestURI("/newscategory/sortTree.htm");
        request.addParameter("data", "[]");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    public void testSortTree2() throws Exception {
        request.setRequestURI("/newscategory/sortTree.htm");
        request.addParameter("data",
            "[{id:1,parentId:-1},{id:2,parentId:-1},{id:3,parentId:1}]");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    /** * index. */
    public void testIndex() throws Exception {
        request.setRequestURI("/newscategory/index.htm");
        mv = controller.handleRequest(request, response);
        assertNotNull(mv);
    }

    /** * getAllTree. */
    public void testAllTree() throws Exception {
        request.setRequestURI("/newscategory/getAllTree.htm");
        mv = controller.handleRequest(request, response);
        assertNotNull("[]", response.getContentAsString());
    }
}
