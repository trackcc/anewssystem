package anni.aerp.manager;

import anni.aerp.domain.ErpMaterial;

import anni.core.test.AbstractDaoTestCase;
import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpMaterialManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(ErpMaterialManagerTest.class);
    private ErpMaterialManager erpMaterialManager;

    public void setErpMaterialManager(
        ErpMaterialManager erpMaterialManager) {
        this.erpMaterialManager = erpMaterialManager;
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
        assertNotNull(erpMaterialManager);
    }
}
