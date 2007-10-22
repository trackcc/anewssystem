package anni.anews.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsTest extends TestCase {
    protected static Log logger = LogFactory.getLog(NewsTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        News entity = new News();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setNewsCategory(null);
        assertNull(entity.getNewsCategory());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setSubtitle(null);
        assertNull(entity.getSubtitle());
        entity.setLink(null);
        assertNull(entity.getLink());
        entity.setImage(null);
        assertNull(entity.getImage());
        entity.setHit(null);
        assertNull(entity.getHit());
        entity.setSummary(null);
        assertNull(entity.getSummary());
        entity.setContent(null);
        assertNull(entity.getContent());
        entity.setSource(null);
        assertNull(entity.getSource());
        entity.setEditor(null);
        assertNull(entity.getEditor());
        entity.setUpdateDate(null);
        assertNull(entity.getUpdateDate());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setNewsComments(null);
        assertNull(entity.getNewsComments());
        entity.setNewsTags(null);
        assertNull(entity.getNewsTags());

        // ��ҳ
        entity.setHtml("html");
        assertEquals("html", entity.getHtml());
        entity.setPageType(0);
        assertEquals(0, entity.getPageType());
        entity.setPageSize(0);
        assertEquals(0, entity.getPageSize());
    }
}
