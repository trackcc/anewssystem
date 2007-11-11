package anni.aerp2.manager;

import anni.aerp2.domain.Erp2SuccessBid;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2SuccessBidManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(Erp2SuccessBidManagerTest.class);
    private Erp2SuccessBidManager erp2SuccessBidManager;

    public void setErp2SuccessBidManager(
        Erp2SuccessBidManager erp2SuccessBidManager) {
        this.erp2SuccessBidManager = erp2SuccessBidManager;
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
        assertNotNull(erp2SuccessBidManager);
    }
}
