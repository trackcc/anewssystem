package anni.core.tree;

import java.util.HashSet;
import java.util.Set;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LongTreeNodeTest extends TestCase {
    protected static Log logger = LogFactory.getLog(LongTreeNodeTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        LongTreeNode entity = new LongTreeNode();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        assertNull(entity.getText());

        //
        entity.setParent(null);
        assertNull(entity.getParent());

        //
        entity.setTheSort(null);
        assertNull(entity.getTheSort());
        entity.setQtip(null);
        assertNull(entity.getQtip());
        entity.setCls(null);
        assertNull(entity.getCls());

        //
        entity.setDraggable(true);
        assertTrue(entity.getDraggable());
        entity.setAllowEdit(true);
        assertTrue(entity.getAllowEdit());
        entity.setAllowDelete(true);
        assertTrue(entity.getAllowDelete());
        entity.setAllowChildren(true);
        assertTrue(entity.getAllowChildren());

        //
        entity.setParentId(null);
        assertNull(entity.getParentId());
        assertTrue(entity.isRoot());
        assertTrue(entity.isLeaf());
    }

    public void testTrue() {
        LongTreeNode entity = new LongTreeNode();
        assertTrue(entity.getDraggable());
        assertTrue(entity.getAllowEdit());
        assertTrue(entity.getAllowDelete());
        assertTrue(entity.getAllowChildren());
    }

    public void testChildren() {
        LongTreeNode entity = new LongTreeNode();
        entity.setChildren(null);
        assertNotNull(entity.getChildren());

        Set<LongTreeNode> children = new HashSet<LongTreeNode>();
        LongTreeNode child = new LongTreeNode();
        children.add(child);
        entity.setChildren(children);
        assertEquals(1, entity.getChildren().size());
    }

    public void testIsRoot() {
        LongTreeNode entity = new LongTreeNode();
        LongTreeNode parent = new LongTreeNode();
        entity.setParent(parent);

        assertFalse(entity.isRoot());
    }

    public void testIsLeaf() {
        LongTreeNode entity = new LongTreeNode();

        Set<LongTreeNode> children = new HashSet<LongTreeNode>();
        LongTreeNode child = new LongTreeNode();
        children.add(child);
        entity.setChildren(children);
        assertFalse(entity.isLeaf());
    }

    public void testDeadLock() {
        LongTreeNode entity = new LongTreeNode();
        assertTrue(entity.isDeadLock(entity));
    }
}
