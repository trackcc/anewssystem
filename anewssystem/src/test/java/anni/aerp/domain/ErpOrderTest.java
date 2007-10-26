package anni.aerp.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpOrderTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ErpOrderTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        ErpOrder entity = new ErpOrder();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setCode(null);
        assertNull(entity.getCode());
        entity.setOrderDate(null);
        assertNull(entity.getOrderDate());
        entity.setCustomer(null);
        assertNull(entity.getCustomer());
        entity.setLinkMan(null);
        assertNull(entity.getLinkMan());
        entity.setTel(null);
        assertNull(entity.getTel());
        entity.setAmount(null);
        assertNull(entity.getAmount());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setHandMan(null);
        assertNull(entity.getHandMan());
        entity.setPayment(null);
        assertNull(entity.getPayment());
        entity.setDelivery(null);
        assertNull(entity.getDelivery());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setInputMan(null);
        assertNull(entity.getInputMan());
        entity.setInputTime(null);
        assertNull(entity.getInputTime());
        entity.setErpOrderInfos(null);
        assertNull(entity.getErpOrderInfos());
    }
}
