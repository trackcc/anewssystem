
package anni.tools;

import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.*;
import freemarker.template.*;
import jxl.*;

@SuppressWarnings("unchecked")
public class GenFreemarker {
    Template index = null;
    Template edit = null;
    Template list = null;
    String genDir;
    String excelFile;
    String projectName;
    String packageName;

    /**
     * args[0] genDir "./target/gen-freemarker/"
     * args[1] packageName "com.cysoft.sample.domain"
     * args[2] excelFile "res/sample.xls"
	 * args[3] projectName "ablog"
     */
    void init(String[] args) {
        genDir = args[0];
        packageName = args[1];
        excelFile = args[2];
        projectName = args[3];
    }

    public GenFreemarker() throws Exception {
    }

    void generateAll(String[] args) throws Exception {
        init(args);

        String[] classes = ExcelUtils.getClassNames(excelFile);

        generateIndex(classes);

        for (int i = 0; i < classes.length; i++) {
            String className = classes[i];
            Class clz = Class.forName(packageName + "." + className);
            Field[] fields = clz.getDeclaredFields();
            generateEdit(className, fields);
            generateList(className, fields);
        }
        List<Bean> beanList = ExcelUtils.read(excelFile);
        generateValidation(beanList);
    }

    void generateIndex(String[] classes) throws Exception {
        Map model = new HashMap();
        model.put("classes", classes);

        FreemarkerUtils.process("templates/src/main/webapp/index.jsp",
            model,
            "target/" + projectName + "/src/main/webapp/",
            "index.jsp");
    }

    void generateEdit(String className, Field[] fields) throws Exception {
        Map model = new HashMap();
        model.put("clz", className);
        model.put("fields", fields);

        FreemarkerUtils.process("templates/src/main/webapp/freemarker/edit.ftl",
            model,
            "target/" + projectName + "/src/main/webapp/freemarker/" + projectName + "/" + className.toLowerCase(),
            "edit" + className + ".ftl");
    }

    void generateList(String className, Field[] fields) throws Exception {
        Map model = new HashMap();
        model.put("clz", className);
        model.put("fields", fields);

        FreemarkerUtils.process("templates/src/main/webapp/freemarker/list.ftl",
            model,
            "target/" + projectName + "/src/main/webapp/freemarker/" + projectName + "/" + className.toLowerCase(),
            "list" + className + ".ftl");
    }

    void generateValidation(List<Bean> beanList) throws Exception {
        Map model = new HashMap();
        model.put("beanList", beanList);

        FreemarkerUtils.process("templates/src/main/resources/validation/validation.xml",
            model,
            "target/" + projectName + "/src/main/resources/validation/",
            "validation.xml");
    }

    public static void main(String[] args) throws Exception {
        GenFreemarker gen = new GenFreemarker();
        gen.generateAll(args);
    }
}
