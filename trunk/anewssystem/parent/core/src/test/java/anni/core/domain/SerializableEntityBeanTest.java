package anni.core.domain;

import junit.framework.TestCase;


public class SerializableEntityBeanTest extends TestCase {
    SerializableEntityBean entityBean = null;

    @Override
    protected void setUp() {
        entityBean = new SerializableEntityBean();
    }

    @Override
    protected void tearDown() {
    }

    public void testGetId() {
        assertNull(entityBean.getId());
        entityBean.setId(new Long(1L));
        assertEquals(new Long(1L), entityBean.getId());
    }

    public void testEquals() {
        entityBean.setId(new Long(1L));

        SerializableEntityBean bean = new SerializableEntityBean(new Long(
                    1L));
        assertTrue(bean.equals(entityBean));
    }

    public void testEquals2() {
        SerializableEntityBean bean = new SerializableEntityBean();
        assertFalse(bean.equals(entityBean));
    }

    public void testEquals3() {
        assertFalse(entityBean.equals(new Object()));
    }

    public void testHashCode() {
        entityBean.setId(new Long(1L));
        assertEquals(1, entityBean.hashCode());
    }

    public void testHashCode2() {
        assertNotNull(entityBean.hashCode());
    }
}
