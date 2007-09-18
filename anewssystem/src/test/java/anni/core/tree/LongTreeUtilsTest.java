package anni.core.tree;

import java.io.*;

import java.util.*;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LongTreeUtilsTest extends TestCase {
    protected static Log logger = LogFactory.getLog(LongTreeUtilsTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testConstructor() {
        LongTreeUtils utils = new LongTreeUtils();
        assertNotNull(utils);
    }

    public void testIsDeadLock() {
        //
        assertTrue(LongTreeUtils.isDeadLock(null, null));

        //
        LongTreeNode node = new LongTreeNode();
        assertTrue(LongTreeUtils.isDeadLock(node, null));

        //
        assertTrue(LongTreeUtils.isDeadLock(null, node));

        //
        assertTrue(LongTreeUtils.isDeadLock(node, node));
    }

    public void testIsDeadLock2() {
        LongTreeNode node = new LongTreeNode();
        LongTreeNode node2 = new LongTreeNode();
        //
        assertFalse(LongTreeUtils.isDeadLock(node, node2));
    }

    public void testIsDeadLock3() {
        LongTreeNode node = new LongTreeNode();
        LongTreeNode node2 = new LongTreeNode();
        node.getChildren().add(node2);
        //
        assertTrue(LongTreeUtils.isDeadLock(node, node2));
    }

    public void testIsDeadLock4() {
        LongTreeNode node = new LongTreeNode();
        LongTreeNode node2 = new LongTreeNode();
        LongTreeNode node3 = new LongTreeNode();
        node.getChildren().add(node3);
        //
        assertFalse(LongTreeUtils.isDeadLock(node, node2));
    }

    public void testWrite() throws Exception {
        LongTreeNode node = new LongTreeNode();
        StringWriter writer = new StringWriter();
        String[] excludes = new String[] {"class", "root", "parent"};
        LongTreeUtils.write(node, writer, excludes, null);

        assertEquals("{\"parentId\":0,\"theSort\":0,\"cls\":\"\",\"leaf\":true,\"qtip\":\"\",\"allowDelete\":true,\"allowEdit\":true,\"draggable\":true,\"id\":0,\"text\":\"\",\"allowChildren\":true,\"name\":\"\",\"children\":[]}",
            writer.toString());
    }

    public void testJson2Node() throws Exception {
        String json = "{\"id\":1,\"name\":\"name\",\"theSort\":0}";
        String[] excludes = new String[] {"class", "root", "parent"};
        LongTreeNode node = LongTreeUtils.json2Node(json,
                LongTreeNode.class, excludes, null);
        assertEquals(new Long(1L), node.getId());
        assertEquals("name", node.getName());
    }

    public void testJson2List() throws Exception {
        String json = "[{\"id\":1},{\"id\":2}]";
        String[] excludes = new String[] {"class", "root", "parent"};
        List<LongTreeNode> list = LongTreeUtils.json2List(json,
                LongTreeNode.class, excludes, null);
        assertEquals(2, list.size());
    }

    public void testJson2List2() throws Exception {
        String json = "[]";
        String[] excludes = new String[] {"class", "root", "parent"};
        List<LongTreeNode> list = LongTreeUtils.json2List(json,
                LongTreeNode.class, excludes, null);
        assertEquals(0, list.size());
    }
}
