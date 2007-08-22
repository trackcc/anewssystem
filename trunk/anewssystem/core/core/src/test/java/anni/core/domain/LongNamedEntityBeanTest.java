package anni.core.domain;

import junit.framework.TestCase;


public class LongNamedEntityBeanTest extends TestCase {
    LongNamedEntityBean bean = null;

    @Override
    protected void setUp() {
        bean = new LongNamedEntityBean();
    }

    @Override
    protected void tearDown() {
    }

    public void testNamed() {
        assertNull(bean.getName());
        bean.setName("name");
        assertEquals("name", bean.getName());
    }
}
