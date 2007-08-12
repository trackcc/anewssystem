package anni.anews.web.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anni.anews.domain.News;

import anni.anews.manager.CategoryManager;
import anni.anews.manager.NewsManager;

import freemarker.template.Template;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


public class FreemarkerGenerator {
    /** * logger. */
    private static Log logger = LogFactory.getLog(FreemarkerGenerator.class);
    private CategoryManager categoryManager;
    private NewsManager newsManager;
    private FreeMarkerConfigurer freemarkerConfig = null;

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    public void setNewsManager(NewsManager newsManager) {
        this.newsManager = newsManager;
    }

    public void setFreemarkerConfig(FreeMarkerConfigurer freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public void genNews(News news, int page, int pageSize, String root,
        String ctx) {
        logger.info("start generate...");

        Date date = news.getUpdateDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String fileName = root + "/html/" + news.getCategory().getId()
            + "/" + sdf.format(date) + "/" + news.getId() + ".html";
        Map model = new HashMap();
        model.put("news", news);
        model.put("ctx", ctx);

        if (page == 0) {
            // 无分页
            template2File("newstemplates/news3.ftl", fileName, model);
        } else {
            List<String> pages = new ArrayList<String>();
            String content = news.getContent();
            logger.info("page : " + page);
            logger.info(content);

            if (page == 1) {
                String ff = "<div style=\"page-break-after: always;\"><span style=\"display: none;\">&nbsp;</span></div>";
                String ie = "<div style=\"PAGE-BREAK-AFTER: always\"><span style=\"DISPLAY: none\">&nbsp;</span></div>";

                if (content.indexOf(ff) != -1) {
                    String[] tmp = content.split(ff);

                    for (String str : tmp) {
                        pages.add(str);
                    }
                } else {
                    String[] tmp = content.split(ie);

                    for (String str : tmp) {
                        pages.add(str);
                    }
                }
            } else {
                for (int i = 0; i <= (content.length() / pageSize); i++) {
                    if ((i * pageSize) > content.length()) {
                        pages.add(content.substring(i * pageSize, pageSize));
                    } else {
                        pages.add(content.substring(i * pageSize,
                                content.length()));
                    }
                }
            }

            logger.info(pages);

            for (int i = 0; i < pages.size(); i++) {
                model.put("page", i + 1);
                model.put("total", pages.size());
                model.put("pageContent", pages.get(i));

                if (i != 0) {
                    fileName = root + "/html/"
                        + news.getCategory().getId() + "/"
                        + sdf.format(date) + "/" + news.getId() + "_"
                        + (i + 1) + ".html";
                }

                template2File("newstemplates/news3.ftl", fileName, model);
            }
        }

        logger.info("end generate...");
    }

    private void template2File(String templateName, String fileName,
        Map map) {
        try {
            Template t = freemarkerConfig.getConfiguration()
                                         .getTemplate(templateName);

            String result = FreeMarkerTemplateUtils
                .processTemplateIntoString(t, map);
            File file = new File(fileName);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        new FileOutputStream(fileName), "UTF-8"));
            out.println(result);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
