package anni.core.utils;

import java.lang.reflect.*;

import junit.framework.TestCase;


public class StringUtilsTest extends TestCase {
    public void testConstructor() {
        assertNotNull(new StringUtils());
    }

    public void testNotEmpty() {
        assertTrue(StringUtils.notEmpty(" "));
        assertTrue(StringUtils.notEmpty("str"));
        assertFalse(StringUtils.notEmpty(null));
        assertFalse(StringUtils.notEmpty(""));
    }
}
