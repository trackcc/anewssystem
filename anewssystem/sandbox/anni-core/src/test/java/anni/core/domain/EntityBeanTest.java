package anni.core.domain;

import junit.framework.TestCase;


public class EntityBeanTest extends TestCase {
    EntityBean bean = null;

    @Override
    protected void setUp() {
        bean = new EntityBean();
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

        EntityBean bean2 = new EntityBean(1L);
        assertTrue(bean2.equals(bean));
    }

    public void testEquals2() {
        EntityBean bean2 = new EntityBean();
        assertFalse(bean2.equals(bean));
    }

    public void testEquals3() {
        assertFalse(bean.equals(new Object()));
    }

    public void testHashCode() {
        assertNotNull(bean.hashCode());

        bean.setId(1L);
        assertEquals(1, bean.hashCode());
    }
}
