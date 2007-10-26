package anni.aerp.manager;

import anni.aerp.domain.ErpOrder;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpOrderManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpOrderManagerTest.class);
    private ErpOrderManager erpOrderManager;

    public void setErpOrderManager(ErpOrderManager erpOrderManager) {
        this.erpOrderManager = erpOrderManager;
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
        assertNotNull(erpOrderManager);
    }
}
