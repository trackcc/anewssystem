package anni.aerp2.web;

import anni.aerp2.domain.Erp2InviteBid;

import anni.core.test.AbstractWebTests;
import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2InviteBidControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2InviteBidControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (Erp2InviteBidController) applicationContext.getBean(
                "anni.aerp2.web.Erp2InviteBidController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);

        //request.setRequestURI("/erp2invitebid/list.htm");
        //mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        //mvHelper.assertViewName(mv, "/aerp2/erp2invitebid/listErp2InviteBid");
    }
}
