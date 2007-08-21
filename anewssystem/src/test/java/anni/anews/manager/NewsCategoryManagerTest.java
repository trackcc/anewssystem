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

    public void testInsertCode() {
        NewsCategory entity = new NewsCategory();
        entity.setName("test1");
        newsCategoryManager.save(entity);
        assertEquals(new Long(256L * 256 * 256 * 256 * 256 * 256 * 256),
            entity.getCode());

        NewsCategory entity2 = new NewsCategory();
        entity2.setName("test2");
        entity2.setParent(entity);
        newsCategoryManager.save(entity2);
        assertEquals(new Long(
                (256L + 1) * 256 * 256 * 256 * 256 * 256 * 256),
            entity2.getCode());

        NewsCategory entity3 = new NewsCategory();
        entity3.setName("test3");
        entity3.setParent(entity);
        newsCategoryManager.save(entity3);
        assertEquals(new Long(
                (256L + 2) * 256 * 256 * 256 * 256 * 256 * 256),
            entity3.getCode());

        entity3.setName("test4");
        newsCategoryManager.save(entity3);
        assertEquals(new Long(
                (256L + 2) * 256 * 256 * 256 * 256 * 256 * 256),
            entity3.getCode());
        assertEquals("test4", entity3.getName());
    }
}
