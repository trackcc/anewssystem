package anni.core.utils;

import java.lang.reflect.*;

import java.util.*;

import junit.framework.TestCase;


public class DateUtilsTest extends TestCase {
    public void testConstructor() {
        assertNotNull(new DateUtils());
    }

    public void testDate2Str() {
        String str = "2007-11-22";
        Date date = DateUtils.str2Date(str);
        String result = DateUtils.date2Str(date);
        assertEquals(result, str);
    }

    public void testDate2Str2() {
        String str = "2007-11-22";
        Date date = DateUtils.str2Date(str);
        String result = DateUtils.date2Str(date, "yyyy-MM-dd");
        assertEquals(result, str);
    }

    public void testDate2Str3() {
        assertEquals("", DateUtils.date2Str(null));
    }

    public void testDate2Str4() {
        assertEquals("", DateUtils.date2Str(null, "yyyy-MM-dd"));
    }

    public void testDate2Str5() {
        assertEquals("", DateUtils.date2Str(new Date(), "XXXX"));
    }

    public void testStr2Date() {
        assertNull(DateUtils.str2Date("XXX"));
    }

    public void testGetLastMonthLastDate() {
        assertNotNull(DateUtils.getLastMonthLastDate());
    }
}
