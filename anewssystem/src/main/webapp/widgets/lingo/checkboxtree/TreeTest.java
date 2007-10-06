
import junit.framework.TestCase;

public class TreeTest extends TestCase {
    protected Tree tree;
    protected Tree node1;
    protected Tree node2;
    protected Tree node11;
    protected Tree node12;

    protected void setUp() {
        tree = new Tree("01", true);

        node1 = new Tree("0101", true);
        node1.setParent(tree);
        tree.getChildren().add(node1);
        node2 = new Tree("0102", false);
        node2.setParent(tree);
        tree.getChildren().add(node2);

        node11 = new Tree("010101", false);
        node11.setParent(node1);
        node1.getChildren().add(node11);
        node12 = new Tree("010102", false);
        node12.setParent(node1);
        node1.getChildren().add(node12);

        tree.render();
    }

    protected void tearDown() {
        tree = null;
        node1 = null;
        node2 = null;
        node11 = null;
        node12 = null;
    }

    public void testCheckNode1() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node1.check();
        //System.out.println(tree);

        assertEquals("some", tree.getClassName());
        assertEquals("all", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("all", node11.getClassName());
        assertEquals("all", node12.getClassName());
    }

    public void testCheckNode2() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node2.check();
        //System.out.println(tree);

        assertEquals("all", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("all", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());
    }

    public void testCheckNode3() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node2.check();
        node1.check();
        //System.out.println(tree);

        assertEquals("all", tree.getClassName());
        assertEquals("all", node1.getClassName());
        assertEquals("all", node2.getClassName());
        assertEquals("all", node11.getClassName());
        assertEquals("all", node12.getClassName());
    }

    public void testCheckNode4() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node2.check();
        node1.check();
        node1.check();
        //System.out.println(tree);

        assertEquals("some", tree.getClassName());
        assertEquals("none", node1.getClassName());
        assertEquals("all", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());
    }

    public void testCheckNode5() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node2.check();
        node1.check();
        node1.check();
        node2.check();
        //System.out.println(tree);

        assertEquals("none", tree.getClassName());
        assertEquals("none", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());
    }

    public void testCheckNode6() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node2.check();
        node1.check();
        node1.check();
        node2.check();
        node11.check();
        //System.out.println(tree);

        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("all", node11.getClassName());
        assertEquals("none", node12.getClassName());
    }

    public void testCheckNode7() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node11.check();
        //System.out.println(tree);

        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("all", node11.getClassName());
        assertEquals("none", node12.getClassName());
    }

    public void testCheckNode8() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node11.check();
        node12.check();
        //System.out.println(tree);

        assertEquals("some", tree.getClassName());
        assertEquals("all", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("all", node11.getClassName());
        assertEquals("all", node12.getClassName());
    }

    public void testCheckNode9() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        node11.check();
        node12.check();
        node12.check();
        //System.out.println(tree);

        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("all", node11.getClassName());
        assertEquals("none", node12.getClassName());
    }

    public void testCheckNode10() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        //System.out.println(tree);
        tree.check();
        //System.out.println(tree);

        assertEquals("all", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("all", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());
    }

    // 如果下级已经是all了，上级还是some的情况
    // 点一下上级some -> all，下级不应该变成some，应该保持all状态
    public void testCheckNode11() {
        assertEquals("some", tree.getClassName());
        assertEquals("some", node1.getClassName());
        assertEquals("none", node2.getClassName());
        assertEquals("none", node11.getClassName());
        assertEquals("none", node12.getClassName());

        System.out.println(tree);
        node1.check();
        tree.check();
        System.out.println(tree);

        assertEquals("all", tree.getClassName());
        assertEquals("all", node1.getClassName());
        assertEquals("all", node2.getClassName());
        assertEquals("all", node11.getClassName());
        assertEquals("all", node12.getClassName());
    }
}
