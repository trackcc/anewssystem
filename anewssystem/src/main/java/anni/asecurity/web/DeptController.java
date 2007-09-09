package anni.asecurity.web;

import anni.asecurity.domain.Dept;

import anni.asecurity.manager.DeptManager;

import anni.asecurity.web.support.extjs.TreeController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class DeptController extends TreeController<Dept, DeptManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(DeptController.class);

    /** * constructor. */
    public DeptController() {
        setEditView("/asecurity/dept/editDept");
        setListView("/asecurity/dept/listDept");
    }

    /** * index. */
    public void index() {
        mv.setViewName("asecurity/dept/index");
    }

    /**
     * getExcludes().
     *
     * @return 不需要转化成json的属性
     */
    @Override
    public String[] getExcludes() {
        return new String[] {"users", "children", "parent"};
    }
}
