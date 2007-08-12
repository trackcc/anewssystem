package anni.anews.manager;

import anni.anews.domain.Tag;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TagManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(TagManagerTest.class);
    private TagManager tagManager;

    public void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
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
        assertNotNull(tagManager);
    }
}
