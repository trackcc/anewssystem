package anni.aerp.web;

import anni.aerp.domain.ErpRation;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpRationControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpRationControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (ErpRationController) applicationContext.getBean(
                "anni.aerp.web.ErpRationController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        /*
                request.setRequestURI("/erpration/list.htm");
                mv = controller.handleRequest(request, response);
                mvHelper.assertModelAttributeAvailable(mv, "page");
                mvHelper.assertViewName(mv, "/aerp/erpration/listErpRation");
        */
    }
}
