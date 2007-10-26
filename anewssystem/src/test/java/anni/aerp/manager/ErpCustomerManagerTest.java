package anni.aerp.manager;

import anni.aerp.domain.ErpCustomer;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpCustomerManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpCustomerManagerTest.class);
    private ErpCustomerManager erpCustomerManager;

    public void setErpCustomerManager(
        ErpCustomerManager erpCustomerManager) {
        this.erpCustomerManager = erpCustomerManager;
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
        assertNotNull(erpCustomerManager);
    }
}
