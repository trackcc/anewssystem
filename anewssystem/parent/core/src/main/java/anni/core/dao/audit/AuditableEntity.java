package anni.core.dao.audit;

import java.util.Date;


/**
 * 领域对象记录创建,修改时间及创建,修改人的接口.
 * 记录上述信息是领域对象比较通用的需求.
 * {@link org.springside.core.commons.BaseManageController#bindObject(javax.servlet.http.HttpServletRequest, Object)}会对实现了此接口的领域对象自动从session中取出session 作create/modify User
 *
 *   **
 *    * 为AuditableEntity接口重载了{@link BaseController#bindObject(javax.servlet.http.HttpServletRequest, Object)}的bindObject函数.
 *    * 如果对象实现了该接口:
 *    * 自动对createUser/modifyUser取session中的"user"属性.
 *    * 自动对createTime/modifyTime取当前时间.
 *    *
 *   protected BindingResult bindObject(HttpServletRequest request, Object command) throws Exception {
 *       BindingResult result = super.bindObject(request, command);
 *       if (command instanceof AuditableEntity) {
 *           AuditableEntity ve = (AuditableEntity) command;
 *           IUser user = (IUser) request.getSession().getAttribute(Constants.USER_IN_SESSION);
 *           if (ve.getCreateUser() == null)
 *               ve.setCreateUser(user);
 *           else
 *               ve.setModifyUser(user);
 *
 *           if (ve.getCreateTime() == null)
 *               ve.setCreateTime(new Date());
 *           else
 *               ve.setModifyTime(new Date());
 *       }
 *       return result;
 *   }
 * @author calvin
 * @see org.springside.core.commons.BaseManageController
 */
public interface AuditableEntity {
    public Date getCreateTime();

    public void setCreateTime(Date createTime);

    public Date getModifyTime();

    public void setModifyTime(Date modifyTime);

    public IUser getCreateUser();

    public void setCreateUser(IUser user);

    public IUser getModifyUser();

    public void setModifyUser(IUser user);
}
