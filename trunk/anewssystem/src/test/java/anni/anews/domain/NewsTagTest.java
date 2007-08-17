package anni.anews.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsTagTest extends TestCase {
    protected static Log logger = LogFactory.getLog(NewsTagTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        NewsTag entity = new NewsTag();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setTheSort(null);
        assertNull(entity.getTheSort());
        entity.setNewses(null);
        assertNull(entity.getNewses());
    }
}
