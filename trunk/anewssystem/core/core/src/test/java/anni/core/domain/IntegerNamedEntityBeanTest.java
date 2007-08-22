package anni.core.domain;

import junit.framework.TestCase;


public class IntegerNamedEntityBeanTest extends TestCase {
    IntegerNamedEntityBean bean = null;

    @Override
    protected void setUp() {
        bean = new IntegerNamedEntityBean();
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
