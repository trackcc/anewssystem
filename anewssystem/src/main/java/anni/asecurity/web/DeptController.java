package anni.asecurity.web;

import anni.asecurity.domain.Dept;

import anni.asecurity.manager.DeptManager;

import anni.core.tree.LongTreeController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class DeptController extends LongTreeController<Dept, DeptManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(DeptController.class);

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("asecurity/dept/index");
    }

    /** * orgmap. */
    public void orgmap() {
        // 组织结构
        logger.info("start");
        mv.addObject("deptList", getEntityDao().loadTops());
        mv.setViewName("asecurity/dept/orgmap");
    }

    /**
     * all.
     *
     * @return 不需要转化成json的属性
     */
    @Override
    public String[] getExcludesForAll() {
        return new String[] {"class", "root", "parent", "users"};
    }

    /**
     * children.
     *
     * @return 不需要转化成json的属性
     */
    @Override
    public String[] getExcludesForChildren() {
        return new String[] {"class", "root", "parent", "users", "children"};
    }
}
