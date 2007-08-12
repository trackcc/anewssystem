package anni.anews.web;

import anni.anews.domain.Tag;

import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TagControllerTest extends PrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(TagControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (TagController) ctx.getBean(
                "anni.anews.web.TagController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/tag/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/tag/listTag");
    }
}
