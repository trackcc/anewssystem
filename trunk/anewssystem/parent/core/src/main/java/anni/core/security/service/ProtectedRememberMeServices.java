package anni.core.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;

import org.acegisecurity.ui.rememberme.TokenBasedRememberMeServices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * TokenBasedRememberMeServices的logout方法，在session失效的时候会抛出NullPointerException.
 * 这里对问题进行了修补.
 *
 * @author Lingo
 * @since 2007-06-05
 */
public class ProtectedRememberMeServices
    extends TokenBasedRememberMeServices {
    private static Log logger = LogFactory.getLog(ProtectedRememberMeServices.class);

    /**
     * 注销.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authentication 可能为null
     */
    @Override
    public void logout(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication) {
        logger.info("start");
        response.addCookie(makeCancelCookie(request));
        logger.info("end");
    }
}
