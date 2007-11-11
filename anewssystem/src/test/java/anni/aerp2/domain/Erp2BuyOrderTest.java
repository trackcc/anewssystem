package anni.aerp2.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BuyOrderTest extends TestCase {
    protected static Log logger = LogFactory.getLog(Erp2BuyOrderTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Erp2BuyOrder entity = new Erp2BuyOrder();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setErp2Supplier(null);
        assertNull(entity.getErp2Supplier());
        entity.setOrderDate(null);
        assertNull(entity.getOrderDate());
        entity.setLinkman(null);
        assertNull(entity.getLinkman());
        entity.setCode(null);
        assertNull(entity.getCode());
        entity.setUsername(null);
        assertNull(entity.getUsername());
        entity.setPrice(null);
        assertNull(entity.getPrice());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setProvideDate(null);
        assertNull(entity.getProvideDate());
        entity.setAudit(null);
        assertNull(entity.getAudit());
        entity.setTotalPrice(null);
        assertNull(entity.getTotalPrice());
        entity.setErp2BuyOrderInfos(null);
        assertNull(entity.getErp2BuyOrderInfos());
    }
}
