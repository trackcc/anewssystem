package anni.anews.manager;

import anni.anews.domain.NewsConfig;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsConfigManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(NewsConfigManagerTest.class);
    private NewsConfigManager newsConfigManager;

    public void setNewsConfigManager(NewsConfigManager newsConfigManager) {
        this.newsConfigManager = newsConfigManager;
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
        assertNotNull(newsConfigManager);
    }
}
