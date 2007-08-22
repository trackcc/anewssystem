package anni.core.web.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.processors.JsonValueProcessor;


/**
 * @auhtor Lingo
 * @since 2007-08-02
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
    private DateFormat dateFormat;

    /**
     * 构造方法.
     *
     * @param datePattern 日期格式
     */
    public DateJsonValueProcessor(String datePattern) {
        dateFormat = new SimpleDateFormat(datePattern);
    }

    public Object processArrayValue(Object value) {
        return process(value);
    }

    public Object processObjectValue(String key, Object value) {
        return process(value);
    }

    private Object process(Object value) {
        return dateFormat.format((Date) value);
    }
}
