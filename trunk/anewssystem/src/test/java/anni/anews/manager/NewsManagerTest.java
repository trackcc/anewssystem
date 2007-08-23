package anni.anews.manager;

import anni.anews.domain.News;
import anni.anews.domain.NewsCategory;

import anni.anews.manager.NewsCategoryManager;

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
}
