package anni.atracker.manager;

import anni.atracker.domain.TrackerIssue;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TrackerIssueManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(TrackerIssueManagerTest.class);
    private TrackerIssueManager trackerIssueManager;

    public void setTrackerIssueManager(
        TrackerIssueManager trackerIssueManager) {
        this.trackerIssueManager = trackerIssueManager;
    }

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
    }

    @Override
    public void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() {
        assertNotNull(trackerIssueManager);
    }
}
