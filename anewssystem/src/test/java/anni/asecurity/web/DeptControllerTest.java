package anni.asecurity.web;

import anni.asecurity.domain.Dept;

import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DeptControllerTest extends PrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(DeptControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (DeptController) ctx.getBean(
                "anni.asecurity.web.DeptController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/dept/index.htm");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "asecurity/dept/index");
    }

    public void testGetExcludesForAll() {
        DeptController target = (DeptController) controller;
        assertEquals(4, target.getExcludesForAll().length);
        assertEquals("class", target.getExcludesForAll()[0]);
        assertEquals("root", target.getExcludesForAll()[1]);
        assertEquals("parent", target.getExcludesForAll()[2]);
        assertEquals("users", target.getExcludesForAll()[3]);
    }

    public void testGetExcludesForChildren() {
        DeptController target = (DeptController) controller;
        assertEquals(5, target.getExcludesForChildren().length);
        assertEquals("class", target.getExcludesForChildren()[0]);
        assertEquals("root", target.getExcludesForChildren()[1]);
        assertEquals("parent", target.getExcludesForChildren()[2]);
        assertEquals("users", target.getExcludesForChildren()[3]);
        assertEquals("children", target.getExcludesForChildren()[4]);
    }
}
