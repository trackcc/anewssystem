package anni.anews.web;

import anni.anews.domain.NewsTag;

import anni.anews.manager.NewsTagManager;

import anni.core.grid.LongGridController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsTagController extends LongGridController<NewsTag, NewsTagManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsTagController.class);

    /** * constructor. */
    public NewsTagController() {
        logger.info("start");

        //setEditView("/anews/newstag/editNewsTag");
        //setListView("/anews/newstag/listNewsTag");
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("anews/newstag/index");
    }

    /**
     * @return excludes.
     */
    @Override
    public String[] getExcludes() {
        return new String[] {"newses"};
    }
}
