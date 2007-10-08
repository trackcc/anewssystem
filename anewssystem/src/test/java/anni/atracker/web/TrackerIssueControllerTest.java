package anni.atracker.web;

import anni.atracker.domain.TrackerIssue;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TrackerIssueControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(TrackerIssueControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (TrackerIssueController) applicationContext.getBean(
                "anni.atracker.web.TrackerIssueController");
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
