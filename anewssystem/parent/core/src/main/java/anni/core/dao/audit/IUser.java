package anni.core.dao.audit;


/**
 * 统一的User接口.
 * 用于AuditableEntity记录修改人的,无论在项目中使用了什么样的user类,
 * 只要实现了此接口,就可以统一的方式获得操作员loginID
 *
 * @author calvin
 */
public interface IUser {
    public String getLoginid();
}
