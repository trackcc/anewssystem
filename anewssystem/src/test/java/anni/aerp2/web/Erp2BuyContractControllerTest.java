package anni.aerp2.web;

import anni.aerp2.domain.Erp2BuyContract;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BuyContractControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2BuyContractControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (Erp2BuyContractController) applicationContext.getBean(
                "anni.aerp2.web.Erp2BuyContractController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        //request.setRequestURI("/erp2buycontract/list.htm");
        //mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        //mvHelper.assertViewName(mv, "/aerp2/erp2buycontract/listErp2BuyContract");
    }
}
