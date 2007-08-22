package anni.core.domain;

import junit.framework.TestCase;


public class StringNamedEntityBeanTest extends TestCase {
    StringNamedEntityBean bean = null;

    @Override
    protected void setUp() {
        bean = new StringNamedEntityBean();
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
