package anni.core.utils;


/**
 * 字符串工具类.
 *
 * @author Lingo
 */
public class StringUtils {
    /** * 工具类需要的protected构造方法. */
    protected StringUtils() {
    }

    /**
     * 判断str是否为空.
     * 如果str==null或str长度等于零，就返回true
     *
     * @param str 传入字符串
     * @return 是否为空
     */
    public static boolean notEmpty(String str) {
        return (str != null) && (str.length() != 0);
    }
}
