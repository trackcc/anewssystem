package anni.anews.web;

import anni.anews.domain.NewsConfig;

import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsConfigControllerTest extends PrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(NewsConfigControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (NewsConfigController) ctx.getBean(
                "anni.anews.web.NewsConfigController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/newsconfig/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/anews/newsconfig/listNewsConfig");
    }

    public void testManage() throws Exception {
        request.setRequestURI("/newsconfig/manage.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, "/anews/newsconfig/manage");
    }

    public void testUpdate() throws Exception {
        request.setRequestURI("/newsconfig/update.htm");
        request.addParameter("id", "1");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, "redirect:/newsconfig/manage.htm");
    }
}
