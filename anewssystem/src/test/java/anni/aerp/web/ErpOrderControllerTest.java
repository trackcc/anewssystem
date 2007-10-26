package anni.aerp.web;

import anni.aerp.domain.ErpOrder;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpOrderControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpOrderControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (ErpOrderController) applicationContext.getBean(
                "anni.aerp.web.ErpOrderController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        /*
                request.setRequestURI("/erporder/list.htm");
                mv = controller.handleRequest(request, response);
                mvHelper.assertModelAttributeAvailable(mv, "page");
                mvHelper.assertViewName(mv, "/aerp/erporder/listErpOrder");
        */
    }
}
