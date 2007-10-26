package anni.aerp.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpCustomerTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ErpCustomerTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        ErpCustomer entity = new ErpCustomer();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setCode(null);
        assertNull(entity.getCode());
        entity.setType(null);
        assertNull(entity.getType());
        entity.setZip(null);
        assertNull(entity.getZip());
        entity.setLeader(null);
        assertNull(entity.getLeader());
        entity.setFax(null);
        assertNull(entity.getFax());
        entity.setLinkMan(null);
        assertNull(entity.getLinkMan());
        entity.setEmail(null);
        assertNull(entity.getEmail());
        entity.setTel(null);
        assertNull(entity.getTel());
        entity.setHomepage(null);
        assertNull(entity.getHomepage());
        entity.setProvince(null);
        assertNull(entity.getProvince());
        entity.setCity(null);
        assertNull(entity.getCity());
        entity.setTown(null);
        assertNull(entity.getTown());
        entity.setAddress(null);
        assertNull(entity.getAddress());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setSource(null);
        assertNull(entity.getSource());
        entity.setRank(null);
        assertNull(entity.getRank());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setInputMan(null);
        assertNull(entity.getInputMan());
        entity.setInputTime(null);
        assertNull(entity.getInputTime());
    }
}
