package anni.core.utils;

import java.lang.reflect.*;

import java.util.*;

import junit.framework.TestCase;


public class BeanUtilsTest extends TestCase {
    public void testConstructor() {
        assertNotNull(new BeanUtils());
    }

    public void testGetDeclaredField() throws Exception {
        Field field = TestBean.class.getDeclaredField("stringField");
        TestBean testBean = new TestBean();
        assertEquals(field,
            BeanUtils.getDeclaredField(testBean, "stringField"));
        assertEquals(field,
            BeanUtils.getDeclaredField(TestBean.class, "stringField"));
    }

    public void testGetDeclaredField2() throws Exception {
        try {
            BeanUtils.getDeclaredField(TestBean.class, "stringField0");
            fail();
        } catch (NoSuchFieldException ex) {
            assertTrue(true);
        }
    }

    public void testForceGetProperty() throws Exception {
        TestBean testBean = new TestBean();
        assertEquals("string",
            BeanUtils.forceGetProperty(testBean, "stringField"));
    }

    public void testForceSetProperty() throws Exception {
        TestBean testBean = new TestBean();
        BeanUtils.forceSetProperty(testBean, "stringField", "force");
        assertEquals("force", testBean.getStringField());
    }

    public void testInvokePrivateMethod() throws Exception {
        TestBean testBean = new TestBean();
        assertEquals("doPrivate",
            BeanUtils.invokePrivateMethod(testBean, "doPrivate"));
    }

    public void testInvokePrivateMethod2() throws Exception {
        TestBean testBean = new TestBean();
        assertEquals("str",
            BeanUtils.invokePrivateMethod(testBean, "invoke1", "str"));
    }

    public void testInvokePrivateMethod3() throws Exception {
        try {
            TestBean testBean = new TestBean();
            assertNull(BeanUtils.invokePrivateMethod(testBean, "invoke2"));
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testInvokePrivateMethod4() throws Exception {
        TestBean2 testBean = new TestBean2();
        assertEquals("str",
            BeanUtils.invokePrivateMethod(testBean, "invoke1", "str"));
    }

    public void testInvokePrivateMethod5() throws Exception {
        try {
            TestBean testBean = new TestBean();
            assertNull(BeanUtils.invokePrivateMethod(testBean, "invoke0"));
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testGetFieldsByType() throws Exception {
        TestBean testBean = new TestBean();
        List<Field> fieldList = BeanUtils.getFieldsByType(testBean,
                String.class);
        assertEquals(2, fieldList.size());
    }

    public void testGetPropertyType() throws Exception {
        Class clazz = BeanUtils.getPropertyType(TestBean.class,
                "stringField");
        assertEquals(String.class, clazz);
    }

    public void testGetGetterName() throws Exception {
        String methodName = BeanUtils.getGetterName(TestBean.class,
                "stringField");
        assertEquals("getStringField", methodName);
    }

    public void testGetGetterName2() throws Exception {
        String methodName = BeanUtils.getGetterName(TestBean.class,
                "booleanField");
        assertEquals("isBooleanField", methodName);
    }

    public void testGetGetterMethod() throws Exception {
        Method m = TestBean.class.getDeclaredMethod("getStringField",
                new Class[0]);
        Method method = BeanUtils.getGetterMethod(TestBean.class,
                "stringField");
        assertEquals(m, method);
    }

    public void testGetGetterMethod2() throws Exception {
        Method method = BeanUtils.getGetterMethod(TestBean.class,
                "stringField0");
        assertNull(method);
    }

    public void testGetGetterMethod3() throws Exception {
        Method method = BeanUtils.getGetterMethod(TestBean.class,
                "stringField2");
        assertNull(method);
    }

    static class TestBean {
        private String stringField = "string";
        private String stringField2 = "string2";
        private boolean booleanField = true;

        public String getStringField() {
            return stringField;
        }

        public boolean getBooleanField() {
            return booleanField;
        }

        private String doPrivate() {
            return "doPrivate";
        }

        private String invoke1(String str) {
            return str;
        }

        private void invoke2() throws Exception {
            throw new Exception("invoke2");
        }
    }

    static class TestBean2 extends TestBean {
    }
}
