
package anni.tools;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import jxl.*;

@SuppressWarnings("unchecked")
public class GenHibernateReveng {
    static String basedir = null;
    static String inputFileName = null;
    static String packageName = null;
    static String prefix = null;

    /** * 根据命令行参数进行初始化. */
    static void init(String[] args) throws Exception {
        basedir = args[0];
        packageName = args[1];
        inputFileName = args[2];
        prefix = args[3];
    }

    static void genXml(List<Bean> beanList) {
        List list = new ArrayList();
        for (Bean bean : beanList) {
            if (!bean.isDisplay()) {
                continue;
            }
            String className = getClassName(bean.tableName);
            list.add(className);
        }
        makeReveng(list);
    }

    static String getClassName(String tableName) {
        String[] tmp = tableName.split("_");
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < tmp.length; i++) {
            String name = tmp[i].substring(0, 1).toUpperCase() + tmp[i].substring(1);
            buff.append(name);
        }
        return buff.toString();
    }

    static String getFieldName(String columnName) {
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


    static void makeReveng(List list) {
        try {
            PrintWriter out = new PrintWriter(basedir + "/hibernate.reveng.xml");
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<!DOCTYPE hibernate-reverse-engineering SYSTEM \"http://hibernate.sourceforge.net/hibernate-reverse-engineering-3.0.dtd\">");
            out.println("<hibernate-reverse-engineering>");
            out.println("  <table-filter match-name=\".*\" match-schema=\"PUBLIC\"/>");

            for (int i = 0; i < list.size(); i++) {
                String name = (String) list.get(i);

                Pattern p = Pattern.compile("[A-Z][a-z]*");
                Matcher m = p.matcher(name);
                StringBuffer buff = new StringBuffer();
                while (m.find()) {
                    buff.append(m.group().toUpperCase()).append("_");
                }
                if (buff.length() > 1) {
                    buff.deleteCharAt(buff.length() - 1);
                }

                out.println("  <table schema=\"PUBLIC\" name=\"" + prefix.toUpperCase() +
                    buff.toString() + "\"" + " class=\"" + packageName + name + "\">\r\n" +
                    "    <primary-key>\r\n" +
                    "      <generator class=\"increment\"/>\r\n" +
                    "    </primary-key>\r\n" +
                    "  </table>");
            }
            out.println("</hibernate-reverse-engineering>");
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** * main. */
    public static void main(String[] args) throws Exception {
        init(args);

        System.out.println("start...");

        List<Bean> beanList = ExcelUtils.read(inputFileName);

        genXml(beanList);

        System.out.println("end...");
    }

}
