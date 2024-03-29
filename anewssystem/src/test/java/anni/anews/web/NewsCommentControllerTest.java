package anni.anews.web;

import anni.anews.domain.NewsComment;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsCommentControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(NewsCommentControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (NewsCommentController) applicationContext.getBean(
                "anni.anews.web.NewsCommentController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/newscomment/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/anews/newscomment/listNewsComment");
    }
}
