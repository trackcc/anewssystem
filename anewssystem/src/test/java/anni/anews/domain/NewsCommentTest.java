package anni.anews.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsCommentTest extends TestCase {
    protected static Log logger = LogFactory.getLog(NewsCommentTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        NewsComment entity = new NewsComment();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setNews(null);
        assertNull(entity.getNews());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setContent(null);
        assertNull(entity.getContent());
        entity.setUsername(null);
        assertNull(entity.getUsername());
        entity.setUpdateDate(null);
        assertNull(entity.getUpdateDate());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setIp(null);
        assertNull(entity.getIp());
    }
}
