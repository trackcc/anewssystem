
package ${webPackage};

import anni.core.test.PrototypeControllerTest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IndexControllerTest extends PrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(IndexControllerTest.class);
    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (IndexController) ctx.getBean(
                "${webPackage}.IndexController");
    }
    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }
    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/index/index.htm");
        mv = controller.handleRequest(request, response);
        // mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, null);
    }
}
