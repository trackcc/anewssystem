package anni.anews.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsCategoryTest extends TestCase {
    protected static Log logger = LogFactory.getLog(NewsCategoryTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        NewsCategory entity = new NewsCategory();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setTop(null);
        assertNull(entity.getTop());
        entity.setParent(null);
        assertNull(entity.getParent());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setTheSort(null);
        assertNull(entity.getTheSort());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setAllChildren(null);
        assertNull(entity.getAllChildren());
        entity.setChildren(null);
        assertNull(entity.getChildren());
        entity.setNewses(null);
        assertNull(entity.getNewses());
    }
}
