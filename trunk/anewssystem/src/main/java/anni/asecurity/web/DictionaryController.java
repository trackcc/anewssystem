package anni.asecurity.web;

import anni.asecurity.domain.Dictionary;

import anni.asecurity.manager.DictionaryManager;

import anni.core.tree.LongTreeController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class DictionaryController extends LongTreeController<Dictionary, DictionaryManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(DictionaryController.class);

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("asecurity/dictionary/index");
    }

    /**
     * all.
     *
     * @return 不需要转化成json的属性
     */
    @Override
    public String[] getExcludesForAll() {
        return new String[] {"class", "root", "parent"};
    }

    /**
     * children.
     *
     * @return 不需要转化成json的属性
     */
    @Override
    public String[] getExcludesForChildren() {
        return new String[] {"class", "root", "parent", "children"};
    }
}
