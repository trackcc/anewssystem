package anni.anews.web;

import anni.anews.domain.NewsConfig;

import anni.anews.manager.NewsCategoryManager;
import anni.anews.manager.NewsConfigManager;
import anni.anews.manager.NewsManager;

import anni.core.web.prototype.ExtendController;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class IndexController extends ExtendController {
    /** * newsConfigManager. */
    private NewsConfigManager newsConfigManager = null;

    /** * newsCategoryManager. */
    private NewsCategoryManager newsCategoryManager = null;

    /** * newsManager. */
    private NewsManager newsManager = null;

    /** * @param newsConfigManager NewsConfigManager. */
    public void setNewsConfigManager(NewsConfigManager newsConfigManager) {
        this.newsConfigManager = newsConfigManager;
    }

    /** * @param newsCategoryManager NewsCategoryManager. */
    public void setNewsCategoryManager(
        NewsCategoryManager newsCategoryManager) {
        this.newsCategoryManager = newsCategoryManager;
    }

    /** * @param newsManager NewsManager. */
    public void setNewsManager(NewsManager newsManager) {
        this.newsManager = newsManager;
    }

    // 前台显示模板
    /** * index. */
    public void index() {
        NewsConfig newsConfig = newsConfigManager
            .getDefaultConfig();
        String templateName = newsConfig.getTemplateName();
        mv.addObject("newsCategoryList",
            newsCategoryManager.getAll("theSort", true));
        mv.setViewName("/anews/template/" + templateName + "/index");
    }

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
        mv.addObject("news", newsManager.get(id));
        mv.setViewName("/anews/template/" + templateName + "/detail");
    }
}
