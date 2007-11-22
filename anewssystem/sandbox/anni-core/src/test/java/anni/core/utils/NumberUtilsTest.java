package anni.core.utils;

import java.lang.reflect.*;

import junit.framework.TestCase;


public class NumberUtilsTest extends TestCase {
    public void testConstructor() {
        assertNotNull(new NumberUtils());
    }

    public void testStr2Int() {
        assertEquals(1, NumberUtils.str2Int("1", 0));
        assertEquals(0, NumberUtils.str2Int("XX", 0));
    }

    public void testAdd() {
        assertEquals(1, NumberUtils.add(0, 1), 0.1);
    }

    public void testSubtract() {
        assertEquals(1, NumberUtils.subtract(1, 0), 0.1);
    }

    public void testMultiply() {
        assertEquals(1, NumberUtils.multiply(1, 1), 0.1);
        assertEquals(1, NumberUtils.multiply(1, 1, 1), 0.1);
    }

    public void testMultiply2() {
        try {
            NumberUtils.multiply(1, 1, -1);
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testDivide() {
        assertEquals(1, NumberUtils.divide(1, 1), 0.1);
        assertEquals(1, NumberUtils.divide(1, 1, 1), 0.1);
    }

    public void testDivide2() {
        try {
            NumberUtils.divide(1, 1, -1);
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testRound() {
        assertEquals(1, NumberUtils.round(1, 1), 0.1);
    }

    public void testRound2() {
        try {
            NumberUtils.round(1, -1);
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }
}
