package anni.anews.web;

import anni.anews.domain.Comment;

import anni.anews.manager.CommentManager;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月08日 下午 18时10分12秒781
 */
public class CommentController extends BaseLongController<Comment, CommentManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(CommentController.class);
}
