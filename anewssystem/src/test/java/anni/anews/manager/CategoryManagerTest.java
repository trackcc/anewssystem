package anni.anews.manager;

import anni.anews.domain.Category;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CategoryManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(CategoryManagerTest.class);
    private CategoryManager categoryManager;

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
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
        assertNotNull(categoryManager);
    }
}
