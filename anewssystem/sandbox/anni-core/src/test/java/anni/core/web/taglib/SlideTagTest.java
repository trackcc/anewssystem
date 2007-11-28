package anni.core.web.taglib;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.BodyContent;

import anni.core.dao.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.dao.DataIntegrityViolationException;


public class SlideTagTest extends TestCase {
    SlideTag tag = null;

    @Override
    protected void setUp() {
        tag = new SlideTag();

        String[][] info = new String[][] {
                {"图片地址", "图片链接", "图片名称"}
            };
        tag.setInfo(info);
    }

    @Override
    protected void tearDown() {
    }

    public void testDefault() {
        assertNotNull(tag.getInfo());
    }

    public void testDefault2() {
        tag.setInfo(null);
        assertNull(tag.getInfo());
    }

    public void testDoStartTag() throws Exception {
        assertEquals(1, tag.doStartTag());
    }

    public void testDoEndTag() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);

        EasyMock.expect(pageContext.getOut()).andReturn(jspWriter);
        jspWriter.write(isA(String.class));
        EasyMock.replay(pageContext);
        EasyMock.replay(jspWriter);

        tag.setPageContext(pageContext);
        assertEquals(6, tag.doEndTag());

        EasyMock.verify();
    }

    public void testDoEndTag2() throws Exception {
        PageContext pageContext = EasyMock.createMock(PageContext.class);
        JspWriter jspWriter = EasyMock.createMock(JspWriter.class);

        EasyMock.expect(pageContext.getOut()).andReturn(jspWriter);
        jspWriter.write(isA(String.class));
        expectLastCall().andThrow(new IOException());
        EasyMock.replay(pageContext);
        EasyMock.replay(jspWriter);

        tag.setPageContext(pageContext);

        try {
            assertEquals(6, tag.doEndTag());
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }

        EasyMock.verify();
    }
}
