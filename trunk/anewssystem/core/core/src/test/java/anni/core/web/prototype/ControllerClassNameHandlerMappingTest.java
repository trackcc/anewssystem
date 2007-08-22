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

import org.springframework.context.ApplicationContext;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.throwaway.ThrowawayController;


public class ControllerClassNameHandlerMappingTest extends TestCase {
    ControllerClassNameHandlerMapping mapping = null;

    @Override
    protected void setUp() {
        mapping = new ControllerClassNameHandlerMapping();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testSetter() {
        WebApplicationContext ctx = createMock(WebApplicationContext.class);
        expect(ctx.getBeanNamesForType(Controller.class))
            .andReturn(new String[0]).anyTimes();
        expect(ctx.getBeanNamesForType(ThrowawayController.class))
            .andReturn(new String[0]).anyTimes();

        replay(ctx);
        mapping.setApplicationContext(ctx);
        mapping.setExcludedPackages(null);
        mapping.setExcludedClasses(null);

        mapping.setExcludedPackages(new String[0]);
        mapping.setExcludedClasses(new Class[0]);

        mapping.initApplicationContext();
    }

    public void testInitApplicationContext() {
        WebApplicationContext ctx = createMock(WebApplicationContext.class);
        expect(ctx.getBeanNamesForType(Controller.class))
            .andReturn(new String[] {"one"}).anyTimes();

        expect(ctx.getBeanNamesForType(ThrowawayController.class))
            .andReturn(new String[] {"go"}).anyTimes();

        expect(ctx.getType("one")).andReturn(Object.class).anyTimes();
        expect(ctx.isSingleton("one")).andReturn(true).anyTimes();
        expect(ctx.getBean("one")).andReturn(null).anyTimes();
        expect(ctx.getType("go")).andReturn(Thread.class).anyTimes();
        expect(ctx.isSingleton("go")).andReturn(true).anyTimes();
        expect(ctx.getBean("go")).andReturn(null).anyTimes();
        replay(ctx);
        mapping.setApplicationContext(ctx);

        mapping.initApplicationContext();
    }

    public void testInitApplicationContext2() {
        WebApplicationContext ctx = createMock(WebApplicationContext.class);
        BaseController baseController = new BaseController();

        expect(ctx.getBeanNamesForType(Controller.class))
            .andReturn(new String[0]).anyTimes();

        expect(ctx.getBeanNamesForType(ThrowawayController.class))
            .andReturn(new String[0]).anyTimes();

        expect(ctx.getType("baseController"))
            .andReturn(BaseController.class).anyTimes();
        expect(ctx.isSingleton("baseController")).andReturn(true).anyTimes();
        expect(ctx.getBean("baseController")).andReturn(baseController)
            .anyTimes();
        replay(ctx);
        mapping.setApplicationContext(ctx);

        mapping.initApplicationContext();
    }

    public void testInitApplicationContext3() {
        WebApplicationContext ctx = createMock(WebApplicationContext.class);
        BaseController baseController = new BaseController();

        expect(ctx.getBeanNamesForType(Controller.class))
            .andReturn(new String[] {"baseController"}).anyTimes();

        expect(ctx.getBeanNamesForType(ThrowawayController.class))
            .andReturn(new String[0]).anyTimes();

        expect(ctx.getType("baseController")).andReturn(null).anyTimes();
        expect(ctx.isSingleton("baseController")).andReturn(true).anyTimes();
        expect(ctx.getBean("baseController")).andReturn(baseController)
            .anyTimes();
        replay(ctx);
        mapping.setApplicationContext(ctx);

        mapping.initApplicationContext();
    }

    public void testInitApplicationContext4() {
        mapping.setExcludedPackages(new String[] {"java.lang"});
        mapping.setExcludedClasses(new Class[] {List.class});

        WebApplicationContext ctx = createMock(WebApplicationContext.class);
        expect(ctx.getBeanNamesForType(Controller.class))
            .andReturn(new String[] {"one"}).anyTimes();

        expect(ctx.getBeanNamesForType(ThrowawayController.class))
            .andReturn(new String[] {"go"}).anyTimes();

        expect(ctx.getType("one")).andReturn(Object.class).anyTimes();
        expect(ctx.isSingleton("one")).andReturn(true).anyTimes();
        expect(ctx.getBean("one")).andReturn(null).anyTimes();
        expect(ctx.getType("go")).andReturn(List.class).anyTimes();
        expect(ctx.isSingleton("go")).andReturn(true).anyTimes();
        expect(ctx.getBean("go")).andReturn(null).anyTimes();
        replay(ctx);
        mapping.setApplicationContext(ctx);

        mapping.initApplicationContext();
    }
}
