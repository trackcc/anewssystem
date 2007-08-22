package anni.core.domain.tree;

import java.text.MessageFormat;

import java.util.List;


/**
 * XTree树型辅助生成工具.
 *
 * @author Lingo
 * @since 2007-06-05
 */
public class XTreeUtils {
    /**
     * 树根结点.
     */
    public static final MessageFormat TREE = new MessageFormat(
            "var {0}{2}=new WebFXTree(\"{3}\","
            + "\"javascript:{1}(''{2}'',''{4}'',{5},''{3}'',''{6}'',''{7}'',''{8}'')\");\n"
            + "xtree_data[{0}{2}.id]="
            + "'{'id:{2},parent:{4},isLeaf:{5},name:''{3}'',description:''{6}'',action:''{7}'',target:''{8}'''}';\n");

    /**
     * 除了根结点外的其他结点.
     */
    public static final MessageFormat ITEM = new MessageFormat(
            "var {0}{2}=new WebFXTreeItem(\"{3}\","
            + "\"javascript:{1}(''{2}'',''{4}'',{5},''{3}'',''{6}'',''{7}'',''{8}'')\");\n"
            + "xtree_data[{0}{2}.id]="
            + "'{'id:{2},parent:{4},isLeaf:{5},name:''{3}'',description:''{6}'',action:''{7}'',target:''{8}'''}';\n");

    /**
     * protected contructor.
     */
    protected XTreeUtils() {
    }

    /**
     * 以root为根生成一棵数.
     *
     * @param root LongTreeEntityBean
     * @param prefix 树型变量的前缀
     * @param doClick 点击鼠标左键触发的事件
     * @return 返回用来生成树的javascript字符串
     */
    public static String makeTree(
        LongTreeEntityBean<LongTreeEntityBean> root, String prefix,
        String doClick) {
        return getWebTree(root, prefix, doClick);
    }

    /**
     * 以root为根生成一棵树的时候，用这个方法生成树的结点.
     *
     * @param node 结点
     * @param prefix 前缀
     * @param doClick 点击鼠标左键触发的事件
     * @return String
     */
    public static String getWebTree(
        LongTreeEntityBean<LongTreeEntityBean> node, String prefix,
        String doClick) {
        StringBuffer buff = new StringBuffer();

        // 如果是根，就生成根节点
        if (node.isRoot()) {
            buff.append(getTreeData(node, prefix, doClick));
        }

        // 如果不是叶子节点，说明拥有下级子节点，迭代生成下级树型
        if (!node.isLeaf()) {
            String parentName = prefix + node.getId();

            for (LongTreeEntityBean child : node.getChildren()) {
                buff.append(XTreeUtils.getTreeData(child, prefix, doClick));
                buff.append(parentName).append(".add(").append(prefix)
                    .append(child.getId()).append(");\n");
                buff.append(XTreeUtils.getWebTree(child, prefix, doClick));
            }
        }

        // 最后把根节点写到document里，显示树型
        if (node.isRoot()) {
            buff.append("document.write(").append(prefix)
                .append(node.getId()).append(");\n");
        }

        return buff.toString();
    }

    /**
     * 生成一个xtree的节点.
     * param args {prefix, click, id, name, parent_id, isLeaf, description, action, target}
     *              {0}     {1}    {2} {3}   {4}        {5}     {6}          {7}     {8}
     *
     * @param node 结点
     * @param prefix 前缀
     * @param doClick 点击鼠标左键触发的事件
     * @return String
     */
    public static String getTreeData(
        LongTreeEntityBean<LongTreeEntityBean> node, String prefix,
        String doClick) {
        Object args = generateArgs(node, prefix, doClick);

        if (node.isRoot()) {
            return TREE.format(args);
        } else {
            return ITEM.format(args);
        }
    }

    /**
     * 自动生成一个根节点，并把队列中所有的LongTreeEntityBean作为它的子节点.
     *
     * @param nodes LongTreeEntityBean list
     * @param prefix 树型变量的前缀
         * @param rootId 根结点的id
     * @param doClick 点击鼠标左键触发的事件
     * @return 返回用来生成树的javascript字符串
     */
    public static String makeTree(
        List<LongTreeEntityBean<LongTreeEntityBean>> nodes, String rootId,
        String prefix, String doClick) {
        StringBuffer buff = new StringBuffer();

        for (LongTreeEntityBean<LongTreeEntityBean> node : nodes) {
            buff.append(XTreeUtils.getWebTreeItem(node, rootId, prefix,
                    doClick));
        }

        return buff.toString();
    }

    /**
     * 在自动生成一个根结点的时候，用这个方法生成树的结点.
     *
     * @param node 结点
     * @param prefix 变量前缀
     * @param rootId 根节点的id
     * @param doClick 点击鼠标左键触发的事件
     * @return StringBuffer
     */
    public static StringBuffer getWebTreeItem(
        LongTreeEntityBean<LongTreeEntityBean> node, String prefix,
        String rootId, String doClick) {
        StringBuffer buff = new StringBuffer();

        if (node.isRoot()) {
            buff.append(getTreeItemData(node, prefix, doClick));
            buff.append(rootId).append(".add(").append(prefix)
                .append(node.getId()).append(");\n");
        }

        if (!node.isLeaf()) {
            String parentName = prefix + node.getId();

            for (LongTreeEntityBean child : node.getChildren()) {
                buff.append(XTreeUtils.getTreeItemData(child, prefix,
                        doClick));
                buff.append(parentName).append(".add(").append(prefix)
                    .append(child.getId()).append(");\n");
                buff.append(XTreeUtils.getTreeItemData(child, prefix,
                        doClick));
            }
        }

        return buff;
    }

    /**
     * 生成一个xtree的节点.
     * param args {prefix, click, id, name, parent_id, isLeaf, description, action, target}
     *              {0}     {1}    {2} {3}   {4}        {5}     {6}          {7}     {8}
     *
     * @param node 结点
     * @param prefix 变量前缀
     * @param doClick 点击鼠标左键触发的事件
     * @return String
     */
    public static String getTreeItemData(
        LongTreeEntityBean<LongTreeEntityBean> node, String prefix,
        String doClick) {
        return ITEM.format(generateArgs(node, prefix, doClick));
    }

    /**
     * 生成需要的参数.
     *
     * @param node 一个结点
     * @param prefix 生成树结点变量的前缀
     * @param doClick 单击时调用的函数名称
     * @return Object[] 参数数组
     */
    private static Object[] generateArgs(
        LongTreeEntityBean<LongTreeEntityBean> node, String prefix,
        String doClick) {
        int leaf;

        if (node.isLeaf()) {
            leaf = 1;
        } else {
            leaf = 0;
        }

        long parent;

        if (node.isRoot()) {
            parent = 0L;
        } else {
            parent = node.getParent().getId();
        }

        Object[] args = new Object[] {
                prefix, doClick, node.getId(), node.getName(), parent, leaf,
                "", "", ""
            };

        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                args[i] = "";
            }
        }

        return args;
    }
}
