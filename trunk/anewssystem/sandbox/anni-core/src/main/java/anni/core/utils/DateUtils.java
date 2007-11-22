package anni.core.utils;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具类.
 *
 * @author Lingo
 */
public class DateUtils {
    /** * 年-月-日，默认日期格式. */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");

    /** * 紧缩日期格式. */
    private static SimpleDateFormat customerFormat = new SimpleDateFormat(
            "yyyyMMdd");

    /** * protected构造方法. */
    protected DateUtils() {
    }

    /**
     * 使用默认格式将日期转换成字符串.
     *
     * @param date 需要转换的日期
     * @return 转换后的字符串，如果发生异常，返回空字符串
     */
    public static String date2Str(Date date) {
        if (date == null) {
            return "";
        } else {
            return dateFormat.format(date);
        }
    }

    /**
     * 使用自定义格式，将日期转换成字符串.
     *
     * @param date 需要转换的日期
     * @param pattern 自定义格式
     * @return 转换后的字符串，如果发生异常，返回空字符串
     */
    public static String date2Str(Date date, String pattern) {
        if (date == null) {
            return "";
        }

        try {
            customerFormat.applyPattern(pattern);
        } catch (IllegalArgumentException ex) {
            return "";
        }

        return customerFormat.format(date);
    }

    /**
     * 使用默认格式，将字符串转换成日期.
     *
     * @param str 需要转换的字符串
     * @return 转换后的日期，如果发生异常，返回null
     */
    public static Date str2Date(String str) {
        try {
            return dateFormat.parse(str);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 返回上个月的最后一天.
     *
     * @return 日期
     */
    public static Date getLastMonthLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return calendar.getTime();
    }
}
