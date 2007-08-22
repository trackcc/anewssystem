package anni.core.domain;

import junit.framework.TestCase;


public class SerializableNamedEntityBeanTest extends TestCase {
    SerializableNamedEntityBean bean = null;

    @Override
    protected void setUp() {
        bean = new SerializableNamedEntityBean();
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
