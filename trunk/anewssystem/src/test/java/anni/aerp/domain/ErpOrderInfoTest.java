package anni.aerp.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpOrderInfoTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ErpOrderInfoTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        ErpOrderInfo entity = new ErpOrderInfo();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setErpOrder(null);
        assertNull(entity.getErpOrder());
        entity.setProduct(null);
        assertNull(entity.getProduct());
        entity.setType(null);
        assertNull(entity.getType());
        entity.setUnit(null);
        assertNull(entity.getUnit());
        entity.setWidth(null);
        assertNull(entity.getWidth());
        entity.setHeight(null);
        assertNull(entity.getHeight());
        entity.setArea(null);
        assertNull(entity.getArea());
        entity.setPrice(null);
        assertNull(entity.getPrice());
        entity.setAmount(null);
        assertNull(entity.getAmount());
        entity.setNum(null);
        assertNull(entity.getNum());
        entity.setUnitType(null);
        assertNull(entity.getUnitType());
        entity.setStatus(null);
        assertNull(entity.getStatus());
    }
}
