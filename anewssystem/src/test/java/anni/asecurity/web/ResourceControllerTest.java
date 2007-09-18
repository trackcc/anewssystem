package anni.asecurity.web;

import anni.asecurity.domain.Resource;

import anni.core.test.AbstractPrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ResourceControllerTest extends AbstractPrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(ResourceControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (ResourceController) ctx.getBean(
                "anni.asecurity.web.ResourceController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/resource/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/asecurity/resource/listResource");
    }

    public void testInsert() throws Exception {
        request.setRequestURI("/resource/insert.htm");
        request.addParameter("name", "resource");
        request.addParameter("resType", "METHOD");
        request.addParameter("resString",
            "anni.asecurity.web.ResourceController.*");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "resource");
        mvHelper.assertViewName(mv, null);
    }

    public void testInsert2() throws Exception {
        request.setRequestURI("/resource/insert.htm");
        request.addParameter("name", "res dao");
        request.addParameter("resType", "METHOD");
        request.addParameter("resString",
            "anni.asecurity.manager.ResourceManager.*");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "resource");
        mvHelper.assertViewName(mv, null);
    }
}
