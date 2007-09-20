package anni.core.json;

import java.util.HashSet;
import java.util.Set;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


public class JsonControllerTest extends TestCase {
    protected static Log logger = LogFactory.getLog(JsonControllerTest.class);
    protected TheJsonController controller;

    @Override
    protected void setUp() {
        controller = new TheJsonController();
    }

    @Override
    protected void tearDown() {
        controller = null;
    }

    public void testTrue() {
        assertNotNull(controller);
    }

    public void testGetDatePattern() {
        assertEquals("yyyy-MM-dd", controller.getDatePattern());
    }

    public void testGetEntityClass() {
        assertEquals(Object.class, controller.getEntityClass());
    }

    public void testEntityDao() {
        Object object = new Object();
        controller.setEntityDao(object);
        assertEquals(object, controller.getEntityDao());
    }

    public void testGetCommandName() {
        assertEquals("object", controller.getCommandName(new Object()));
    }

    public void testResponseEncoding() {
        assertEquals(JsonController.JSON_DEFAULT_ENCODING,
            controller.getResponseEncoding());
        controller.setResponseEncoding("GBK");
        assertEquals("GBK", controller.getResponseEncoding());
    }

    public void testHandleRequestInternal() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertNotNull(controller.handleRequestInternal(request, response));
    }

    public void testHandleRequestInternal2() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/thejson/index.htm");

        MockHttpServletResponse response = new MockHttpServletResponse();
        ModelAndView mv = controller.handleRequestInternal(request,
                response);
        assertEquals("index", mv.getViewName());
    }

    public static class TheJsonController extends JsonController<Object, Object> {
        public void index() {
            mv.setViewName("index");
        }
    }
}
