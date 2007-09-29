package anni.asecurity.web;

import anni.asecurity.domain.User;

import anni.core.json.JsonController;

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
        response.getWriter()
                .print("{failure:true,response:'authenticationFailure'}");
    }

    /**
     * 登录成功后默认进入的页面.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void defaultTarget() throws Exception {
        logger.info(params());

        User loginUser = (User) session.getAttribute("loginUser");
        response.getWriter()
                .print("{success:true,response:'"
            + loginUser.getTruename() + "'}");
    }

    /**
     * 用户密码错误.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void userPasswordError() throws Exception {
        logger.info(params());
        response.getWriter()
                .print("{failure:true,response:'userPasswordError'}");
    }

    /**
     * 用户密码错误.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void tooManyUserError() throws Exception {
        logger.info(params());
        response.getWriter()
                .print("{failure:true,response:'tooManyUserError'}");
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
     * 获得登录用户名.
     *
     * @throws Exception 写入可能抛出异常
     */
    public void getLoginName() throws Exception {
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
}
