package anni.core.grid;

import java.util.HashSet;
import java.util.Set;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LongGridBeanTest extends TestCase {
    protected static Log logger = LogFactory.getLog(LongGridBeanTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        LongGridBean bean = new LongGridBean();
        assertNull(bean.getId());
        bean.setId(1L);
        assertEquals(new Long(1L), bean.getId());
    }
}
