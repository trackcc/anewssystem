
package anni.tools;

import java.io.*;
import java.lang.reflect.*;
//import java.text.*;
import java.util.*;
import freemarker.template.*;
import jxl.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("unchecked")
public class GenDomain {
    String genDir;
    String packageName;
    String excelFile;
    String projectName;
    String prefix;
    Date now = new Date();

    DomainUtils domainUtils = new DomainUtils();

    /**
     * args[0] genDir "./target/gen-domain/"
     * args[1] packageName "anni.anews.domain"
     * args[2] excelFile "res/sample.xls"
     * args[3] projectName "anews"
     * args[4] prefix = "A_NEWS_"
     */
    void init(String[] args) {
        genDir = args[0];
        packageName = args[1];
        excelFile = args[2];
        projectName = args[3];
        prefix = args[4];
        domainUtils.prefix = prefix;
    }

    public GenDomain() throws Exception {
    }

    void generateAll(String[] args) throws Exception {
        init(args);

        String[] classes = ExcelUtils.getClassNames(excelFile);

        for (int i = 0; i < classes.length; i++) {
            String className = classes[i];
            Class clz = Class.forName(packageName + "." + className);
            Field[] fields = clz.getDeclaredFields();
            generateDomain(className, clz, fields);
            generateDomainTest(className, fields);
        }
    }


    void generateDomain(String className, Class pojo, Field[] fields) throws Exception {
        Map model = new HashMap();
        model.put("className", className);
        model.put("packageName", packageName);
        model.put("pojo", pojo);
        model.put("fields", fields);
        model.put("domainUtils", domainUtils);
        model.put("now", now);

        model.put("importList", getImportList(pojo));

        FreemarkerUtils.process("templates/src/main/java/domain/domain.java",
            model,
            "target/" + projectName + "/src/main/java/" + packageName.replaceAll("\\.", "/"),
            className + ".java");
    }

    void generateDomainTest(String className, Field[] fields) throws Exception {
        Map model = new HashMap();
        model.put("className", className);
        model.put("packageName", packageName);
        model.put("fields", fields);

        FreemarkerUtils.process("templates/src/test/java/domain/Test.java",
            model,
            "target/" + projectName + "/src/test/java/" + packageName.replaceAll("\\.", "/"),
            className + "Test.java");
    }

    // ====================================================

    Set<String> getImportList(Class clz) throws Exception {
        List<Annotation> annoList = getAllMethodAnnotations(clz);
        Set<String> importList = new TreeSet<String>();
        for (Annotation annotation : annoList) {
            Class annoClass = annotation.annotationType();
            if (annoClass == ManyToOne.class) {
                importList.add("javax.persistence.ManyToOne");
                importList.add("javax.persistence.FetchType");
                importList.add("javax.persistence.JoinColumn");
            } else if (annoClass == OneToMany.class) {
                importList.add("java.util.Set");
                importList.add("java.util.HashSet");
                importList.add("javax.persistence.OneToMany");
                importList.add("javax.persistence.JoinColumn");
                importList.add("javax.persistence.FetchType");
                importList.add("javax.persistence.CascadeType");
            } else if (annoClass == ManyToMany.class) {
                importList.add("java.util.Set");
                importList.add("java.util.HashSet");
                importList.add("javax.persistence.ManyToMany");
                importList.add("javax.persistence.JoinColumn");
                importList.add("javax.persistence.JoinTable");
                importList.add("javax.persistence.CascadeType");
                importList.add("javax.persistence.FetchType");
            } else if (annoClass == Column.class) {
                Column anno = (Column) annotation;
                if (anno.length() > 255) {
                    importList.add("javax.persistence.Lob");
                }
            }
        }
        for (Field field : clz.getDeclaredFields()) {
            if (field.getType() == Date.class) {
                importList.add("java.util.Date");
                importList.add("javax.persistence.Temporal");
                importList.add("javax.persistence.TemporalType");
            }
        }
        return importList;
    }
    List<Annotation> getAllMethodAnnotations(Class clz) throws Exception {
        List<Annotation> list = new ArrayList<Annotation>();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    list.add(annotation);
                }
            }
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        GenDomain gen = new GenDomain();
        gen.generateAll(args);
    }
}
