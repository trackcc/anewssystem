package anni.core.utils;

import java.lang.reflect.*;

import junit.framework.TestCase;


public class UserSessionTest extends TestCase {
    public void testConstructor() {
        assertNotNull(new UserSession());
    }

    public void testGet() {
        Object obj = new Object();
        UserSession.set("test", obj);

        Object object = UserSession.get("test");
        assertEquals(object, obj);
    }

    public void testGet2() {
        String str = new String();
        UserSession.set("str", str);

        String str2 = UserSession.get("str", String.class);
        assertEquals(str2, str);
    }
}
