package anni.aerp2.manager;

import anni.aerp2.domain.Erp2Supplier;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2SupplierManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2SupplierManagerTest.class);
    private Erp2SupplierManager erp2SupplierManager;

    public void setErp2SupplierManager(
        Erp2SupplierManager erp2SupplierManager) {
        this.erp2SupplierManager = erp2SupplierManager;
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
        assertNotNull(erp2SupplierManager);
    }
}
