
package ${webPackage};

import anni.core.test.AbstractWebTests;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AdminControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(AdminControllerTest.class);
    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (AdminController) applicationContext.getBean(
                "${webPackage}.AdminController");
    }
    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }
    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/admin/index.htm");
        mv = controller.handleRequest(request, response);
        // mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, null);

        request.setRequestURI("/admin/top.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);

        request.setRequestURI("/admin/menu.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);

        request.setRequestURI("/admin/main.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }
}
