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
        request.setRequestURI("/resource/index.htm");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "asecurity/resource/index");
    }

    public void testSave() throws Exception {
        request.setRequestURI("/resource/save.htm");
        request.addParameter("data",
            "{id:'0',name:'resource',resType:'METHOD',resString:'anni.asecurity.web.ResourceController.*'}");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "resource");
        mvHelper.assertViewName(mv, null);
    }

    public void testSave2() throws Exception {
        request.setRequestURI("/resource/save.htm");
        request.addParameter("data",
            "{id:'0',name:'res dao',resType:'METHOD',resString:'anni.asecurity.manager.ResourceManager.*'}");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "resource");
        mvHelper.assertViewName(mv, null);
    }
}
