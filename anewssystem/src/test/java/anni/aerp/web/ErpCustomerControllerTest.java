package anni.aerp.web;

import anni.aerp.domain.ErpCustomer;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpCustomerControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpCustomerControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (ErpCustomerController) applicationContext.getBean(
                "anni.aerp.web.ErpCustomerController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        /*
                request.setRequestURI("/erpcustomer/list.htm");
                mv = controller.handleRequest(request, response);
                mvHelper.assertModelAttributeAvailable(mv, "page");
                mvHelper.assertViewName(mv, "/aerp/erpcustomer/listErpCustomer");
        */
    }
}
