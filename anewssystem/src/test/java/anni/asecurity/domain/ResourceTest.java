package anni.asecurity.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ResourceTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ResourceTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Resource entity = new Resource();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setResType(null);
        assertNull(entity.getResType());
        entity.setResString(null);
        assertNull(entity.getResString());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setRoles(null);
        assertNull(entity.getRoles());
    }
}
