package anni.asecurity.web;

import java.util.HashMap;
import java.util.Map;

import anni.asecurity.domain.User;

import anni.core.json.JsonController;
import anni.core.json.JsonUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 执行acegi登录成功，或失败之后的转发任务，兼顾测试是否已登录的判断.
 *
 * @author Lingo
 * @since 2007-09-29
 */
public class LoginController extends JsonController {
    /** * logger. */
    private static Log logger = LogFactory.getLog(LoginController.class);

    /**
     * 验证失败.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void authenticationFailure() throws Exception {
        logger.info(params());
        response.getWriter().print("{failure:true,response:'验证失败'}");
    }

    /**
     * 用户密码错误.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void userPasswordError() throws Exception {
        logger.info(params());
        response.getWriter().print("{failure:true,response:'密码错误'}");
    }

    /**
     * 同一用户不能登录两次.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void tooManyUserError() throws Exception {
        logger.info(params());
        response.getWriter().print("{failure:true,response:'同一用户不能登录两次'}");
    }

    /**
     * 验证码错误.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void captchaError() throws Exception {
        logger.info(params());
        response.getWriter().print("{failure:true,response:'验证码错误'}");
    }

    /**
     * 检测用户是否已经登录.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void isLogin() throws Exception {
        logger.info(params());

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser != null) {
            response.getWriter()
                    .print("{success:true,response:'"
                + loginUser.getTruename() + "'}");
        } else {
            response.getWriter().print("{failure:true}");
        }
    }

    /**
     * 登录成功.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void loginSuccess() throws Exception {
        String callback = getStrParam("callback", null);
        User loginUser = (User) session.getAttribute("loginUser");
        logger.info(callback);

        if (loginUser != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", true);
            map.put("response", loginUser.getTruename());
            map.put("callback", callback);
            JsonUtils.write(map, response.getWriter(), null, null);
        } else {
            response.getWriter().print("{success:false}");
        }
    }
}
