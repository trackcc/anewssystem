package anni.aerp2.manager;

import anni.aerp2.domain.Erp2Product;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2ProductManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(Erp2ProductManagerTest.class);
    private Erp2ProductManager erp2ProductManager;

    public void setErp2ProductManager(
        Erp2ProductManager erp2ProductManager) {
        this.erp2ProductManager = erp2ProductManager;
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
        assertNotNull(erp2ProductManager);
    }
}
