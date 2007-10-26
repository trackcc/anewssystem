package anni.aerp.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpProductTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ErpProductTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        ErpProduct entity = new ErpProduct();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setCode(null);
        assertNull(entity.getCode());
        entity.setType(null);
        assertNull(entity.getType());
        entity.setBuyPrice(null);
        assertNull(entity.getBuyPrice());
        entity.setSalePrice(null);
        assertNull(entity.getSalePrice());
        entity.setUnit(null);
        assertNull(entity.getUnit());
        entity.setPic(null);
        assertNull(entity.getPic());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setInputMan(null);
        assertNull(entity.getInputMan());
        entity.setInputTime(null);
        assertNull(entity.getInputTime());
    }
}
