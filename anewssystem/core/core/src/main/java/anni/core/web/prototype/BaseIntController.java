package anni.core.web.prototype;

import java.io.Serializable;

import anni.core.dao.EntityDao;


/**
 * 专门用于primary key为long情况的BaseController.
 *
 * @author Lingo
 * @since 2007-06-22
 */
public class BaseIntController<T, D extends EntityDao<T>>
    extends BaseController<T, D> {
    /**
     * @return 从request中获得pojo的id.
     */
    @Override
    protected Serializable getPrimaryKey() {
        return getIntParam("id", -1);
    }

    /**
     * @return 从request中获得pojo的id数组.
     */
    @Override
    protected Serializable[] getPrimaryKeys() {
        return getIntParams("itemlist");
    }
}
