package anni.anews.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import anni.anews.domain.NewsCategory;
import anni.anews.domain.NewsConfig;

import anni.anews.manager.NewsCategoryManager;
import anni.anews.manager.NewsConfigManager;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsConfigController extends BaseLongController<NewsConfig, NewsConfigManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsConfigController.class);

    /** * newsCategoryManager. */
    private NewsCategoryManager newsCategoryManager = null;

    /** * constructor. */
    public NewsConfigController() {
        setEditView("/anews/newsconfig/manage");
        // setEditView("/anews/newsconfig/editNewsConfig");
        setListView("/anews/newsconfig/listNewsConfig");
        setSuccessView("redirect:/newsconfig/manage.htm");
    }

    /** * @param newsCategoryManager NewsCategoryManager. */
    public void setNewsCategoryManager(
        NewsCategoryManager newsCategoryManager) {
        this.newsCategoryManager = newsCategoryManager;
    }

    /**
     * @param requestIn HttpServletRequest.
     * @param command Object
     * @param binder ServletRequestDataBinder
     *
     * @throws Exception 异常
     */
    @Override
    protected void preBind(HttpServletRequest requestIn, Object command,
        ServletRequestDataBinder binder) throws Exception {
        //
        NewsConfig config = (NewsConfig) command;

        config.setCommentNeedAudit(0);
        config.setNewsNeedAudit(0);
        config.setCouldComment(0);
        config.setCategoryStrategy(NewsCategory.STRATEGY_BIT_CODE);
        config.setTemplateName(NewsConfig.DEFAULT_TEMPLATE_NAME);
    }

    /** * manage. */
    public void manage() {
        logger.info("start");

        NewsConfig config = getEntityDao().getDefaultConfig();
        mv.addObject("config", config);
        mv.setViewName("/anews/newsconfig/manage");
    }

    /**
     * 保证修改分类策略后，更新所有分类的编码.
     */
    @Override
    protected void onUpdate() {
        List<NewsCategory> newsCategoryList = newsCategoryManager.getAll();

        for (NewsCategory newsCategory : newsCategoryList) {
            newsCategoryManager.save(newsCategory);
        }
    }
}