package anni.aerp.web;

import anni.aerp.domain.ErpMaterial;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpMaterialControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpMaterialControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (ErpMaterialController) applicationContext.getBean(
                "anni.aerp.web.ErpMaterialController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        /*
                request.setRequestURI("/erpmaterial/list.htm");
                mv = controller.handleRequest(request, response);
                mvHelper.assertModelAttributeAvailable(mv, "page");
                mvHelper.assertViewName(mv, "/aerp/erpmaterial/listErpMaterial");
        */
    }
}
