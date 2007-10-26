package anni.aerp.web;

import anni.aerp.domain.ErpProduct;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpProductControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpProductControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (ErpProductController) applicationContext.getBean(
                "anni.aerp.web.ErpProductController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        /*
                request.setRequestURI("/erpproduct/list.htm");
                mv = controller.handleRequest(request, response);
                mvHelper.assertModelAttributeAvailable(mv, "page");
                mvHelper.assertViewName(mv, "/aerp/erpproduct/listErpProduct");
        */
    }
}
