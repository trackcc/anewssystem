package anni.anews.manager;

import anni.anews.domain.News;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(NewsManagerTest.class);
    private NewsManager newsManager;

    public void setNewsManager(NewsManager newsManager) {
        this.newsManager = newsManager;
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
