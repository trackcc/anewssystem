package anni.atracker.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TrackerProjectTest extends TestCase {
    protected static Log logger = LogFactory.getLog(TrackerProjectTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        TrackerProject entity = new TrackerProject();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setSummary(null);
        assertNull(entity.getSummary());
        entity.setTrackerIssues(null);
        assertNull(entity.getTrackerIssues());
    }
}
