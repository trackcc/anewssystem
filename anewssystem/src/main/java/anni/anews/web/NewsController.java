package anni.anews.web;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import anni.anews.domain.News;
import anni.anews.domain.NewsCategory;
import anni.anews.domain.NewsTag;

import anni.anews.manager.NewsCategoryManager;
import anni.anews.manager.NewsConfigManager;
import anni.anews.manager.NewsManager;
import anni.anews.manager.NewsTagManager;

import anni.anews.web.support.FreemarkerGenerator;

import anni.core.dao.support.Page;

import anni.core.web.prototype.BaseLongController;
import anni.core.web.prototype.ExtremeTablePage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.extremecomponents.table.limit.Limit;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsController extends BaseLongController<News, NewsManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsController.class);

    /** * categoryManager. */
    private NewsCategoryManager categoryManager = null;

    /** * tagManager. */
    private NewsTagManager tagManager = null;

    /** * configManager. */
    private NewsConfigManager configManager = null;

    /** * freemarkerGenerator. */
    private FreemarkerGenerator freemarkerGenerator = null;

    /** * constructor. */
    public NewsController() {
        setEditView("/anews/news/editNews");
        setListView("/anews/news/listNews");
    }

    /** * @param categoryManager categoryManager. */
    public void setNewsCategoryManager(NewsCategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    /** * @param tagManager TagManager. */
    public void setNewsTagManager(NewsTagManager tagManager) {
        this.tagManager = tagManager;
    }

    /** * @param configManager configManager. */
    public void setNewsConfigManager(NewsConfigManager configManager) {
        this.configManager = configManager;
    }

    /** * @param freemarkerGenerator FreemarkerGenerator. */
    public void setFreemarkerGenerator(
        FreemarkerGenerator freemarkerGenerator) {
        this.freemarkerGenerator = freemarkerGenerator;
    }

    /**
     * 向模型中设置关联数据.
     *
     * @param model ModelAndView中的数据模型
     */
    @Override
    protected void referenceData(Map model) {
        model.put("categoryList", categoryManager.loadTops());
        model.put("tagList", tagManager.getAll());
        model.put("config", configManager.get(1L));
    }

    /**
     * 绑定选中的分类.
     *
     * @param request 请求
     * @param command 需要绑定的command
     * @param binder 绑定工具
     * @throws Exception 异常
     */
    @Override
    protected void preBind(HttpServletRequest request, Object command,
        ServletRequestDataBinder binder) throws Exception {
        binder.setDisallowedFields(new String[] {
                "category_id", "tags", "status", "quick"
            });

        News entity = (News) command;

        // 这里绑定新闻分类
        long categoryId = getLongParam("category_id", -1L);

        if (categoryId != -1L) {
            NewsCategory category = categoryManager.get(categoryId);

            if (category == null) {
                binder.getBindingResult()
                      .rejectValue("newsCategory", "指定分类不存在",
                    new Object[0], "指定分类不存在");
            } else {
                entity.setNewsCategory(category);
            }
        } else {
            binder.getBindingResult()
                  .rejectValue("newsCategory", "请选择分类", new Object[0],
                "请选择分类");
        }

        String enter = getStrParam("enter", "");

        if (enter.equals("存为草稿")) {
            entity.setStatus(News.STATUS_DRAFT);
        } else {
            // 是否立即发布
            int quick = getIntParam("quick", 0);

            if (quick == 0) {
                // 如果需要审核
                entity.setStatus(News.STATUS_WAIT);
            } else {
                // 直接发布状态
                entity.setStatus(News.STATUS_NORMAL);
            }
        }
    }

    /**
     * 添加.
     */
    @Override
    public void onInsert() throws Exception {
        News news = (News) mv.getModel().get("news");

        if (news.getUpdateDate() == null) {
            news.setUpdateDate(new Date());
        }

        if ((news.getLink() == null) || news.getLink().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            news.setLink(request.getContextPath() + "/html/"
                + news.getNewsCategory().getId() + "/"
                + sdf.format(news.getUpdateDate()) + "/" + news.getId()
                + ".html");
        }

        String tags = getStrParam("tags", "");
        logger.info(tags);

        // news.getNewsTags().removeAll(news.getNewsTags());
        if (!tags.equals("")) {
            String[] array = tags.split(",");

            for (String tagName : array) {
                NewsTag tag = tagManager.createOrGet(tagName);
                logger.info(tag);

                //if (!news.getTags().contains(tag)) {
                news.getNewsTags().add(tag);

                //tag.getNewses().add(news);
                //}
            }
        }

        getEntityDao().save(news);

        generateHtml(news);

        if (news.getStatus() == News.STATUS_NORMAL) {
            mv.setViewName(successView + "?status=" + News.STATUS_NORMAL);
        } else if (news.getStatus() == News.STATUS_WAIT) {
            mv.setViewName(successView + "?status=" + News.STATUS_WAIT);
        } else if (news.getStatus() == News.STATUS_DRAFT) {
            mv.setViewName(successView + "?status=" + News.STATUS_DRAFT);
        }
    }

    /**
     * 修改.
     */
    @Override
    public void onUpdate() throws Exception {
        News news = (News) mv.getModel().get("news");

        if ((news.getLink() == null) || news.getLink().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            news.setLink(request.getContextPath() + "/html/"
                + news.getNewsCategory().getId() + "/"
                + sdf.format(news.getUpdateDate()) + "/" + news.getId()
                + ".html");
        }

        String tags = getStrParam("tags", "");
        logger.info(tags);

        news.getNewsTags().removeAll(news.getNewsTags());

        if (!tags.equals("")) {
            String[] array = tags.split(",");

            for (String tagName : array) {
                NewsTag tag = tagManager.createOrGet(tagName);
                logger.info(tag);

                //if (!news.getTags().contains(tag)) {
                news.getNewsTags().add(tag);

                //tag.getNewses().add(news);
                //}
            }
        }

        getEntityDao().save(news);

        generateHtml(news);

        if (news.getStatus() == News.STATUS_NORMAL) {
            mv.setViewName(successView + "?status=" + News.STATUS_NORMAL);
        } else if (news.getStatus() == News.STATUS_WAIT) {
            mv.setViewName(successView + "?status=" + News.STATUS_WAIT);
        } else if (news.getStatus() == News.STATUS_DRAFT) {
            mv.setViewName(successView + "?status=" + News.STATUS_DRAFT);
        }
    }

    /**
     * 生成html静态页面.
     */
    private void generateHtml(News entity) {
        // 0 不分页 1 手工分页 2 自动分页
        int page = getIntParam("page", 0);
        int pageSize = getIntParam("pagesize", 1000);
        String root = request.getRealPath("/");
        String ctx = request.getContextPath();
        freemarkerGenerator.genNews(entity, page, pageSize, root, ctx);
    }

    /**
     * 按状态查询新闻记录.
     */
    @Override
    public void list() throws Exception {
        mv = new ModelAndView(listView);

        Limit limit = ExtremeTablePage.getLimit(request,
                Page.DEFAULT_PAGE_SIZE);
        int status = getIntParam("status", 0);
        int pageNo = limit.getPage();
        int pageSize = limit.getCurrentRowsDisplayed();
        Map<String, String> sortMap = ExtremeTablePage.getSort(limit);
        logger.info(sortMap);

        String hql;

        if (sortMap.isEmpty()) {
            hql = "from News where status=? order by id desc";
        } else {
            Map.Entry entry = (Map.Entry) sortMap.entrySet().iterator()
                                                 .next();
            hql = "from News where status=? order by " + entry.getKey()
                + " " + entry.getValue();
        }

        logger.info(hql);

        Page page = getEntityDao().pagedQuery(hql, pageNo, pageSize, status);

        mv.addObject("page", page.getResult());
        mv.addObject("totalRows",
            Integer.valueOf((int) page.getTotalCount()));
        referenceData(mv.getModel());
        onList();
    }

    /**
     * 修改新闻状态.
     */
    public void changeStatus() {
        logger.info("start");

        int status = getIntParam("status", -1);

        if ((status == -1) || (status > 6)) {
            mv.setViewName(successView);

            return;
        }

        Serializable[] ids = getPrimaryKeys();
        int success = 0;

        if (ids != null) {
            for (Serializable id : ids) {
                try {
                    News entity = getEntityDao().get(id);

                    if (entity == null) {
                        continue;
                    }

                    entity.setStatus(status);
                    getEntityDao().save(entity);
                    success++;
                } catch (DataIntegrityViolationException e) {
                    saveMessage(onRemoveSelectedFailure(id));
                }
            }

            saveMessage("成功处理" + success + "条纪录!");
        }

        mv.setViewName(successView + "?status=" + status);
    }

    /**
     * 模糊查询，限于name,subtitle,summary和content.
     */
    public void search() {
        logger.info("start");

        String keywords = getStrParam("keywords", "").trim();

        if (keywords.equals("")) {
            mv.setViewName("/anews/news/search");

            return;
        }

        keywords = "%" + keywords + "%";

        int pageNo = getIntParam("pageNo", 1);
        Page page = getEntityDao()
                        .pagedQuery("from News where name like ? or subtitle like ? or summary like ? or content like ?",
                pageNo, 20, keywords, keywords, keywords, keywords);
        mv.addObject("page", page);
        mv.setViewName("/anews/news/search");
    }
}
