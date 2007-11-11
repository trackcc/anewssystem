package anni.aerp2.web;

import anni.aerp2.domain.Erp2BuyOrderInfo;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BuyOrderInfoControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2BuyOrderInfoControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (Erp2BuyOrderInfoController) applicationContext
            .getBean("anni.aerp2.web.Erp2BuyOrderInfoController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/erp2buyorderinfo/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv,
            "/aerp2/erp2buyorderinfo/listErp2BuyOrderInfo");
    }
}
