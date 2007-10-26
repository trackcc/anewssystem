package anni.aerp.manager;

import anni.aerp.domain.ErpOrderInfo;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpOrderInfoManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpOrderInfoManagerTest.class);
    private ErpOrderInfoManager erpOrderInfoManager;

    public void setErpOrderInfoManager(
        ErpOrderInfoManager erpOrderInfoManager) {
        this.erpOrderInfoManager = erpOrderInfoManager;
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
        assertNotNull(erpOrderInfoManager);
    }
}
