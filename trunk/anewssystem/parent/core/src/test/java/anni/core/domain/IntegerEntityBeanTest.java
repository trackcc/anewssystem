package anni.core.domain;

import junit.framework.TestCase;


public class IntegerEntityBeanTest extends TestCase {
    IntegerEntityBean bean = null;

    @Override
    protected void setUp() {
        bean = new IntegerEntityBean();
    }

    @Override
    protected void tearDown() {
    }

    public void testGetId() {
        assertNull(bean.getId());
        bean.setId(1);
        assertEquals(new Integer(1), bean.getId());
    }

    public void testEquals() {
        bean.setId(1);

        IntegerEntityBean bean2 = new IntegerEntityBean(1);
        assertTrue(bean2.equals(bean));
    }

    public void testEquals2() {
        IntegerEntityBean bean2 = new IntegerEntityBean();
        assertFalse(bean2.equals(bean));

        assertFalse(bean.equals(new Object()));
        assertFalse(bean.equals(new SerializableEntityBean()));
        assertFalse(bean.equals(new LongEntityBean()));
        assertFalse(bean.equals(new StringEntityBean()));
    }

    public void testHashCode() {
        assertNotNull(bean.hashCode());

        bean.setId(1);
        assertEquals(1, bean.hashCode());
    }
}
