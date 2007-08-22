package anni.core.utils;

import java.text.*;

import java.util.*;


public class XTreeUtils {
    public static final MessageFormat TREE = new MessageFormat(
            "var {0}{2} = new WebFXTree(\"{3}\", \"javascript:{1}(''{2}'', ''{4}'', {5}, ''{3}'', ''{6}'', ''{7}'', ''{8}'')\");\n"
            + "xtree_data[{0}{2}.id] = '{'id:{2}, parent:{4}, isLeaf:{5}, name:''{3}'', description:''{6}'', action:''{7}'', target:''{8}'''}';\n");
    public static final MessageFormat ITEM = new MessageFormat(
            "var {0}{2} = new WebFXTreeItem(\"{3}\", \"javascript:{1}(''{2}'', ''{4}'', {5}, ''{3}'', ''{6}'', ''{7}'', ''{8}'')\");\n"
            + "xtree_data[{0}{2}.id] = '{'id:{2}, parent:{4}, isLeaf:{5}, name:''{3}'', description:''{6}'', action:''{7}'', target:''{8}'''}';\n");

    /**
     * 生成一个xtree的节点.
     *
     * @param isRoot 是否为根节点
     * @param args {prefix, click, id, name, parent_id, isLeaf, description, action, target}
     *              {0}     {1}    {2} {3}   {4}        {5}     {6}          {7}     {8}
     */
    public static String getTreeData(boolean isRoot, Object[] args) {
        // System.out.println(Arrays.asList(TREE.getFormatsByArgumentIndex()));
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                args[i] = "";
            }
        }

        if (isRoot) {
            return TREE.format(args);
        } else {
            return ITEM.format(args);
        }
    }
}
