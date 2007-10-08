package anni.atracker.manager;

import anni.atracker.domain.TrackerProject;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TrackerProjectManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(TrackerProjectManagerTest.class);
    private TrackerProjectManager trackerProjectManager;

    public void setTrackerProjectManager(
        TrackerProjectManager trackerProjectManager) {
        this.trackerProjectManager = trackerProjectManager;
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
        assertNotNull(trackerProjectManager);
    }
}
