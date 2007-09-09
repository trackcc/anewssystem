package anni.asecurity.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import anni.asecurity.domain.Dept;
import anni.asecurity.domain.Role;
import anni.asecurity.domain.User;

import anni.asecurity.manager.DeptManager;
import anni.asecurity.manager.RoleManager;
import anni.asecurity.manager.UserManager;

import anni.core.web.prototype.BaseLongController;
import anni.core.web.prototype.StreamView;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.acegisecurity.providers.encoding.PasswordEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.util.WebUtils;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class UserController extends BaseLongController<User, UserManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(UserController.class);

    /**
     * RoleManager.
     */
    private RoleManager roleManager = null;

    /** * deptManager. */
    private DeptManager deptManager = null;

    /** * constructor. */
    public UserController() {
        setEditView("/asecurity/user/editUser");
        setListView("/asecurity/user/listUser");
    }

    /**
     * @param roleManager Role Manager.
     */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /** * @param deptManager DeptManager. */
    public void setDeptManager(DeptManager deptManager) {
        this.deptManager = deptManager;
    }

    /**
     * 向模型中设置关联数据.
     *
     * @param model ModelAndView中的数据模型
     */
    @Override
    protected void referenceData(Map model) {
        model.put("statusEnum", User.STATUS_ENUM);
    }

    /**
     * 显示用户列表.
     *
     * @throws Exception 异常
     */
    @Override
    public void list() throws Exception {
        super.list();
        mv.addObject("statusEnum", User.STATUS_ENUM);
    }

    /**
     * 显示用户对应的角色列表.
     *
     * @throws Exception 异常
     */
    public void selectRoles() throws Exception {
        // mv.setViewName("/admin/selectRoles");
        Long userId = getLongParam("userId", 0L);

        if (userId != 0) {
            WebUtils.setSessionAttribute(request, "userId", userId);
        }

        Long id = (Long) WebUtils.getRequiredSessionAttribute(request,
                "userId");
        List<Role> roles = roleManager.getAll();

        User user = getEntityDao().get(id);

        for (Role role : roles) {
            if (user.getRoles().contains(role)) {
                role.setAuthorized(true);
            }
        }

        mv.addObject("user", user);
        mv.addObject("roles", roles);
    }

    /**
     * 授权与撤消授权.
     *
     * @throws Exception 异常
     */
    public void authRoles() throws Exception {
        mv.setViewName("forward:/user/selectRoles.htm");

        boolean auth = getBooleanParam("auth", false);

        // String[] itemlist = StringUtils.split(getStrParam("itemlist", ""),
        //         ",");
        String[] itemlist = getStrParams("itemlist");

        Long userId = (Long) WebUtils.getRequiredSessionAttribute(request,
                "userId");
        User user = getEntityDao().get(userId);

        if (user != null) {
            if (auth) {
                for (String id : itemlist) {
                    Role role = roleManager.get(Long.valueOf(id));

                    if (!user.getRoles().contains(role)) {
                        user.getRoles().add(role);
                    }
                }
            } else {
                for (String id : itemlist) {
                    Role role = roleManager.get(Long.valueOf(id));

                    if (user.getRoles().contains(role)) {
                        user.getRoles().remove(role);
                    }
                }
            }

            getEntityDao().save(user);
        }
    }

    /**
     * 对密码进行操作.
     *
     * @param request 请求
     * @param command 需要绑定的command
     * @param binder 绑定工具
     * @throws Exception 异常
     */
    @Override
    protected void preBind(HttpServletRequest request, Object command,
        ServletRequestDataBinder binder) throws Exception {
        Long id = getLongParam("id", -1L);
        binder.setDisallowedFields(new String[] {"password"});

        User user = (User) command;

        // 新增
        if ((id != -1L) && (getStrParam("pswd", null) != null)) {
            String pswd = getStrParam("pswd", "");
            String repeatpswd = getStrParam("repeatpswd", "");

            if (!"".equals(pswd)) {
                if (!pswd.equals(repeatpswd)) {
                    binder.getBindingResult()
                          .rejectValue("password", "两次输入的密码不一致",
                        new Object[0], "");
                } else {
                    if (StringUtils.isNotEmpty(pswd)) {
                        PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
                        user.setPassword(passwordEncoder.encodePassword(
                                pswd, null));
                    }
                }
            }
        } else {
            //修改
            String password = getStrParam("password", null);

            if (StringUtils.isNotEmpty(password)) {
                PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
                user.setPassword(passwordEncoder.encodePassword(password,
                        null));
            }
        }

        long deptId = getLongParam("deptId", -1L);
        Dept dept = deptManager.get(deptId);
        user.setDept(dept);
        logger.info(params());
    }

    /** * manage. */
    public void manage() {
        mv.setViewName("asecurity/user/manage");
    }

    /** * index. */
    public void index() {
        mv.setViewName("asecurity/user/index");
    }

    /**
     * onInsert.
     *
     * @throws Exception 写入response可能出现异常
     */
    @Override
    public void onInsert() throws Exception {
        logger.info(params());
        response.getWriter().print("{success:true,info:\"success\"}");
        mv.setView(new StreamView("application/json"));
    }

    /**
     * onUpdate.
     *
     * @throws Exception 写入response可能出现异常
     */
    @Override
    public void onUpdate() throws Exception {
        logger.info(params());
        response.getWriter().print("{success:true,info:\"success\"}");
        mv.setView(new StreamView("application/json"));
    }
}
