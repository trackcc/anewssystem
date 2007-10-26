package anni.aerp.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpMaterialTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ErpMaterialTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        ErpMaterial entity = new ErpMaterial();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setSource(null);
        assertNull(entity.getSource());
        entity.setSupplier(null);
        assertNull(entity.getSupplier());
        entity.setPrice(null);
        assertNull(entity.getPrice());
        entity.setUnit(null);
        assertNull(entity.getUnit());
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
