
package anni.tools;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import jxl.*;

@SuppressWarnings("unchecked")
public class GenXml {
    static String basedir = null;
    static String inputFileName = null;
    static String packageName = null;
    static String projectName = null;

    /** * 根据命令行参数进行初始化. */
    static void init(String[] args) throws Exception {
        basedir = args[0];
        packageName = args[1];
        inputFileName = args[2];
        projectName = args[3];
    }

    public static void genHibernate() throws Exception {
        String[] classes = ExcelUtils.getClassNames(inputFileName);
        Map model = new HashMap();
        model.put("classes", classes);
        model.put("packageName", packageName);
        FreemarkerUtils.process("templates/src/main/resources/spring/applicationContext-hibernate.xml",
            model,
            "target/" + projectName + "/src/main/resources/spring/",
            "applicationContext-hibernate.xml");
        FreemarkerUtils.process("templates/src/main/resources/spring/db/hibernate-mock.xml",
            model,
            "target/" + projectName + "/src/main/resources/spring/db/",
            "hibernate-mock.xml");
        // hibernate3:hbm2ddl
        FreemarkerUtils.process("templates/src/hibernate.cfg.xml",
            model,
            "target/" + projectName + "/src/",
            "hibernate.cfg.xml");
    }

    public static void genI18N() throws Exception {
        List<Bean> beanList = ExcelUtils.read(inputFileName);

        Map model = new HashMap();
        model.put("beanList", beanList);
        model.put("packageName", packageName);

        FreemarkerUtils.process("templates/src/main/native2ascii/validation.properties",
            model,
            "target/" + projectName + "/src/main/native2ascii/",
            "validation.properties",
            "GB2312",
			Locale.US);
        FreemarkerUtils.process("templates/src/main/native2ascii/validation_zh_CN.properties",
            model,
            "target/" + projectName + "/src/main/native2ascii/",
            "validation_zh_CN.properties",
            "GB2312");
    }

    public static void genStrutsMenu() throws Exception {
        List<Bean> beanList = ExcelUtils.read(inputFileName);

        Map model = new HashMap();
        model.put("beanList", beanList);
        model.put("projectName", projectName);

        FreemarkerUtils.process("templates/src/main/webapp/WEB-INF/menu-config.xml",
            model,
            "target/" + projectName + "/src/main/webapp/WEB-INF/",
            "menu-config.xml",
            "UTF-8");
        FreemarkerUtils.process("templates/src/main/webapp/freemarker/admin/menu.ftl",
            model,
            "target/" + projectName + "/src/main/webapp/freemarker/admin/",
            "menu.ftl",
            "UTF-8");
    }

    public static void genDbUnitExcel() throws Exception {
        DbUnitUtils.genDbUnitExcel("target/" + projectName + "/src/test/resources/xls/");
    }

    /** * main. */
    public static void main(String[] args) throws Exception {
        init(args);

        System.out.println("start...");

        genHibernate();
        genI18N();
        genStrutsMenu();
        genDbUnitExcel();

        System.out.println("end...");
    }

}
