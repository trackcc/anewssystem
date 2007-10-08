package anni.atracker.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TrackerIssueTest extends TestCase {
    protected static Log logger = LogFactory.getLog(TrackerIssueTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        TrackerIssue entity = new TrackerIssue();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setTrackerProject(null);
        assertNull(entity.getTrackerProject());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setPriority(null);
        assertNull(entity.getPriority());
        entity.setSeverity(null);
        assertNull(entity.getSeverity());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setAssignTo(null);
        assertNull(entity.getAssignTo());
        entity.setSubmitBy(null);
        assertNull(entity.getSubmitBy());
        entity.setAddTime(null);
        assertNull(entity.getAddTime());
        entity.setUpdateDate(null);
        assertNull(entity.getUpdateDate());
        entity.setContent(null);
        assertNull(entity.getContent());
        entity.setFile(null);
        assertNull(entity.getFile());
    }
}
