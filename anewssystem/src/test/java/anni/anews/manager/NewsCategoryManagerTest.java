package anni.anews.manager;

import anni.anews.domain.NewsCategory;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsCategoryManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(NewsCategoryManagerTest.class);
    private NewsCategoryManager newsCategoryManager;

    public void setNewsCategoryManager(
        NewsCategoryManager newsCategoryManager) {
        this.newsCategoryManager = newsCategoryManager;
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
        assertNotNull(newsCategoryManager);
    }
}
