package anni.aerp2.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2SupplierTest extends TestCase {
    protected static Log logger = LogFactory.getLog(Erp2SupplierTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Erp2Supplier entity = new Erp2Supplier();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setType(null);
        assertNull(entity.getType());
        entity.setLinkman(null);
        assertNull(entity.getLinkman());
        entity.setLead(null);
        assertNull(entity.getLead());
        entity.setTel(null);
        assertNull(entity.getTel());
        entity.setRegion(null);
        assertNull(entity.getRegion());
        entity.setArea(null);
        assertNull(entity.getArea());
        entity.setCode(null);
        assertNull(entity.getCode());
        entity.setZip(null);
        assertNull(entity.getZip());
        entity.setFax(null);
        assertNull(entity.getFax());
        entity.setEmail(null);
        assertNull(entity.getEmail());
        entity.setHomepage(null);
        assertNull(entity.getHomepage());
        entity.setAddress(null);
        assertNull(entity.getAddress());
        entity.setRank(null);
        assertNull(entity.getRank());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setErp2InviteBids(null);
        assertNull(entity.getErp2InviteBids());
        entity.setErp2Products(null);
        assertNull(entity.getErp2Products());
    }
}
