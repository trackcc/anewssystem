package anni.core.domain.helper;

import java.util.HashSet;
import java.util.Set;

import anni.core.domain.TreeEntityBean;

import junit.framework.TestCase;


public class TreeHelperTest extends TestCase {
    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void test0() {
        TreeHelper treeHelper = new TreeHelper();
        assertNotNull(treeHelper);
    }

    public void test1() {
        assertTrue(TreeHelper.checkDeadLock(null, null));
    }

    public void test2() {
        TreeEntityBean bean1 = new Node();
        assertTrue(TreeHelper.checkDeadLock(bean1, null));
    }

    public void test3() {
        TreeEntityBean bean1 = new Node();
        assertTrue(TreeHelper.checkDeadLock(null, bean1));
    }

    public void test4() {
        TreeEntityBean bean1 = new Node();
        assertTrue(TreeHelper.checkDeadLock(bean1, bean1));
    }

    public void test5() {
        TreeEntityBean bean1 = new Node();
        TreeEntityBean bean2 = new Node();
        assertFalse(TreeHelper.checkDeadLock(bean1, bean2));
    }

    public void test6() {
        TreeEntityBean bean1 = new Node();
        TreeEntityBean bean2 = new Node();
        bean2.getChildren().add(bean1);
        assertFalse(TreeHelper.checkDeadLock(bean1, bean2));
    }

    public void test7() {
        TreeEntityBean bean1 = new Node();
        TreeEntityBean bean2 = new Node();
        bean1.getChildren().add(bean2);
        assertTrue(TreeHelper.checkDeadLock(bean1, bean2));
    }

    public void test8() {
        TreeEntityBean bean1 = new Node();
        TreeEntityBean bean2 = new Node();
        TreeEntityBean bean3 = new Node();
        bean1.getChildren().add(bean2);
        assertFalse(TreeHelper.checkDeadLock(bean1, bean3));
    }

    static class Node implements TreeEntityBean<Node> {
        private Node parent;
        private Set<Node> children = new HashSet<Node>();

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Set<Node> getChildren() {
            return children;
        }

        public void setChildren(Set<Node> children) {
            this.children = children;
        }

        public boolean isRoot() {
            return parent == null;
        }

        public boolean isLeaf() {
            return children.size() == 0;
        }

        public boolean checkDeadLock(Node parent) {
            return TreeHelper.checkDeadLock(this, parent);
        }
    }
}
