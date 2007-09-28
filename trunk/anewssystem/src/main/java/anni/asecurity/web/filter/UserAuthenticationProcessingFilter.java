package anni.asecurity.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import anni.asecurity.domain.User;

import anni.asecurity.manager.UserManager;

import org.acegisecurity.Authentication;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;

import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;

import org.acegisecurity.userdetails.UserDetails;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 把User变量放入http session中,key为Constants.USER_IN_SESSION.
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-25
 * @version 1.0
 */
public class UserAuthenticationProcessingFilter
    extends AuthenticationProcessingFilter {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(UserAuthenticationProcessingFilter.class);

    /**
     * 会话中用户标志.
     */
    public static final String USER_IN_SESSION = "loginUser";

    /**
     * UserDao.
     */
    private UserManager userManager = null;

    /**
     * @param userManager UserManager.
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * 是否需要授权.
     *
     * @param request 请求
     * @param response 响应
     * @return 是否需要授权
     */
    protected boolean requiresAuthentication(HttpServletRequest request,
        HttpServletResponse response) {
        boolean requiresAuth = super.requiresAuthentication(request,
                response);
        HttpSession httpSession = request.getSession(true);

        if (!requiresAuth) {
            SecurityContext sc = SecurityContextHolder.getContext();
            Authentication auth = sc.getAuthentication();

            if ((auth != null)
                    && auth.getPrincipal() instanceof UserDetails) {
                UserDetails ud = (UserDetails) auth.getPrincipal();
                User user = userManager.getUserByLoginidAndPasswd(ud
                        .getUsername(), ud.getPassword());
                httpSession.setAttribute(USER_IN_SESSION, user);
            }
        }

        return requiresAuth;
    }
}
