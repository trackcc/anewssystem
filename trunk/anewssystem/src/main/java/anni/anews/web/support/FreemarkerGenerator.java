package anni.anews.web.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anni.anews.domain.News;

/*
*import anni.anews.manager.NewsCategoryManager;
*import anni.anews.manager.NewsManager;
*/
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


/**
 * 生成静态页面的生成器.
 *
 * @author Lingo
 * @since 2007-08-18
 */
public class FreemarkerGenerator {
    /** * logger. */
    private static Log logger = LogFactory.getLog(FreemarkerGenerator.class);

    /** * fckeditor在firefox上的分页符. */
    public static final String FCK_PAGE_BREAK_FF = "<div style=\"page-break-after: always;\"><span style=\"display: none;\">&nbsp;</span></div>";

    /** * fckeditor在ie上的分页符. */
    public static final String FCK_PAGE_BREAK_IE = "<div style=\"PAGE-BREAK-AFTER: always\"><span style=\"DISPLAY: none\">&nbsp;</span></div>";

    /** * freemarker配置. */
    private FreeMarkerConfigurer freemarkerConfig = null;

    /*
    *private NewsCategoryManager categoryManager;
    *private NewsManager newsManager;
    *public void setNewsCategoryManager(NewsCategoryManager categoryManager) {
    *    this.categoryManager = categoryManager;
    *}
    *public void setNewsManager(NewsManager newsManager) {
    *    this.newsManager = newsManager;
    *}
    */

    /**
     * @param freemarkerConfig 配置
     */
    public void setFreemarkerConfig(FreeMarkerConfigurer freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    /**
     * 生成静态页面.
     *
     * @param news 新闻
     * @param page 开始页
     * @param pageSize 页面大小
     * @param root 根目录??????
     * @param ctx contextPath
     */
    public void genNews(News news, int page, int pageSize, String root,
        String ctx) {
        logger.info("start generate...");

        Date date = news.getUpdateDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        // FIXME: 忘了root是啥意思了
        String fileName = root + "/html/" + news.getNewsCategory().getId()
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
                if (content.indexOf(FCK_PAGE_BREAK_FF) != -1) {
                    String[] tmp = content.split(FCK_PAGE_BREAK_FF);

                    for (String str : tmp) {
                        pages.add(str);
                    }
                } else {
                    String[] tmp = content.split(FCK_PAGE_BREAK_IE);

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
                        + news.getNewsCategory().getId() + "/"
                        + sdf.format(date) + "/" + news.getId() + "_"
                        + (i + 1) + ".html";
                }

                template2File("/anews/newstemplates/news3.ftl", fileName,
                    model);
            }
        }

        logger.info("end generate...");
    }

    /**
     * 根据freemarker模板生成静态文件.
     *
     * @param templateName 模板名称
     * @param fileName 静态页面名称
     * @param map 模板使用的参数
     */
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
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TemplateException ex) {
            ex.printStackTrace();
        }
    }
}
