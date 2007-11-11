package anni.aerp2.web;

import anni.aerp2.domain.Erp2SuccessBid;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2SuccessBidControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2SuccessBidControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (Erp2SuccessBidController) applicationContext.getBean(
                "anni.aerp2.web.Erp2SuccessBidController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        //request.setRequestURI("/erp2successbid/list.htm");
        //mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        //mvHelper.assertViewName(mv, "/aerp2/erp2successbid/listErp2SuccessBid");
    }
}
