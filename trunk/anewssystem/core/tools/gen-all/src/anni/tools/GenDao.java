
package anni.tools;

import java.io.*;
import java.util.*;
import jxl.*;
import java.text.*;

@SuppressWarnings("unchecked")
public class GenDao {
    static String rootPackage;
    static String daoPackage;
    static String hibernatePackage;
    static String domainPackage;
    static String webPackage;
    static String genDir;
    static String excelFile;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 a HH时mm分ss秒S");
    static Date now = new Date();

    /**
     * args[0] genDir "./target/gen/"
     * args[1] rootPackage "com.cysoft.sample"
     * args[2] excelFile "res/sample.xls"
     */
    public static void main(String[] args) throws Exception {
        genDir = args[0];
        rootPackage = args[1];
        excelFile = args[2];

        Workbook workbook = Workbook.getWorkbook(new File(excelFile));
        Sheet[] sheets = workbook.getSheets();
        String[] classes = new String[sheets.length];
        for (int i = 0; i < sheets.length; i++) {
            classes[i] = getClassName(sheets[i].getName());
        }

        daoPackage = rootPackage + ".dao";
        hibernatePackage = daoPackage + ".hibernate";
        domainPackage = rootPackage + ".domain";
        webPackage = rootPackage + ".web";

        File rootDir = new File(genDir + "/" + rootPackage.replaceAll("\\.", "/"));
        File daoDir = new File(rootDir, "dao");
        File hibernateDir = new File(daoDir, "hibernate");
        hibernateDir.mkdirs();

        File domainDir = new File(rootDir, "domain");
        domainDir.mkdirs();

        File webDir = new File(rootDir, "web");
        webDir.mkdirs();

        for (String pojo : classes) {
            makeDao(pojo, daoDir);
            makeHibernate(pojo, hibernateDir);
            makeWeb(pojo, webDir);
        }

        copyPackageHtml();
        makeXml(classes);
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

    static void makeDao(String pojo, File dir) throws Exception {
        File file = new File(dir, pojo + "Dao.java");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        out.println("package " + daoPackage + ";");
        out.println("import " + domainPackage + "." + pojo + ";");
        out.println("import anni.core.dao.EntityDao;");
        out.println("/**");
        out.println(" * @author Lingo.");
        out.println(" * @since " + sdf.format(now));
        out.println(" */");
        out.println("public interface " + pojo + "Dao extends EntityDao<" + pojo + ">{}");
        out.flush();
        out.close();
    }

    static void makeHibernate(String pojo, File dir) throws Exception {
        File file =
            new File(dir, pojo + "DaoImpl.java");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        out.println("package " + hibernatePackage + ";");
        out.println("import " + daoPackage + "." + pojo + "Dao;");
        out.println("import " + domainPackage + "." + pojo + ";");
        out.println("import anni.core.dao.HibernateEntityDao;");
        out.println("/**");
        out.println(" * @author Lingo.");
        out.println(" * @since " + sdf.format(now));
        out.println(" */");
        out.println("public class " + pojo + "DaoImpl extends HibernateEntityDao<" + pojo
            + "> implements " + pojo + "Dao{}");
        out.flush();
        out.close();
    }

    static void makeWeb(String pojo, File dir) throws Exception {
        File file = new File(dir, pojo + "Controller.java");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        out.println("package " + webPackage + ";");
        out.println("import " + daoPackage + "." + pojo + "Dao;");
        out.println("import " + domainPackage + "." + pojo + ";");
        out.println("import anni.core.web.prototype.BaseController;");
        out.println("/**");
        out.println(" * @author Lingo.");
        out.println(" * @since " + sdf.format(now));
        out.println(" */");
        out.println("public class " + pojo + "Controller extends BaseController<" + pojo
            + ", " + pojo + "Dao>{}");
        out.flush();
        out.close();
    }

    static void copyPackageHtml() throws Exception {
        copyTo("res/dao/dao/package.html", daoPackage.replaceAll("\\.", "/") + "/package.html");
        copyTo("res/dao/dao/hibernate/package.html", hibernatePackage.replaceAll("\\.", "/") + "/package.html");
        copyTo("res/dao/domain/package.html", domainPackage.replaceAll("\\.", "/") + "/package.html");
        copyTo("res/dao/web/package.html", webPackage.replaceAll("\\.", "/") + "/package.html");
    }

    static void copyTo(String src, String dest) throws Exception {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(genDir + "/" + dest);
        byte[] bb = new byte[1024];
        int len = 0;
        while ((len = fis.read(bb, 0, 1024)) != -1) {
            fos.write(bb, 0, len);
        }
        fis.close();
        fos.flush();
        fos.close();
    }

    static void makeXml(String[] classes) throws Exception {
        PrintWriter out = new PrintWriter(new FileWriter(genDir + "/applicationContext-manager.xml"));
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN 2.0//EN\" \"http://www.springframework.org/dtd/spring-beans-2.0.dtd\">");
        out.println("");
        out.println("<beans default-lazy-init=\"true\" default-autowire=\"byName\">");

        for (String clz : classes) {
            String cap = clz.substring(0, 1).toLowerCase() + clz.substring(1);
            out.println("    <bean id=\"" + cap + "Dao\" class=\"" + hibernatePackage + "." + clz + "DaoImpl\"/>");
        }

        out.println("</beans>");
        out.flush();
        out.close();

        out = new PrintWriter(new FileWriter(genDir + "/dispatcher-servlet.xml"));
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN 2.0//EN\" \"http://www.springframework.org/dtd/spring-beans-2.0.dtd\">");
        out.println("");
        out.println("<beans default-lazy-init=\"false\" default-autowire=\"byName\">");
        out.println("    <bean id=\"baseController\" abstract=\"true\">");
        out.println("        <property name=\"validators\" ref=\"beanValidator\"/>");
        out.println("    </bean>");
        out.println("");

        for (String clz : classes) {
            String cap = clz.substring(0, 1).toLowerCase() + clz.substring(1);
            out.println("    <bean class=\"" + webPackage + "." + clz + "Controller\" parent=\"baseController\" scope=\"request\">");
            out.println("        <property name=\"entityDao\" ref=\"" + cap + "Dao\"/>");
            out.println("    </bean>");
        }

        out.println("");
        out.println("");
        out.println("    <bean class=\"anni.core.web.prototype.ControllerClassNameHandlerMapping\"/>");
        out.println("");
        out.println("    <bean id=\"viewNameTranslator\" class=\"org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator\"/>");
        out.println("");
        out.println("    <bean id=\"freemarkerConfig\" class=\"anni.core.web.freemarker.CustomerFreeMarkerConfigurer\">");
        out.println("        <property name=\"templateLoaderPath\" value=\"/freemarker/\"/>");
        out.println("        <property name=\"configLocation\" value=\"/WEB-INF/freemarker.properties\"/>");
        out.println("    </bean>");
        out.println("");
        out.println("    <bean id=\"viewResolver\" class=\"org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver\">");
        out.println("        <property name=\"cache\" value=\"false\"/>");
        out.println("        <property name=\"prefix\" value=\"/\"/>");
        out.println("        <property name=\"suffix\" value=\".ftl\"/>");
        out.println("        <property name=\"exposeSpringMacroHelpers\" value=\"true\"/>");
        out.println("        <property name=\"exposeRequestAttributes\" value=\"true\"/>");
        out.println("        <property name=\"contentType\" value=\"text/html; charset=UTF-8\"/>");
        out.println("    </bean>");
        out.println("");
        out.println("    <bean id=\"multipartResolver\" class=\"org.springframework.web.multipart.commons.CommonsMultipartResolver\">");
        out.println("        <property name=\"maxUploadSize\" value=\"104857600\"/>");
        out.println("        <property name=\"maxInMemorySize\" value=\"4096\"/>");
        out.println("    </bean>");
        out.println("</beans>");
        out.flush();
        out.close();
    }

}

