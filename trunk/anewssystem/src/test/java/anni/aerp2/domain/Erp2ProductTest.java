package anni.aerp2.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Erp2ProductTest extends TestCase {
    protected static Log logger = LogFactory.getLog(Erp2ProductTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Erp2Product entity = new Erp2Product();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setErp2Supplier(null);
        assertNull(entity.getErp2Supplier());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setType(null);
        assertNull(entity.getType());
        entity.setMaterial(null);
        assertNull(entity.getMaterial());
        entity.setFactory(null);
        assertNull(entity.getFactory());
        entity.setPrice(null);
        assertNull(entity.getPrice());
        entity.setUnit(null);
        assertNull(entity.getUnit());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setNum(null);
        assertNull(entity.getNum());
        entity.setTotal(null);
        assertNull(entity.getTotal());
    }
}
