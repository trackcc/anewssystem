package anni.core.utils;

import java.math.BigDecimal;


/**
 * 提供高精度的运算支持.
 * 所以函数以double为参数类型，兼容int与float.
 *
 * @author calvin
 */
public class NumberUtils {
    /** * 工具类的protected构造方法. */
    protected NumberUtils() {
    }

    /**
     * 解析字符串，转换成整数，出现异常则返回默认值.
     *
     * @param str 需要转换的字符串
     * @param defaultValue 出现异常返回的默认值
     * @return 返回整数
     */
    public static int str2Int(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            System.err.println(ex);

            return defaultValue;
        }
    }

    /**
     * 精确的加法运算.
     *
     * @param v1 加数
     * @param v2 加数
     * @return 和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.add(b2).doubleValue();
    }

    /**
     *
     * 精确的减法运算.
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 结果
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算.
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算，并对运算结果截位.
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @param scale 运算结果小数后精确的位数
     * @return 积
     */
    public static double multiply(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP)
                 .doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算.
     *
     * @see #divide(double, double, int)
     * @param v1 被除数
     * @param v2 除数
     * @return 商
     */
    public static double divide(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.divide(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算.
     * 由scale参数指定精度，以后的数字四舍五入.
     *
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 商
     */
    public static double divide(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理.
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(v);

        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
