package anni.aerp2.manager;

import anni.aerp2.domain.Erp2BuyContract;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BuyContractManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2BuyContractManagerTest.class);
    private Erp2BuyContractManager erp2BuyContractManager;

    public void setErp2BuyContractManager(
        Erp2BuyContractManager erp2BuyContractManager) {
        this.erp2BuyContractManager = erp2BuyContractManager;
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
        assertNotNull(erp2BuyContractManager);
    }
}
