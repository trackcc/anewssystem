
package anni.tools;

import java.io.*;
import java.util.*;
import jxl.*;

public class ExcelUtils {
    public static List<Bean> read(String xls) throws Exception {
        return read(new File(xls));
    }

    public static List<Bean> read(File file) throws Exception {
        Workbook workbook = Workbook.getWorkbook(file);
        Sheet[] sheets = workbook.getSheets();
        List<Bean> list = new ArrayList<Bean>(sheets.length);
        for (int i = 0; i < sheets.length; i++) {
            Sheet sheet = sheets[i];
            Bean bean = new Bean(sheet.getName());
            try {
                // many2many：说明是多对多关系的中间表，不生成对应的manager和controller
                bean.type = sheet.getCell(7, 0).getContents();
            } catch (Exception ex) {
                //
            }
            for (int j = 1; j < sheet.getRows(); j++) {
                Row row = new Row();
                row.name = sheet.getCell(0, j).getContents();
                if ("".equals(row.name)) {
                    continue;
                }
                row.type = sheet.getCell(1, j).getContents();
                row.notNull = sheet.getCell(2, j).getContents();
                row.defaultValue = sheet.getCell(3, j).getContents();
                row.desc = sheet.getCell(4, j).getContents();
                row.pk = sheet.getCell(5, j).getContents();
                row.fk = sheet.getCell(6, j).getContents();
                bean.rowList.add(row);
            }
            list.add(bean);
        }
        return list;
    }

    public static String[] getClassNames(String xls) throws Exception {
        return getClassNames(new File(xls));
    }

    public static String[] getClassNames(File file) throws Exception {
        List<Bean> beanList = read(file);
        List<String> classList = new ArrayList<String>();
        for (int i = 0; i < beanList.size(); i++) {
            Bean bean = beanList.get(i);
            if (bean.type != null && bean.type.equals("many2many")) {
                continue;
            }
            String className = getClassName(bean.tableName);
            classList.add(className);
        }
        String[] classes = new String[classList.size()];
        return classList.toArray(classes);
    }

    public static String getClassName(String tableName) {
        String[] tmp = tableName.split("_");
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < tmp.length; i++) {
            String name = tmp[i].substring(0, 1).toUpperCase() + tmp[i].substring(1);
            buff.append(name);
        }
        return buff.toString();
    }
}
