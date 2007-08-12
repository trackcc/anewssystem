package anni.anews.web;

import anni.anews.domain.Config;

import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConfigControllerTest extends PrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(ConfigControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (ConfigController) ctx.getBean(
                "anni.anews.web.ConfigController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/config/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/config/listConfig");
    }

    public void testManage() throws Exception {
        request.setRequestURI("/config/manage.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }

    public void testUpdate() throws Exception {
        request.setRequestURI("/config/update.htm");
        request.addParameter("id", "1");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, "redirect:/config/manage.htm");
    }
}
