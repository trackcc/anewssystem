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

    public void testInsertCodeByBitCode() {
        NewsCategory entity = new NewsCategory();
        entity.setName("test1");
        newsCategoryManager.save(entity, NewsCategory.STRATEGY_BIT_CODE);
        assertEquals(new Long(256L * 256 * 256 * 256 * 256 * 256 * 256),
            entity.getBitCode());

        NewsCategory entity2 = new NewsCategory();
        entity2.setName("test2");
        entity2.setParent(entity);
        newsCategoryManager.save(entity2, NewsCategory.STRATEGY_BIT_CODE);
        assertEquals(new Long(
                (256L + 1) * 256 * 256 * 256 * 256 * 256 * 256),
            entity2.getBitCode());

        NewsCategory entity3 = new NewsCategory();
        entity3.setName("test3");
        entity3.setParent(entity);
        newsCategoryManager.save(entity3, NewsCategory.STRATEGY_BIT_CODE);
        assertEquals(new Long(
                (256L + 2) * 256 * 256 * 256 * 256 * 256 * 256),
            entity3.getBitCode());

        entity3.setName("test4");
        newsCategoryManager.save(entity3, NewsCategory.STRATEGY_BIT_CODE);
        assertEquals(new Long(
                (256L + 2) * 256 * 256 * 256 * 256 * 256 * 256),
            entity3.getBitCode());
        assertEquals("test4", entity3.getName());

        entity3.setParent(entity2);
        newsCategoryManager.save(entity3, NewsCategory.STRATEGY_BIT_CODE);
        assertEquals(new Long(
                (((256L + 1) * 256) + 1) * 256 * 256 * 256 * 256 * 256),
            entity3.getBitCode());
    }

    public void testInsertCodeByCharCode() {
        NewsCategory entity = new NewsCategory();
        entity.setName("test1");
        newsCategoryManager.save(entity, NewsCategory.STRATEGY_CHAR_CODE);
        assertEquals("01", entity.getCharCode());

        NewsCategory entity2 = new NewsCategory();
        entity2.setName("test2");
        entity2.setParent(entity);
        newsCategoryManager.save(entity2, NewsCategory.STRATEGY_CHAR_CODE);
        assertEquals("0101", entity2.getCharCode());

        NewsCategory entity3 = new NewsCategory();
        entity3.setName("test3");
        entity3.setParent(entity);
        newsCategoryManager.save(entity3, NewsCategory.STRATEGY_CHAR_CODE);
        assertEquals("0102", entity3.getCharCode());

        entity3.setName("test4");
        newsCategoryManager.save(entity3, NewsCategory.STRATEGY_CHAR_CODE);
        assertEquals("0102", entity3.getCharCode());
        assertEquals("test4", entity3.getName());

        entity3.setParent(entity2);
        newsCategoryManager.save(entity3, NewsCategory.STRATEGY_CHAR_CODE);
        assertEquals("010101", entity3.getCharCode());
    }
}
