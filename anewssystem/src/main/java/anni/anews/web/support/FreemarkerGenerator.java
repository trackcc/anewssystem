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
*/
import anni.anews.domain.NewsConfig;
import anni.anews.domain.NewsTag;

import anni.anews.manager.NewsConfigManager;
import anni.anews.manager.NewsManager;
import anni.anews.manager.NewsTagManager;

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

    /** * newsManager. */
    private NewsManager newsManager = null;

    /** * newsTagManager. */
    private NewsTagManager newsTagManager = null;

    /** * newsConfigManager. */
    private NewsConfigManager newsConfigManager = null;

    /** * @param newsManager NewsManager. */
    public void setNewsManager(NewsManager newsManager) {
        this.newsManager = newsManager;
    }

    /** * @param newsTagManager NewsTagManager. */
    public void setNewsTagManager(NewsTagManager newsTagManager) {
        this.newsTagManager = newsTagManager;
    }

    /** * @param newsConfigManager NewsConfigManager. */
    public void setNewsConfigManager(NewsConfigManager newsConfigManager) {
        this.newsConfigManager = newsConfigManager;
    }

    /*
    *private NewsCategoryManager categoryManager;
    *private NewsManager newsManager;
    *public void setNewsCategoryManager(NewsCategoryManager categoryManager) {
    *    this.categoryManager = categoryManager;
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
     * @param templateName 模板名称
     */
    public void genNews(News news, int page, int pageSize, String root,
        String ctx, String templateName) {
        logger.info("start generate...");

        Date date = news.getUpdateDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        // root是用getRealPath("/")获得的根目录，将html保存在网站根目录下，可以通过tomcat直接访问
        String fileName = null;

        if (news.getNewsCategory() == null) {
            fileName = root + "/html/0/" + sdf.format(date) + "/"
                + news.getId() + ".html";
        } else {
            fileName = root + "/html/" + news.getNewsCategory().getId()
                + "/" + sdf.format(date) + "/" + news.getId() + ".html";
        }

        Map model = new HashMap();
        model.put("news", news);
        model.put("ctx", ctx);

        // 查找关键字相同的相关新闻
        String hql = "select distinct a "
            + "from News a join a.newsTags b "
            + "where b in (select d from News c join c.newsTags d where c.id=?) "
            + "and a.id<>? order by a.id";
        List<NewsTag> tagList = newsTagManager.createQuery(hql,
                news.getId(), news.getId()).setMaxResults(10).list();
        model.put("tagList", tagList);

        NewsConfig newsConfig = newsConfigManager.getDefaultConfig();

        if (page == 0) {
            // 无分页
            template2File("/anews/template/"
                + newsConfig.getTemplateName() + "/detail.ftl", fileName,
                model);
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

                template2File("/anews/template/"
                    + newsConfig.getTemplateName() + "/detail.ftl",
                    fileName, model);
            }
        }

        logger.info("end generate...");

        // 生成rss
        Map map = new HashMap();
        map.put("now", new Date());
        map.put("page",
            newsManager.pagedQuery("from News order by id desc", 1, 10));
        template2File("/anews/newstemplates/rss.ftl",
            root + "/html/rss.xml", map);
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
