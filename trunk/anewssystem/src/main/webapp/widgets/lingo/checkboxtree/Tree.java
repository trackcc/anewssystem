
import java.util.*;

public class Tree {
    String name;
    boolean checked;
    String className;

    List<Tree> children = new ArrayList<Tree>();
    Tree parent = null;

    //
    public Tree(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }
    public void render() {
        className = isAllChildrenChecked();
        for (Tree child : children) {
            child.render();
        }
    }

    public String getName() {
        return name;
    }
    public String getClassName() {
        return className;
    }
    public boolean isChecked() {
        return checked;
    }
    public boolean isLeaf() {
        return children.size() == 0;
    }

    public List<Tree> getChildren() {
        return children;
    }
    public Tree getParent() {
        return parent;
    }
    public void setParent(Tree parent) {
        this.parent = parent;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        printTree(buff, this, 0);
        return buff.toString();
    }

    private void printTree(StringBuffer buff, Tree node, int indent) {
        for (int i = 0; i < indent; i++) {
            buff.append("  ");
        }
        buff.append("[").append(node.className).append("]").append(node.name).append("\n");
        for (Tree child : node.children) {
            printTree(buff, child, indent + 2);
        }
    }

    // ====================================================
    //
    private String isAllChildrenChecked() {
        if (!isLeaf()) {
            int n = 0;
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i).isChecked()) {
                    n++;
                }
            }
            if (isChecked()) {
                return (n == children.size()) ? "all" : "some";
            } else {
                return "none";
            }
        } else {
            return isChecked() ? " all" : "none";
        }
    }

    private String getNextStatus() {
        if (isLeaf()) {
            return className.equals("all") ? "none" : "all";
        } else {
            if (className.equals("all")) {
                return "none";
            } else if (className.equals("none")) {
                return "some";
            } else {
                return "all";
            }
        }
    }

    private String changeParentStatus() {
        int n = 0;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).isChecked()) {
                n++;
            }
        }
        if (n == children.size()) {
            return "all";
        } else if (n == 0) {
            return "none";
        } else {
            return "some";
        }
    }

    //
    public void check() {
        className = getNextStatus();
        checked = !"none".equals(className);
        if (parent != null) {
            parent.checkParent();
        }
        if (!isLeaf()) {
            for (Tree child : children) {
                child.checkChild(className.equals("all"));
            }
        }
    }

    private void checkParent() {
        className = changeParentStatus();
        checked = !"none".equals(className);
        if (parent != null) {
            parent.checkParent();
        }
    }

    private void checkChild(boolean isCheck) {
        if (isLeaf()) {
            className = isCheck ? "all" : "none";
            checked = !"none".equals(className);
        } else {
            if (isCheck) {
                if (className.equals("none")) {
                    className = "some";
                }
                checked = true;
            } else {
                className = "none";
                checked = !"none".equals(className);
                for (Tree child : children) {
                    child.checkChild(false);
                }
            }
        }
    }
}
