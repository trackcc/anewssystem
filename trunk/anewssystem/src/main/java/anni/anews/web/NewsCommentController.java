package anni.anews.web;

import anni.anews.domain.NewsComment;

import anni.anews.manager.NewsCommentManager;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsCommentController extends BaseLongController<NewsComment, NewsCommentManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsCommentController.class);

    /** * constructor. */
    public NewsCommentController() {
        setEditView("/anews/newscomment/editNewsComment");
        setListView("/anews/newscomment/listNewsComment");
        logger.info("start");
    }
}
