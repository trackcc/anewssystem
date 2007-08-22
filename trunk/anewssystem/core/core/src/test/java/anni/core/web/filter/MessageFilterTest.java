package anni.core.web.filter;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;


public class MessageFilterTest extends TestCase {
    MessageFilter filter = null;

    @Override
    protected void setUp() {
        filter = new MessageFilter();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDoFilterInternal() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        FilterChain chain = createMock(FilterChain.class);
        HttpSession session = createMock(HttpSession.class);

        expect(request.getSession()).andReturn(session);
        expect(session.getAttribute("messages")).andReturn(null);
        chain.doFilter(request, response);
        replay(request);
        replay(response);
        replay(chain);
        replay(session);

        filter.doFilterInternal(request, response, chain);
        verify();
    }

    public void testDoFilterInternal2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        FilterChain chain = createMock(FilterChain.class);
        HttpSession session = createMock(HttpSession.class);

        List<String> messages = new ArrayList<String>();
        expect(request.getSession()).andReturn(session).times(2);
        expect(session.getAttribute("messages")).andReturn(messages);
        request.setAttribute("messages", messages);
        session.removeAttribute("messages");
        chain.doFilter(request, response);
        replay(request);
        replay(response);
        replay(chain);
        replay(session);

        filter.doFilterInternal(request, response, chain);
        verify();
    }
}
