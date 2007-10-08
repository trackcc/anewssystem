package anni.atracker.web;

import anni.atracker.domain.TrackerProject;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TrackerProjectControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(TrackerProjectControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (TrackerProjectController) applicationContext.getBean(
                "anni.atracker.web.TrackerProjectController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
    }
}
