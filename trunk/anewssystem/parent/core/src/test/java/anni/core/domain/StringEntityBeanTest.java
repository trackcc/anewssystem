package anni.core.domain;

import junit.framework.TestCase;


public class StringEntityBeanTest extends TestCase {
    StringEntityBean bean = null;

    @Override
    protected void setUp() {
        bean = new StringEntityBean();
    }

    @Override
    protected void tearDown() {
    }

    public void testGetId() {
        assertNull(bean.getId());
        bean.setId("1");
        assertEquals("1", bean.getId());
    }

    public void testEquals() {
        bean.setId("1");

        StringEntityBean bean2 = new StringEntityBean("1");
        assertTrue(bean2.equals(bean));
    }

    public void testEquals2() {
        StringEntityBean bean2 = new StringEntityBean();
        assertFalse(bean2.equals(bean));

        assertFalse(bean.equals(new Object()));
        assertFalse(bean.equals(new SerializableEntityBean()));
        assertFalse(bean.equals(new LongEntityBean()));
        assertFalse(bean.equals(new IntegerEntityBean()));
    }

    public void testHashCode() {
        assertNotNull(bean.hashCode());

        bean.setId("1");
        assertEquals(49, bean.hashCode());
    }
}
