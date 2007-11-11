package anni.aerp2.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BuyContractTest extends TestCase {
    protected static Log logger = LogFactory.getLog(Erp2BuyContractTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Erp2BuyContract entity = new Erp2BuyContract();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setErp2Supplier(null);
        assertNull(entity.getErp2Supplier());
        entity.setCode(null);
        assertNull(entity.getCode());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setLinkman(null);
        assertNull(entity.getLinkman());
        entity.setProvideDate(null);
        assertNull(entity.getProvideDate());
        entity.setReceipt(null);
        assertNull(entity.getReceipt());
        entity.setContractDate(null);
        assertNull(entity.getContractDate());
        entity.setUsername(null);
        assertNull(entity.getUsername());
    }
}
