package anni.anews.web;

import anni.anews.domain.NewsCategory;

import anni.anews.manager.NewsCategoryManager;

import anni.core.tree.LongTreeController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsCategoryController extends LongTreeController<NewsCategory, NewsCategoryManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsCategoryController.class);

    /** * constructor. */
    public NewsCategoryController() {
        //setEditView("/anews/newscategory/editNewsCategory");
        //setListView("/anews/newscategory/listNewsCategory");
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("/anews/newscategory/index");
    }

    /**
     * @return getExcludesForAll.
     */
    @Override
    public String[] getExcludesForAll() {
        return new String[] {
            "parent", "theSort", "status", "newses", "bitCode", "charCode"
        };
    }

    /**
     * @return getExcludesForChildren.
     */
    @Override
    public String[] getExcludesForChildren() {
        return new String[] {
            "parent", "theSort", "status", "newses", "bitCode", "charCode",
            "children"
        };
    }
}
