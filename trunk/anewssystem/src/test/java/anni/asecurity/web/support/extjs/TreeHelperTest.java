package anni.asecurity.web.support.extjs;

import java.util.List;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TreeHelperTest extends TestCase {
    protected static Log logger = LogFactory.getLog(TreeHelperTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testCreateBeanListFromJson() {
        String json = "[{\"id\":\"1\",\"parentId\":-1},{\"id\":\"2\",\"parentId\":-1}]";
        List<TreeNode> list = TreeHelper.createBeanListFromJson(json);
        assertEquals(2, list.size());
    }

    public void testCreateBeanFromJson() {
        String json = "{\"id\":\"1\",\"text\":\"1\",\"parentId\":\"53\"}";
        TreeNode node = TreeHelper.createBeanFromJson(json);
        assertEquals(1L, node.getId());
        assertEquals("1", node.getText());
        assertEquals(53L, node.getParentId());
    }
}
