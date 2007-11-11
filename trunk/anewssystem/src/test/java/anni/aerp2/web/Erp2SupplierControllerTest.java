package anni.aerp2.web;

import anni.aerp2.domain.Erp2Supplier;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2SupplierControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2SupplierControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (Erp2SupplierController) applicationContext.getBean(
                "anni.aerp2.web.Erp2SupplierController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        //request.setRequestURI("/erp2supplier/list.htm");
        //mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        //mvHelper.assertViewName(mv, "/aerp2/erp2supplier/listErp2Supplier");
    }

    public void testSave() throws Exception {
        request.setRequestURI("/erp2supplier/save.htm");
        request.addParameter("data",
            "{supplierValue:{town:0},products:[{id:-1,name:'name',type:1,material:'material',factory:'factory',price:0,unit:'unit'}]}");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, null);
    }
}
