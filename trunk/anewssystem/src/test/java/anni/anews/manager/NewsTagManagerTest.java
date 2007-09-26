package anni.anews.manager;

import anni.anews.domain.NewsTag;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsTagManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(NewsTagManagerTest.class);
    private NewsTagManager newsTagManager;

    public void setNewsTagManager(NewsTagManager newsTagManager) {
        this.newsTagManager = newsTagManager;
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
        assertNotNull(newsTagManager);
    }
}
