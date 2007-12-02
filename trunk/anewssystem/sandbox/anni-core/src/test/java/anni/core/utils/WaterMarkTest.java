package anni.core.utils;

import java.io.*;

import java.lang.reflect.*;

import junit.framework.TestCase;


public class WaterMarkTest extends TestCase {
    public void testConstructor() {
        assertNotNull(new WaterMark());
    }

    public void testExecute() {
        try {
            WaterMark.execute(null);
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testExecute2() throws Exception {
        String fileName = WaterMark.execute(
                "target/test-classes/image/1.jpg");

        assertEquals("target/test-classes/image/1.jpg", fileName);
    }

    public void testExecute3() throws Exception {
        String fileName = WaterMark.execute(
                "target/test-classes/image/1.gif");

        assertEquals("target/test-classes/image/1.jpg", fileName);
    }

    public void testExecute4() throws Exception {
        String fileName = WaterMark.execute(
                "target/test-classes/image/1.png");

        assertEquals("target/test-classes/image/1.jpg", fileName);
    }

    public void testExecute5() throws Exception {
        String fileName = WaterMark.execute(
                "target/test-classes/image/1_jpg");

        assertEquals("target/test-classes/image/1_jpg.jpg", fileName);
    }

    public void testGetFormatName() throws IOException {
        assertEquals("JPEG",
            WaterMark.getFormatName(
                new File("target/test-classes/image/1_jpg")));
    }

    public void testGetFormatName2() throws IOException {
        try {
            WaterMark.getFormatName(new File("pom.xml"));
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }
}
