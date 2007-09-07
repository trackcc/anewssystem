package anni.asecurity.web.filter;

import javax.servlet.http.*;

import anni.asecurity.manager.UserManager;

import junit.framework.*;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;


public class UserAuthenticationProcessingFilterTest extends TestCase {
    public void testOne() {
        UserAuthenticationProcessingFilter filter = new UserAuthenticationProcessingFilter();
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        HttpSession session = createMock(HttpSession.class);
        UserManager userManager = EasyMock.createMock(UserManager.class);

        expect(request.getRequestURI()).andReturn("/");
        expect(request.getContextPath()).andReturn("/").anyTimes();
        expect(request.getSession(false)).andReturn(session);
        expect(session.getAttribute("loginUser")).andReturn(null);

        replay(request);
        replay(response);
        replay(session);
        EasyMock.replay(userManager);

        filter.setUserManager(userManager);

        boolean result = filter.requiresAuthentication(request, response);
        verify();
    }
}
