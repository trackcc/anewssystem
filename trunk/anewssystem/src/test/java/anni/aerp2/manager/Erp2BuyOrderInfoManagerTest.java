package anni.aerp2.manager;

import anni.aerp2.domain.Erp2BuyOrderInfo;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BuyOrderInfoManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2BuyOrderInfoManagerTest.class);
    private Erp2BuyOrderInfoManager erp2BuyOrderInfoManager;

    public void setErp2BuyOrderInfoManager(
        Erp2BuyOrderInfoManager erp2BuyOrderInfoManager) {
        this.erp2BuyOrderInfoManager = erp2BuyOrderInfoManager;
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
        assertNotNull(erp2BuyOrderInfoManager);
    }
}
