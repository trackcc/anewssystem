package anni.aerp2.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2SuccessBidTest extends TestCase {
    protected static Log logger = LogFactory.getLog(Erp2SuccessBidTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Erp2SuccessBid entity = new Erp2SuccessBid();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setErp2InviteBid(null);
        assertNull(entity.getErp2InviteBid());
        entity.setPublishDate(null);
        assertNull(entity.getPublishDate());
        entity.setPrice(null);
        assertNull(entity.getPrice());
        entity.setDescn(null);
        assertNull(entity.getDescn());
    }
}
