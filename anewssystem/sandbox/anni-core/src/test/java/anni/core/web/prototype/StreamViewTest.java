package anni.core.web.prototype;

import junit.framework.TestCase;


public class StreamViewTest extends TestCase {
    protected StreamView view = null;

    @Override
    protected void setUp() {
        view = new StreamView();
    }

    @Override
    protected void tearDown() {
    }

    public void testContentType() throws Exception {
        assertEquals("text/plain", view.getContentType());

        view.render(null, null, null);
    }
}
