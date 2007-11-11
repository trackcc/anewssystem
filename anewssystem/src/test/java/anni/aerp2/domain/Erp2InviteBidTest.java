package anni.aerp2.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2InviteBidTest extends TestCase {
    protected static Log logger = LogFactory.getLog(Erp2InviteBidTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Erp2InviteBid entity = new Erp2InviteBid();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setErp2Supplier(null);
        assertNull(entity.getErp2Supplier());
        entity.setErp2Bid(null);
        assertNull(entity.getErp2Bid());
        entity.setPrice(null);
        assertNull(entity.getPrice());
        entity.setAddress(null);
        assertNull(entity.getAddress());
        entity.setTel(null);
        assertNull(entity.getTel());
        entity.setBidDate(null);
        assertNull(entity.getBidDate());
        entity.setEmail(null);
        assertNull(entity.getEmail());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setErp2SuccessBids(null);
        assertNull(entity.getErp2SuccessBids());
    }
}
