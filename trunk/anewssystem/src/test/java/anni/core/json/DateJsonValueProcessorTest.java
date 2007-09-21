package anni.core.json;

import java.io.*;

import java.text.*;

import java.util.*;

import anni.core.tree.*;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DateJsonValueProcessorTest extends TestCase {
    protected static Log logger = LogFactory.getLog(DateJsonValueProcessorTest.class);
    protected DateJsonValueProcessor proc;

    @Override
    protected void setUp() {
        proc = new DateJsonValueProcessor("yyyy-MM-dd");
    }

    @Override
    protected void tearDown() {
        proc = null;
    }

    public void testTrue() {
        assertNotNull(proc);
    }

    public void testProcessArrayValue() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(sdf.format(date), proc.processArrayValue(date));
    }

    public void testProcessObjectValue() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(sdf.format(date), proc.processObjectValue("key", date));
    }

    public void testProcessObjectValueForNull() {
        assertNull(proc.processObjectValue("key", null));
    }
}
