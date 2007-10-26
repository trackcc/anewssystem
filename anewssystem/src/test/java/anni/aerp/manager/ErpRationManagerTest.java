package anni.aerp.manager;

import anni.aerp.domain.ErpRation;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpRationManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpRationManagerTest.class);
    private ErpRationManager erpRationManager;

    public void setErpRationManager(ErpRationManager erpRationManager) {
        this.erpRationManager = erpRationManager;
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
        assertNotNull(erpRationManager);
    }
}
