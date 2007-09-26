package anni.anews.web;

import anni.anews.domain.NewsConfig;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsConfigControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(AbstractWebTests.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (NewsConfigController) applicationContext.getBean(
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

    /*
        public void testUpdate() throws Exception {
            testManage();
            request.setRequestURI("/newsconfig/update.htm");
            request.addParameter("id", "1");
            mv = controller.handleRequest(request, response);
            mvHelper.assertViewName(mv, "redirect:/newsconfig/manage.htm");
        }
    */
}
