package anni.core.domain;

import junit.framework.TestCase;


public class LongEntityBeanTest extends TestCase {
    LongEntityBean bean = null;

    @Override
    protected void setUp() {
        bean = new LongEntityBean();
    }

    @Override
    protected void tearDown() {
    }

    public void testGetId() {
        assertNull(bean.getId());
        bean.setId(1L);
        assertEquals(new Long(1L), bean.getId());
    }

    public void testEquals() {
        bean.setId(1L);

        LongEntityBean bean2 = new LongEntityBean(1L);
        assertTrue(bean2.equals(bean));
    }

    public void testEquals2() {
        LongEntityBean bean2 = new LongEntityBean();
        assertFalse(bean2.equals(bean));

        assertFalse(bean.equals(new Object()));
        assertFalse(bean.equals(new SerializableEntityBean()));
        assertFalse(bean.equals(new IntegerEntityBean()));
        assertFalse(bean.equals(new StringEntityBean()));
    }

    public void testHashCode() {
        assertNotNull(bean.hashCode());

        bean.setId(1L);
        assertEquals(1, bean.hashCode());
    }
}
