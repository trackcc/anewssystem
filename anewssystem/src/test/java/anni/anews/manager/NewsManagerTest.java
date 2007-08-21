package anni.anews.manager;

import anni.anews.domain.News;
import anni.anews.domain.NewsCategory;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(NewsManagerTest.class);
    private NewsManager newsManager;
    private NewsCategoryManager newsCategoryManager;

    public void setNewsManager(NewsManager newsManager) {
        this.newsManager = newsManager;
    }

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
        assertNotNull(newsManager);
    }

    public void testGetAllChildren() {
        NewsCategory entity1 = new NewsCategory();
        entity1.setName("test1");
        newsCategoryManager.save(entity1);
        assertEquals(1, entity1.getLevel());

        News news1 = new News();
        news1.setNewsCategory(entity1);
        newsManager.save(news1);

        NewsCategory entity2 = new NewsCategory();
        entity2.setName("test2");
        entity2.setParent(entity1);
        newsCategoryManager.save(entity2);
        assertEquals(2, entity2.getLevel());

        News news2 = new News();
        news2.setNewsCategory(entity2);
        newsManager.save(news2);

        NewsCategory entity3 = new NewsCategory();
        entity3.setName("test3");
        entity3.setParent(entity2);
        newsCategoryManager.save(entity3);
        assertEquals(3, entity3.getLevel());

        News news3 = new News();
        news3.setNewsCategory(entity3);
        newsManager.save(news3);

        assertEquals(3, newsManager.getAllChildren(entity1).size());
        assertEquals(2, newsManager.getAllChildren(entity2).size());
        assertEquals(1, newsManager.getAllChildren(entity3).size());
    }
}
