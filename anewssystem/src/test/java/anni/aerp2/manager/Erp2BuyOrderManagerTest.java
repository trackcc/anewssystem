package anni.aerp2.manager;

import anni.aerp2.domain.Erp2BuyOrder;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BuyOrderManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2BuyOrderManagerTest.class);
    private Erp2BuyOrderManager erp2BuyOrderManager;

    public void setErp2BuyOrderManager(
        Erp2BuyOrderManager erp2BuyOrderManager) {
        this.erp2BuyOrderManager = erp2BuyOrderManager;
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
        assertNotNull(erp2BuyOrderManager);
    }
}
