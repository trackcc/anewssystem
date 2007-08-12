package anni.anews.web;

import anni.anews.domain.Tag;

import anni.anews.manager.TagManager;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月08日 下午 18时10分12秒781
 */
public class TagController extends BaseLongController<Tag, TagManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(TagController.class);
}
