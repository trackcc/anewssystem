package anni.aerp2.manager;

import anni.aerp2.domain.Erp2InviteBid;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2InviteBidManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2InviteBidManagerTest.class);
    private Erp2InviteBidManager erp2InviteBidManager;

    public void setErp2InviteBidManager(
        Erp2InviteBidManager erp2InviteBidManager) {
        this.erp2InviteBidManager = erp2InviteBidManager;
    }

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
    }

    @Override
    public void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() {
        assertNotNull(erp2InviteBidManager);
    }
}
