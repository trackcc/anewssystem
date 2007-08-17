package anni.anews.manager;

import anni.anews.domain.NewsComment;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsCommentManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(NewsCommentManagerTest.class);
    private NewsCommentManager newsCommentManager;

    public void setNewsCommentManager(
        NewsCommentManager newsCommentManager) {
        this.newsCommentManager = newsCommentManager;
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
        assertNotNull(newsCommentManager);
    }
}
