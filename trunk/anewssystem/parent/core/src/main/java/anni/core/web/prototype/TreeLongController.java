package anni.core.web.prototype;

import java.io.Serializable;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import anni.core.dao.TreeEntityDao;

import anni.core.domain.tree.TreeEntityBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * 专门用于primary key为long的treeController.
 *
 * @author Lingo
 * @since 2007-06-22
 */
public class TreeLongController<T extends TreeEntityBean, D extends TreeEntityDao<T>>
    extends BaseController<T, D> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(BaseLongController.class);

    /**
     * 绑定选中的parent menu.
     *
     * @param request 请求
     * @param command 需要绑定的command
     * @param binder 绑定工具
     * @throws Exception 异常
     */
    @Override
    protected void preBind(HttpServletRequest request, Object command,
        ServletRequestDataBinder binder) throws Exception {
        binder.setDisallowedFields(new String[] {"parent_id"});

        T entity = (T) command;

        try {
            Long id = getLongParam("parent_id", -1L);

            if (id != -1L) {
                T parent = entityDao.get(id);

                // check dead lock -- 不允许将自表外键关系设置成环状
                if (entity.checkDeadLock(parent)) {
                    binder.getBindingResult()
                          .rejectValue("parent", "不能把父节点设置为子节点的叶子",
                        new Object[0], "不能把父节点设置为子节点的叶子");
                } else {
                    entity.setParent(parent);
                }
            }
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
    }

    /**
     * 向模型中设置关联数据.
     *
     * @param model ModelAndView中的数据模型
     */
    @Override
    protected void referenceData(Map model) {
        model.put("parents", entityDao.getAll());
    }
}
