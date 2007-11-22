package anni.core.web.prototype;

import java.beans.PropertyEditorSupport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


/**
 * 为了支持commons-validator而增加的工具类.
 * 之所以会有这个类，是因为一个很。。。。说不清楚的东西。
 *
 * 本来我想按照springside中的做法，在客户端和服务器端都用上commons-validator做数据校验
 * 现实是commons-validator并没有springside里介绍的那样易用
 * 碰到的第一个问题是不知道如何验证“两次输入的密码是否相等”，不知道怎么向commons-validator里加扩展
 *
 * 现在遇到的问题是，它只能解析字符串，无论什么对象都当作字符串验证，我明明已经把yyyy-MM-dd解析成Date了
 * 它还要调用Date的toString()方法，自己再校验一遍，结果就出错了呗。没办法，我只好自己把Date的toString()重写一遍
 * 用自己的DateFormat，转换成yyyy-MM-dd格式的，才能通过校验
 *
 * 其实这里看来springMVC的流程也有些问题，既然数据竟然是先绑定，后验证。如果日期格式错误，先抛出的是无法解析的错误。
 * 不是应该先验证再解析吗？现在找不到它这些流程代码具体写在哪里，如果找到的化，就赶快改过来。
 * 但又不知道commons-validator是否支持直接从request里抽出数据进行验证啊。
 *
 * 越做java越觉得java开发技术都很不程序。唉……遇到的解决方案都是半调子。
 *
 * 多说一句，springside里不知道是不是为了避免这个问题，Book.java里的时间字段都是String类型，唉……都无语了。
 *
 * @author:Lingo
 * @date:2006-11-08
 */
public class SimpleDateEditor extends PropertyEditorSupport {
    /**
     * 默认的时间格式为yyy-MM-dd.
     */
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 把一个String转换成一个Date.
     *
     * @param text String.
     */
    public void setAsText(String text) {
        if ((text == null) || (text.trim().length() < 1)) {
            setValue(null);
        } else {
            try {
                Date date = dateFormat.parse(text);
                Date extDate = new ExtDate(date.getTime());
                setValue(extDate);
            } catch (ParseException ex) {
                throw new IllegalArgumentException(
                    "Could not parse date: " + ex.getMessage());
            }
        }
    }

    /**
     * 把一个Date转换成一个String.
     *
     * @return String
     */
    public String getAsText() {
        Date value = (Date) getValue();

        if (value == null) {
            return "";
        } else {
            return dateFormat.format(value);
        }
    }

    /**
     * 我要默认显示的toString()，是yyyy-MM-dd格式的时间.
     *
     * @author:Lingo
     * @date:2006-11-08
     */
    public static class ExtDate extends Date {
        /** * 持久化. */
        private static final long serialVersionUID = -1;

        /** * 日期格式. */
        private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        /**
         * 构造方法.
         *
         * @param time 长整形数值
         */
        public ExtDate(long time) {
            super(time);
        }

        /**
         * 覆盖toString()方法，返回格式化的数据.
         *
         * @return String
         */
        @Override
        public String toString() {
            return dateFormat.format(this);
        }
    }
}
