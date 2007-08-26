package anni.core.utils;

import java.text.*;

import java.util.*;


public class DateUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static SimpleDateFormat customerFormat = new SimpleDateFormat(
            "yyyyMMdd");

    public static String date2Str(Date date) {
        try {
            return dateFormat.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String date2Str(Date date, String pattern) {
        try {
            customerFormat.applyPattern(pattern);

            return customerFormat.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    public static Date str2Date(String str) {
        try {
            return dateFormat.parse(str);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date getLastMonthLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return calendar.getTime();
    }
}
