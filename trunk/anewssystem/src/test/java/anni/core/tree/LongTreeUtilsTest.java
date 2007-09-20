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
}
