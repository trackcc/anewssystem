package anni.anews.web;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import anni.anews.domain.News;
import anni.anews.domain.NewsCategory;
import anni.anews.domain.NewsConfig;
import anni.anews.domain.NewsTag;

import anni.anews.manager.NewsCategoryManager;
import anni.anews.manager.NewsConfigManager;
import anni.anews.manager.NewsManager;
import anni.anews.manager.NewsTagManager;

import anni.anews.web.support.FreemarkerGenerator;

import anni.core.dao.support.Page;

import anni.core.web.prototype.BaseLongController;
import anni.core.web.prototype.ExtremeTablePage;
import anni.core.web.prototype.SimpleDateEditor;

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
public class News2Controller extends BaseLongController<News, NewsManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(News2Controller.class);

    /** * newsCategoryManager. */
    private NewsCategoryManager newsCategoryManager = null;

    /** * newsTagManager. */
    private NewsTagManager newsTagManager = null;

    /** * newsConfigManager. */
    private NewsConfigManager newsConfigManager = null;

    /** * freemarkerGenerator. */
    private FreemarkerGenerator freemarkerGenerator = null;

    /** * constructor. */
    public News2Controller() {
        setEditView("/anews/news/editNews");
        setListView("/anews/news/listNews");
    }

    /** * @param newsCategoryManager NewsCategoryManager. */
    public void setNewsCategoryManager(
        NewsCategoryManager newsCategoryManager) {
        this.newsCategoryManager = newsCategoryManager;
    }

    /** * @param newsTagManager NewsTagManager. */
    public void setNewsTagManager(NewsTagManager newsTagManager) {
        this.newsTagManager = newsTagManager;
    }

    /** * @param newsConfigManager NewsConfigManager. */
    public void setNewsConfigManager(NewsConfigManager newsConfigManager) {
        this.newsConfigManager = newsConfigManager;
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
        model.put("categoryList", newsCategoryManager.loadTops());
        model.put("tagList", newsTagManager.getAll());
        model.put("config", newsConfigManager.get(1L));
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

        // 修改时，不会更新发布时间
        long updateDate;

        if (entity.getUpdateDate() == null) {
            updateDate = System.currentTimeMillis();
        } else {
            updateDate = entity.getUpdateDate().getTime();
        }

        entity.setUpdateDate(new SimpleDateEditor.ExtDate(updateDate));

        // 这里绑定新闻分类
        long categoryId = getLongParam("category_id", -1L);

        if (categoryId != -1L) {
            NewsCategory category = newsCategoryManager.get(categoryId);

            if (category == null) {
                logger.info("指定分类不存在" + categoryId);
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
            logger.info("请选择分类");
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
     *
     * @throws Exception 异常
     */
    @Override
    public void onInsert() throws Exception {
        postEdit();
    }

    /**
     * 修改.
     *
     * @throws Exception 异常
     */
    @Override
    public void onUpdate() throws Exception {
        postEdit();
    }

    /**
     * 插入或更新后的操作.
     */
    private void postEdit() {
        News news = (News) mv.getModel().get("news");

        // FIXME: 如果是添加新闻，这步是不需要的
        if (!news.getNewsTags().isEmpty()) {
            news.getNewsTags().removeAll(news.getNewsTags());
        }

        if (news.getUpdateDate() == null) {
            news.setUpdateDate(new Date());
        }

        // 如果不是链接新闻，则根据updateDate生成指向静态页面的路径
        if ((news.getLink() == null) || news.getLink().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            news.setLink(request.getContextPath() + "/html/"
                + news.getNewsCategory().getId() + "/"
                + sdf.format(news.getUpdateDate()) + "/" + news.getId()
                + ".html");
        }

        String tags = getStrParam("tags", "");
        logger.info(tags);

        if (!tags.equals("")) {
            String[] array = tags.split(",");

            for (String tagName : array) {
                NewsTag tag = newsTagManager.createOrGet(tagName);
                logger.info(tag);

                //if (!news.getTags().contains(tag)) {
                news.getNewsTags().add(tag);
                getEntityDao().save(news);

                //tag.getNewses().add(news);
                //}
            }
        }

        getEntityDao().save(news);

        generateHtml(news);

        mv.setViewName(successView + "?status=" + news.getStatus());
    }

    /**
     * 生成html静态页面.
     *
     * @param entity 新闻
     */
    private void generateHtml(News entity) {
        // 0 不分页 1 手工分页 2 自动分页
        int page = getIntParam("page", 0);
        int pageSize = getIntParam("pagesize", 1000);
        String root = request.getRealPath("/");
        String ctx = request.getContextPath();
        NewsConfig newsConfig = newsConfigManager.getDefaultConfig();
        freemarkerGenerator.genNews(entity, page, pageSize, root, ctx,
            newsConfig.getTemplateName());
    }

    /**
     * 按状态查询新闻记录.
     *
     * @throws Exception 异常
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

        String hql = "from News where status=?";

        // 添加可以根据分类搜索的功能
        // add by Lingo 2007-09-17 09:58
        // -1代表没有参数传递进来，0代表选择全部分类
        long categoryId = getLongParam("category_id", -1L);

        if (categoryId == -1L) {
            Long newsCategoryId = (Long) session.getAttribute(
                    "news_category_id");

            if (newsCategoryId == null) {
                categoryId = 0L;
            } else {
                categoryId = newsCategoryId;
            }
        }

        // 重新向session中设置news_category_id
        session.setAttribute("news_category_id", categoryId);

        // 如果不是选择全部分类，就需要按分类查询新闻
        if (categoryId != 0L) {
            hql += (" and newsCategory.id=" + categoryId);
            mv.addObject("categoryId", categoryId);
        }

        if (sortMap.isEmpty()) {
            hql += " order by id desc";
        } else {
            Map.Entry entry = (Map.Entry) sortMap.entrySet().iterator()
                                                 .next();
            hql += (" order by " + entry.getKey() + " " + entry.getValue());
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
        String hql = "from News where name like ? or subtitle like ? or summary like ? or content like ?";
        Page page = getEntityDao()
                        .pagedQuery(hql, pageNo, 20, keywords, keywords,
                keywords, keywords);
        mv.addObject("page", page);
        logger.info(page.getResult());
        mv.setViewName("/anews/news/search");
    }

    // 前台显示模板
    /** * index. */
    /*
        public void index() {
            NewsConfig newsConfig = newsConfigManager.getDefaultConfig();
            String templateName = newsConfig.getTemplateName();
            mv.addObject("newsCategoryList",
                newsCategoryManager.getAll("theSort", true));
            mv.setViewName("/anews/template/" + templateName + "/index");
        }
    */

    /** * more. */
    public void more() {
        NewsConfig newsConfig = newsConfigManager.getDefaultConfig();
        String templateName = newsConfig.getTemplateName();
        long id = getLongParam("id", -1L);
        mv.addObject("newsCategory", newsCategoryManager.get(id));
        mv.setViewName("/anews/template/" + templateName + "/more");
    }

    /** * detail. */
    public void detail() {
        NewsConfig newsConfig = newsConfigManager.getDefaultConfig();
        String templateName = newsConfig.getTemplateName();
        long id = getLongParam("id", -1L);
        mv.addObject("news", getEntityDao().get(id));
        mv.setViewName("/anews/template/" + templateName + "/detail");
    }

    /**
     * index.
     */
    public void index() {
        logger.info("start");
        mv.setViewName("anews/news/index");
    }
}
