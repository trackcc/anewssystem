package anni.core.utils;

import java.lang.reflect.*;

import junit.framework.TestCase;


public class Md5UtilsTest extends TestCase {
    public void testConstructor() {
        assertNotNull(new Md5Utils());
    }

    public void testMd5() throws Exception {
        assertNotNull(Md5Utils.md5("test"));
    }
}
