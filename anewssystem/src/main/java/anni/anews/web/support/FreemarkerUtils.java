package anni.anews.web.support;

import java.io.*;

import java.util.*;

import freemarker.template.*;


public class FreemarkerUtils {
    public static void process(String templatePath, Map model,
        String destDirPath, String destFileName) throws Exception {
        process(templatePath, model, destDirPath, destFileName, "UTF-8");
    }

    public static void process(String templatePath, Map model,
        String destDirPath, String destFileName, String encoding)
        throws Exception {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("."));
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        Template template = cfg.getTemplate(templatePath, encoding);

        File destDir = new File(destDirPath);

        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File destFile = new File(destDir, destFileName);
        Writer out = new OutputStreamWriter(new FileOutputStream(destFile),
                encoding);
        template.process(model, out);
        out.flush();
        out.close();
    }

    public static void process(String templatePath, Map model,
        String destDirPath, String destFileName, String encoding,
        Locale locale) throws Exception {
        Configuration cfg = new Configuration();
        cfg.setLocale(locale);
        cfg.setDirectoryForTemplateLoading(new File("."));
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        Template template = cfg.getTemplate(templatePath, encoding);

        File destDir = new File(destDirPath);

        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File destFile = new File(destDir, destFileName);
        Writer out = new OutputStreamWriter(new FileOutputStream(destFile),
                encoding);
        template.process(model, out);
        out.flush();
        out.close();
    }
}
