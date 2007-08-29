package anni.anews.web;

import anni.anews.manager.NewsCategoryManager;

import anni.core.web.prototype.ExtendController;


/**
 * 管理模板的控制器.
 *
 * @author Lingo
 * @since 2007-08-29
 */
public class TemplateController extends ExtendController {
    /** * newsCategoryManager. */
    private NewsCategoryManager newsCategoryManager = null;

    /** * @param newsCategoryManager NewsCategoryManager. */
    public void setNewsCategoryManager(
        NewsCategoryManager newsCategoryManager) {
        this.newsCategoryManager = newsCategoryManager;
    }

    /**
     * 实现模板Drag and Drop.
     */
    public void dd() {
        mv.addObject("newsCategoryList", newsCategoryManager.loadTops());
        mv.setViewName("/anews/template/dd");
    }
}
