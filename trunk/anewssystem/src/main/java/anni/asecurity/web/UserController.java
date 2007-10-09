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

import anni.core.grid.LongGridController;

import anni.core.json.JsonUtils;

import net.sf.json.JSONObject;

import org.acegisecurity.providers.encoding.PasswordEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class UserController extends LongGridController<User, UserManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(UserController.class);

    /**
     * RoleManager.
     */
    private RoleManager roleManager = null;

    /** * deptManager. */
    private DeptManager deptManager = null;

    /** * passwordEncoder. */
    private PasswordEncoder passwordEncoder = null;

    /** * constructor. */
    public UserController() {
        //setEditView("/asecurity/user/editUser");
        //setListView("/asecurity/user/listUser");
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

    /** * @param passwordEncoder PasswordEncoder. */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 向模型中设置关联数据.
     *
     * @param model ModelAndView中的数据模型
     */
    protected void referenceData(Map model) {
        model.put("statusEnum", User.STATUS_ENUM);
    }

    /**
     * 显示用户列表.
     *
     * @throws Exception 异常
     */
    public void list() throws Exception {
        //super.list();
        mv.addObject("statusEnum", User.STATUS_ENUM);
    }

    /**
     * 显示用户对应的角色列表.
     *
     * @throws Exception 异常
     */
    public void getRoles() throws Exception {
        // mv.setViewName("/admin/selectRoles");
        Long userId = getLongParam("id", 0L);
        User user = getEntityDao().get(userId);
        List<Role> roles = roleManager.getAll();

        if (user != null) {
            for (Role role : roles) {
                if (user.getRoles().contains(role)) {
                    role.setAuthorized(true);
                }
            }
        }

        JsonUtils.write(roles, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 授权与撤消授权.
     *
     * @throws Exception 异常
     */
    public void auth() throws Exception {
        boolean isAuth = getBooleanParam("isAuth", false);
        String ids = getStrParam("ids", "");
        long userId = getLongParam("userId", 0L);
        User user = getEntityDao().get(userId);

        String[] arrays = ids.split(",");

        if (user != null) {
            if (isAuth) {
                for (String id : arrays) {
                    Role role = roleManager.get(Long.valueOf(id));

                    if (!user.getRoles().contains(role)) {
                        user.getRoles().add(role);
                    }
                }
            } else {
                for (String id : arrays) {
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
                        user.setPassword(passwordEncoder.encodePassword(
                                pswd, null));
                    }
                }
            }
        } else {
            //修改
            String password = getStrParam("password", null);

            if (StringUtils.isNotEmpty(password)) {
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
    public void onInsert() throws Exception {
        logger.info(params());
    }

    /**
     * onUpdate.
     *
     * @throws Exception 写入response可能出现异常
     */
    public void onUpdate() throws Exception {
        logger.info(params());
    }

    /**
     * 保存，新增或修改.
     *
     * @throws Exception 异常
     */
    @Override
    public void save() throws Exception {
        logger.info(params());

        User entity = bindObject();
        String data = getStrParam("data", "");

        JSONObject jsonObject = JSONObject.fromObject(data);

        // 部门
        try {
            long deptId = jsonObject.getLong("dept.name");
            Dept dept = deptManager.get(deptId);
            entity.setDept(dept);
        } catch (Exception ex) {
            logger.info(ex);
        }

        // 设置为可用
        entity.setStatus((byte) 1);

        String resultMessage = null;

        if (entity.getId() == null) {
            // id为null说明是新增
            List<User> userList = getEntityDao()
                                      .findBy("username",
                    entity.getUsername());

            if (userList.size() > 0) {
                resultMessage = "{success:false,info:'此帐号已被别人注册过了'}";
            } else {
                String password = jsonObject.getString("password");
                String confirmpassword = jsonObject.getString(
                        "confirmpassword");

                if ((password == null) || "".equals(password.trim())) {
                    resultMessage = "{success:false,info:'密码不能空'}";
                } else if (!password.equals(confirmpassword)) {
                    resultMessage = "{success:false,info:'两次输入的密码不同'}";
                } else {
                    entity.setPassword(passwordEncoder.encodePassword(
                            password.trim(), null));
                }
            }
        } else {
            // id不为null说明是更新
            String oldpassword2 = jsonObject.getString("oldpassword2");
            String password2 = jsonObject.getString("password2");
            String confirmpassword2 = jsonObject.getString(
                    "confirmpassword2");

            if ("".equals(oldpassword2) && "".equals(password2)
                    && "".equals(confirmpassword2)) {
                // 三个密码框都没有输入，说明不会修改密码
                logger.debug("不修改密码");
            } else {
                String oldpassword2AfterEncode = passwordEncoder
                    .encodePassword(oldpassword2, null);

                if (!oldpassword2AfterEncode.equals(entity.getPassword())) {
                    resultMessage = "{success.false,info:'旧密码输入错误'}";
                } else if ((password2 == null)
                        || "".equals(password2.trim())) {
                    resultMessage = "{success:false,info:'密码不能空'}";
                } else if (!password2.trim().equals(confirmpassword2.trim())) {
                    resultMessage = "{success:false,info:'两次输入的密码不同'}";
                } else {
                    entity.setPassword(passwordEncoder.encodePassword(
                            password2.trim(), null));
                }
            }
        }

        if (resultMessage == null) {
            getEntityDao().save(entity);
            resultMessage = "{success:true}";
        }

        response.getWriter().print(resultMessage);
    }

    /**
     * 开通用户.
     *
     * @throws Exception 异常
     */
    public void openUser() throws Exception {
        logger.info(params());

        String ids = getStrParam("ids", "");

        for (String str : ids.split(",")) {
            try {
                long id = Long.parseLong(str);
                User user = getEntityDao().get(id);
                user.setStatus((byte) 1);
                getEntityDao().save(user);
            } catch (NumberFormatException ex) {
                continue;
            }
        }

        response.getWriter().print("{success:true}");
    }

    /**
     * 关闭用户.
     *
     * @throws Exception 异常
     */
    public void closeUser() throws Exception {
        logger.info(params());

        String ids = getStrParam("ids", "");

        for (String str : ids.split(",")) {
            try {
                long id = Long.parseLong(str);
                User user = getEntityDao().get(id);
                user.setStatus((byte) 0);
                getEntityDao().save(user);
            } catch (NumberFormatException ex) {
                continue;
            }
        }

        response.getWriter().print("{success:true}");
    }

    /**
     * 检测帐号是否重复.
     *
     * @throws Exception 写入json可能出现异常
     */
    public void checkUsername() throws Exception {
        String username = getStrParam("username", "");
        List<User> list = getEntityDao().findBy("username", username);
        boolean isValid = (list.size() == 0);
        response.getWriter().print("{success:" + isValid + "}");
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {
            "roles", "users", "parent", "children",
            "hibernateLazyInitializer", "parentId", "theSort", "text",
            "allowChildren", "cls", "leaf", "root", "qtip", "allowDelete",
            "allowEdit", "draggable"
        };
    }
}
