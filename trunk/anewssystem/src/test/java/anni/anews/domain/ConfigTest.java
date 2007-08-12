package anni.anews.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConfigTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ConfigTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Config entity = new Config();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setNewsNeedAudit(null);
        assertNull(entity.getNewsNeedAudit());
        entity.setCommentNeedAudit(null);
        assertNull(entity.getCommentNeedAudit());
        entity.setCouldComment(null);
        assertNull(entity.getCouldComment());
    }
}
