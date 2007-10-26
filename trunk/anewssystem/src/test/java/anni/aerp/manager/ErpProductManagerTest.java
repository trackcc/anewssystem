package anni.aerp.manager;

import anni.aerp.domain.ErpProduct;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpProductManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpProductManagerTest.class);
    private ErpProductManager erpProductManager;

    public void setErpProductManager(ErpProductManager erpProductManager) {
        this.erpProductManager = erpProductManager;
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
        assertNotNull(erpProductManager);
    }
}
