
package anni.tools;

import java.io.*;
import java.util.*;
import jxl.*;
import java.text.*;
import freemarker.template.*;

@SuppressWarnings("unchecked")
public class GenManager {
    static String rootPackage;
    static String daoPackage;
    static String hibernatePackage;
    static String domainPackage;
    static String webPackage;
    static String genDir;
    static String excelFile;
    static String projectName;

    static String managerPackage;
    static String controllerPackage;

    static Template manager;
    static Template controller;
    static Template managerXml;
    static Template servletXml;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 a HH时mm分ss秒S");
    static Date now = new Date();


    /**
     * args[0] genDir "./target/gen/"
     * args[1] rootPackage "com.cysoft.sample"
     * args[2] excelFile "res/sample.xls"
     */
    static void init(String[] args) {
        genDir = args[0];
        rootPackage = args[1];
        excelFile = args[2];
        projectName = args[3];

        daoPackage = rootPackage + ".dao";
        hibernatePackage = daoPackage + ".hibernate";
        domainPackage = rootPackage + ".domain";
        webPackage = rootPackage + ".web";

        managerPackage = rootPackage + ".manager";
        controllerPackage = rootPackage + ".web";
    }

    public static void main(String[] args) throws Exception {
        init(args);

        String[] classes = ExcelUtils.getClassNames(excelFile);

        for (String pojo : classes) {
            generateManager(pojo);
            generateController(pojo);
        }

        copyPackageHtml();
        generateManagerXml(classes);
        generateServletXml(classes);

        generateWebController();
    }

    static void copyPackageHtml() throws Exception {
        FileUtils.copy("templates/src/main/java/manager/package.html",
            "target/" + projectName + "/src/main/java/" + managerPackage.replaceAll("\\.", "/") + "/package.html");
        FileUtils.copy("templates/src/main/java/domain/package.html",
            "target/" + projectName + "/src/main/java/" + domainPackage.replaceAll("\\.", "/") + "/package.html");
        FileUtils.copy("templates/src/main/java/web/package.html",
            "target/" + projectName + "/src/main/java/" + webPackage.replaceAll("\\.", "/") + "/package.html");
    }

   static void generateManager(String className) throws Exception {
        Map model = new HashMap();
        model.put("clz", className);
        model.put("pkg", rootPackage);
        model.put("pojo", className);
        model.put("now", now);
        // manager
        FreemarkerUtils.process("templates/src/main/java/manager/manager.java",
            model,
            "target/" + projectName + "/src/main/java/" + managerPackage.replaceAll("\\.", "/"),
            className + "Manager.java");
        // test
        FreemarkerUtils.process("templates/src/test/java/manager/Test.java",
            model,
            "target/" + projectName + "/src/test/java/" + managerPackage.replaceAll("\\.", "/"),
            className + "ManagerTest.java");
    }

   static void generateController(String className) throws Exception {
        Map model = new HashMap();
        model.put("clz", className);
        model.put("pkg", rootPackage);
        model.put("pojo", className);
        model.put("now", now);
        model.put("projectName", projectName);
        // manager
        FreemarkerUtils.process("templates/src/main/java/web/controller.java",
            model,
            "target/" + projectName + "/src/main/java/" + controllerPackage.replaceAll("\\.", "/"),
            className + "Controller.java");
        // test
        FreemarkerUtils.process("templates/src/test/java/web/Test.java",
            model,
            "target/" + projectName + "/src/test/java/" + controllerPackage.replaceAll("\\.", "/"),
            className + "ControllerTest.java");
    }

    static void generateManagerXml(String[] classes) throws Exception {
        Map model = new HashMap();
        model.put("classes", classes);
        model.put("pkg", rootPackage);

        FreemarkerUtils.process("templates/src/main/resources/spring/applicationContext-manager.xml",
            model,
            "target/" + projectName + "/src/main/resources/spring/",
            "applicationContext-manager.xml");
    }

    static void generateServletXml(String[] classes) throws Exception {
        Map model = new HashMap();
        model.put("classes", classes);
        model.put("pkg", rootPackage);
        model.put("projectName", projectName);

        FreemarkerUtils.process("templates/src/main/resources/spring/dispatcher-servlet.xml",
            model,
            "target/" + projectName + "/src/main/resources/spring/",
            "dispatcher-servlet.xml");

        FreemarkerUtils.process("templates/src/main/resources/spring/controller/controller.xml",
            model,
            "target/" + projectName + "/src/main/resources/spring/controller/",
            projectName + "-controller.xml");
    }

    static void generateWebController() throws Exception {
        Map model = new HashMap();
        model.put("webPackage", webPackage);
        model.put("now", now);

        // controller
        FreemarkerUtils.process("templates/src/main/java/web/AdminController.java",
            model,
            "target/" + projectName + "/src/main/java/" + webPackage.replaceAll("\\.", "/"),
            "AdminController.java");
        FreemarkerUtils.process("templates/src/main/java/web/IndexController.java",
            model,
            "target/" + projectName + "/src/main/java/" + webPackage.replaceAll("\\.", "/"),
            "IndexController.java");
        // xml
        FreemarkerUtils.process("templates/src/main/resources/spring/controller/admin-controller.xml",
            model,
            "target/" + projectName + "/src/main/resources/spring/controller",
            "admin-controller.xml");

        // test
        FreemarkerUtils.process("templates/src/test/java/web/AdminControllerTest.java",
            model,
            "target/" + projectName + "/src/test/java/" + webPackage.replaceAll("\\.", "/"),
            "AdminControllerTest.java");
        FreemarkerUtils.process("templates/src/test/java/web/IndexControllerTest.java",
            model,
            "target/" + projectName + "/src/test/java/" + webPackage.replaceAll("\\.", "/"),
            "IndexControllerTest.java");
    }

}

