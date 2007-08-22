
package anni.tools;

public class Row {
    public String name;
    public String type;
    public String notNull;
    public String defaultValue;
    public String desc;
    public String pk;
    public String fk;

    public String getDesc() {
        return desc;
    }

    // transient
    public String getFieldName() {
        String columnName = name;
        String[] tmp = columnName.split("_");
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < tmp.length; i++) {
            String name = tmp[i].substring(0, 1).toUpperCase() + tmp[i].substring(1);
            buff.append(name);
        }
        String fieldName = buff.toString();
        String result = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        if (result.endsWith("Id")) {
            return result.substring(0, result.length() - 2);
        } else {
            return result;
        }
    }

    public boolean needValidate() {
        if (name.equals("id")
            || !fk.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public String getDepends() {
        StringBuffer buff = new StringBuffer();
        if (!notNull.equals("")) {
            buff.append("required,");
        }
        if (type.equals("int")) {
            buff.append("integer,");
        } else if (type.equals("bigint")) {
            buff.append("long,");
        } else if (type.equals("float")) {
            buff.append("float,");
        } else if (type.equals("datetime")) {
            buff.append("date,");
        } else if (type.startsWith("varchar(")) {
            buff.append("maxlength,");
        }
        if (buff.length() > 1) {
            buff.deleteCharAt(buff.length() - 1);
        }
        return buff.toString();
    }

    public String getArg() {
        if (type.startsWith("varchar(")) {
            return "\r\n        <arg name=\"maxlength\" key=\"${var:maxlength}\" resource=\"false\" position=\"1\"/>";
        }
        return "";
    }

    public String getVar() {
        if (type.equals("datetime")) {
            return "\r\n        <var>\r\n" +
                "          <var-name>datePatternStrict</var-name>\r\n" +
                "          <var-value>yyyy-MM-dd</var-value>\r\n" +
                "        </var>";
        } else if (type.startsWith("varchar(")) {
            String length = type.substring(8, type.length() - 1);
            return "\r\n        <var>\r\n" +
                "          <var-name>maxlength</var-name>\r\n" +
                "          <var-value>" + length + "</var-value>\r\n" +
                "        </var>";
        }
        return "";
    }
}
