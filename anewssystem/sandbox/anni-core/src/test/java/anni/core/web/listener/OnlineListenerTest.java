package anni.core.web.listener;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSessionBindingEvent;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.mock.web.MockHttpSession;


public class OnlineListenerTest extends TestCase {
    OnlineListener listener = null;

    @Override
    protected void setUp() {
        listener = new OnlineListener();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testValueBound() {
        HttpSessionBindingEvent event = EasyMock.createMock(HttpSessionBindingEvent.class);
        HttpSession session = new MockHttpSession();

        EasyMock.expect(event.getSession()).andReturn(session).anyTimes();
        EasyMock.replay(event);

        listener.valueBound(event);
        listener.valueUnbound(event);

        EasyMock.verify();
    }

    public void testValueBound2() {
        HttpSessionBindingEvent event = EasyMock.createMock(HttpSessionBindingEvent.class);
        HttpSession session = new MockHttpSession();

        EasyMock.expect(event.getSession()).andReturn(session).anyTimes();
        EasyMock.replay(event);

        listener.valueBound(event);
        listener.valueUnbound(event);
        listener.valueBound(event);
        listener.valueUnbound(event);

        EasyMock.verify();
    }

    public void testValueUnbound() {
        HttpSessionBindingEvent event = EasyMock.createMock(HttpSessionBindingEvent.class);
        HttpSession session = new MockHttpSession();

        EasyMock.expect(event.getSession()).andReturn(session).anyTimes();
        EasyMock.replay(event);

        listener.valueUnbound(event);

        EasyMock.verify();
    }
}
