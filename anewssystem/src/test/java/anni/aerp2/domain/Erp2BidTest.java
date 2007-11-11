package anni.aerp2.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2BidTest extends TestCase {
    protected static Log logger = LogFactory.getLog(Erp2BidTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Erp2Bid entity = new Erp2Bid();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setErp2Product(null);
        assertNull(entity.getErp2Product());
        entity.setCode(null);
        assertNull(entity.getCode());
        entity.setProductNum(null);
        assertNull(entity.getProductNum());
        entity.setParameter(null);
        assertNull(entity.getParameter());
        entity.setStartDate(null);
        assertNull(entity.getStartDate());
        entity.setEndDate(null);
        assertNull(entity.getEndDate());
        entity.setOpenDate(null);
        assertNull(entity.getOpenDate());
        entity.setDescn(null);
        assertNull(entity.getDescn());
    }
}
