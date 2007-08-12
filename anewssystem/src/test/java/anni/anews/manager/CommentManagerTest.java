package anni.anews.manager;

import anni.anews.domain.Comment;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CommentManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(CommentManagerTest.class);
    private CommentManager commentManager;

    public void setCommentManager(CommentManager commentManager) {
        this.commentManager = commentManager;
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
        assertNotNull(commentManager);
    }
}
