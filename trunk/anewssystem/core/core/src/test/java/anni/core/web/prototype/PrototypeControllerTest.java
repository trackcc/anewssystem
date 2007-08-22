package anni.core.web.prototype;

import java.io.*;

import java.lang.reflect.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import anni.core.dao.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;


public class PrototypeControllerTest extends TestCase {
    PrototypeController controller = null;

    @Override
    protected void setUp() {
        controller = new PrototypeController();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testConstructor() {
        PrototypeController controller = new PrototypeController(new Object());
        controller.setValidators(null);
        controller.setDelegate(new TestPrototypeController());
    }

    public void testMethodNameResolver() {
        controller.setMethodNameResolver(null);
        assertNull(controller.getMethodNameResolver());
    }

    public void testGetLastModified() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/").anyTimes();

        replay(request);

        assertEquals(-1, controller.getLastModified(request));

        verify();
    }

    public void testHandleNoSuchRequestHandlingMethod()
        throws Exception {
        HttpServletResponse response = createMock(HttpServletResponse.class);
        response.sendError(404);
        replay(response);

        ModelAndView mv = controller.handleNoSuchRequestHandlingMethod(new NoSuchRequestHandlingMethodException(
                    "no", controller.getClass()), null, response);
        verify();
        assertNull(mv);
    }

    public void testnewCommandObject() throws Exception {
        Object cmd = controller.newCommandObject(String.class);
        assertTrue(cmd instanceof String);
    }

    public void testBind() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        Validator validator = createMock(Validator.class);
        Validator validator2 = createMock(Validator.class);
        Object cmd = new Object();

        expect(request.getParameterNames())
            .andReturn(new StringTokenizer(""));
        expect(validator.supports(Object.class)).andReturn(true).anyTimes();
        validator.validate(same(cmd), isA(BeanPropertyBindingResult.class));
        expect(validator2.supports(Object.class)).andReturn(false)
            .anyTimes();

        replay(request);
        replay(validator);

        controller.setValidators(new Validator[] {validator, validator2});
        controller.bind(request, cmd);
        verify();
    }

    public void testBindWithoutValidator() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        Object cmd = new Object();

        expect(request.getParameterNames())
            .andReturn(new StringTokenizer(""));

        replay(request);

        controller.bind(request, cmd);
        verify();
    }

    public void testGetExceptionHandler() throws Exception {
        Method method = controller.getExceptionHandler(new Throwable());
        assertNull(method);
    }

    public void testExceptionHandler() throws Exception {
        TestPrototypeController controller = new TestPrototypeController();
        Method method = controller.getExceptionHandler(new RuntimeException());
        assertNotNull(method);
    }

    public void testInvokeNamedMethod2() throws Exception {
        TestPrototypeController controller = new TestPrototypeController();
        controller.setDelegate(new TestPrototypeController());

        try {
            controller.invokeNamedMethod(null);
            fail();
        } catch (NoSuchRequestHandlingMethodException ex) {
            assertTrue(true);
        }
    }

    public void testInvokeNamedMethod3() throws Exception {
        TestPrototypeController controller = new TestPrototypeController();
        controller.setDelegate(new TestPrototypeController());

        try {
            controller.invokeNamedMethod("test3");
            fail();
        } catch (Throwable ex) {
            assertTrue(true);
        }
    }

    public void testInvokeNamedMethod4() throws Exception {
        try {
            TestPrototypeController controller = new TestPrototypeController();
            controller.invokeNamedMethod("test1");
            fail();
        } catch (Error ex) {
            assertTrue(true);
        }
    }

    public void testInvokeNamedMethod5() throws Exception {
        try {
            TestPrototypeController controller = new TestPrototypeController();
            controller.invokeNamedMethod("test2");
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testInvokeNamedMethod6() throws Exception {
        TestPrototypeController controller = new TestPrototypeController();
        controller.invokeNamedMethod("test4");
    }

    public void testGetLastModified2() throws Exception {
        TestPrototypeController controller = new TestPrototypeController();

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/test1.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/test1.htm").anyTimes();

        replay(request);

        assertEquals(-1, controller.getLastModified(request));

        verify();
    }

    public void testGetLastModified3() throws Exception {
        TestPrototypeController controller = new TestPrototypeController();

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getAttribute("javax.servlet.include.context_path"))
            .andReturn("/").anyTimes();
        expect(request.getAttribute("javax.servlet.include.request_uri"))
            .andReturn("/test2.htm").anyTimes();
        expect(request.getAttribute("javax.servlet.include.servlet_path"))
            .andReturn("/test2.htm").anyTimes();

        replay(request);

        assertEquals(1, controller.getLastModified(request));

        verify();
    }

    class TestPrototypeController extends PrototypeController {
        public void handlerException(HttpServletRequest request,
            HttpServletResponse response, RuntimeException throwable) {
            //
        }

        public void handlerException2(HttpServletRequest request,
            HttpServletResponse response, IllegalAccessException throwable)
            throws Exception {
            throw new Exception();
        }

        public void handlerException3(HttpServletRequest request,
            HttpServletResponse response, Error throwable) {
            throw new Error();
        }

        public void test1() throws Exception {
            throw new Error("test1");
        }

        public void test2() throws Exception {
            throw new IllegalAccessException("test2");
        }

        public void test3() throws Throwable {
            throw new Throwable();
        }

        public void test4() throws Exception {
            throw new RuntimeException();
        }

        public Long test1LastModified(HttpServletRequest request)
            throws Exception {
            throw new IllegalAccessException("test1LastModified");
        }

        public Long test2LastModified(HttpServletRequest request)
            throws Exception {
            return 1L;
        }
    }
}
