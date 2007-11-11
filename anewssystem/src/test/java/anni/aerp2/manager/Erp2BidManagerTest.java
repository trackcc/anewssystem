package anni.aerp2.manager;

import anni.aerp2.domain.Erp2Bid;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BidManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2BidManagerTest.class);
    private Erp2BidManager erp2BidManager;

    public void setErp2BidManager(Erp2BidManager erp2BidManager) {
        this.erp2BidManager = erp2BidManager;
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
        assertNotNull(erp2BidManager);
    }
}
