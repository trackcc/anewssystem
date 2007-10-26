package anni.aerp.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ErpRationTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ErpRationTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        ErpRation entity = new ErpRation();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setProduct(null);
        assertNull(entity.getProduct());
        entity.setMaterial(null);
        assertNull(entity.getMaterial());
        entity.setRate(null);
        assertNull(entity.getRate());
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
