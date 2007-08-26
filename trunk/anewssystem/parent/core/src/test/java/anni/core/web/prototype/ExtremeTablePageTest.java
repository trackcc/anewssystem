package anni.core.web.prototype;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import anni.core.dao.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.extremecomponents.table.limit.Limit;
import org.extremecomponents.table.limit.Sort;

import org.springframework.dao.DataIntegrityViolationException;


public class ExtremeTablePageTest extends TestCase {
    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testGetLimit() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpSession session = createMock(HttpSession.class);
        ServletContext context = createMock(ServletContext.class);

        expect(request.getSession()).andReturn(session);
        expect(session.getServletContext()).andReturn(context);
        expect(context.getInitParameter(
                "extremecomponentsPreferencesLocation")).andReturn("/");
        expect(request.getParameterMap()).andReturn(new HashMap());
        expect(request.getParameter("ec_eti")).andReturn("");

        replay(request);
        replay(session);
        replay(context);

        ExtremeTablePage.getLimit(request);
        verify();
    }

    public void testGetSort() {
        Limit limit = createMock(Limit.class);
        Sort sort = new Sort("alias", "property", "sortOrder");

        expect(limit.getSort()).andReturn(sort);

        replay(limit);

        Map map = ExtremeTablePage.getSort(limit);
        verify();
    }

    public void testGetSort2() {
        Map map = ExtremeTablePage.getSort(null);
        assertTrue(map.isEmpty());
    }
}
