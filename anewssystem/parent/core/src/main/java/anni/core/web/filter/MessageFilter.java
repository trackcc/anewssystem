package anni.core.web.filter;

import java.io.IOException;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 为了信息在redirect时不会消失.
 * {@link anni.core.web.prototype.ExtendController#saveMessage(String)}
 * 使用session暂存message．
 * 而此filter负责把消息 从session 搬回到 request．
 * 来自www.springside.org.cn
 *
 * @author calvin
 * @see anni.core.web.prototype.ExtendController#saveMessage(String)
 * @since 2007-03-14
 * @version 1.0
 */
public class MessageFilter extends OncePerRequestFilter {
    /**
     * 覆盖超类方法.
     *
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器链
     * @throws IOException io异常
     * @throws ServletException servlet异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        List messages = (List) request.getSession().getAttribute("messages");

        if (messages != null) {
            request.setAttribute("messages", messages);
            request.getSession().removeAttribute("messages");
        }

        filterChain.doFilter(request, response);
    }
}
