package anni.aerp2.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BuyOrderInfoTest extends TestCase {
    protected static Log logger = LogFactory.getLog(Erp2BuyOrderInfoTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Erp2BuyOrderInfo entity = new Erp2BuyOrderInfo();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setErp2Product(null);
        assertNull(entity.getErp2Product());
        entity.setErp2BuyOrder(null);
        assertNull(entity.getErp2BuyOrder());
        entity.setParameter(null);
        assertNull(entity.getParameter());
        entity.setNum(null);
        assertNull(entity.getNum());
        entity.setPrice(null);
        assertNull(entity.getPrice());
        entity.setTotalPrice(null);
        assertNull(entity.getTotalPrice());
        entity.setDescn(null);
        assertNull(entity.getDescn());
    }
}
