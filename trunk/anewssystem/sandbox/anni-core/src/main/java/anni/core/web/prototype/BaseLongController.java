package anni.core.web.prototype;

import java.io.Serializable;


/**
 * 专门用于primary key为long情况的BaseController.
 *
 * @author Lingo
 * @param <T> 实体类的类型
 * @param <D> 实体类对应的dao类型
 */
public class BaseLongController<T, D extends EntityDao<T>>
    extends BaseController<T, D> {
    /**
     * @return 从request中获得pojo的id.
     */
    @Override
    protected Serializable getPrimaryKey() {
        return getLongParam("id", -1L);
    }

    /**
     * @return 从request中获得pojo的id数组.
     */
    @Override
    protected Serializable[] getPrimaryKeys() {
        return getLongParams("itemlist");
    }
}
