package anni.anews.web.support;

import java.io.Writer;

import java.util.ArrayList;
import java.util.List;

import anni.anews.domain.NewsCategory;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;


/**
 * @author Lingo.
 * @since 2007年08月08日 下午 19时19分12秒781
 */
public class ExtTreeNode {
    /** * id. */
    private long id;

    /** * qtip. */
    private String qtip = "tooltip";

    /** * text. */
    private String text;

    /** * 可拖拽. */
    private boolean draggable;

    /** * 可编辑. */
    private boolean allowEdit;

    /** * 可删除. */
    private boolean allowDelete;

    /** * 是叶子. */
    private boolean leaf;

    /** * css的class属性. */
    private String cls;

    /** * 允许有子节点. */
    private boolean allowChildren;

    /** * @return qtip. */
    public String getQtip() {
        return qtip;
    }

    /** * @param qtip String. */
    public void setQtip(String qtip) {
        this.qtip = qtip;
    }

    /** * @return id. */
    public long getId() {
        return id;
    }

    /** * @param id long. */
    public void setId(long id) {
        this.id = id;
    }

    /** * @return text. */
    public String getText() {
        return text;
    }

    /** * @param text String. */
    public void setText(String text) {
        this.text = text;
    }

    /** * @return leaf. */
    public boolean getLeaf() {
        return leaf;
    }

    /** * @param leaf boolean. */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /** * @return cls. */
    public String getCls() {
        return cls;
    }

    /** * @param cls String. */
    public void setCls(String cls) {
        this.cls = cls;
    }

    /** * @return draggable. */
    public boolean getDraggable() {
        return draggable;
    }

    /** * @param draggable boolean. */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    /** * @return allowEdit. */
    public boolean getAllowEdit() {
        return allowEdit;
    }

    /** * @param allowEdit boolean. */
    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    /** * @return allowDelete. */
    public boolean getAllowDelete() {
        return allowDelete;
    }

    /** * @param allowDelete boolean. */
    public void setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    /** * @return allowChildren. */
    public boolean getAllowChildren() {
        return allowChildren;
    }

    /** * @param allowChildren boolean. */
    public void setAllowChildren(boolean allowChildren) {
        this.allowChildren = allowChildren;
    }

    /**
    * 把category转换成extjs树形需要的格式.
    *
    * @param category 分类实体类
    * @return ExtTreeNode
    */
    public static ExtTreeNode fromCategory(NewsCategory category) {
        ExtTreeNode node = new ExtTreeNode();
        node.setId(category.getId());
        node.setText(category.getName());
        //node.setLeaf(category.isLeaf());

        //
        node.setAllowEdit(true);
        node.setDraggable(true);
        node.setAllowDelete(true);
        node.setAllowChildren(true);

        return node;
    }

    /**
     * 把categoryList转换成JSON，写入writer.
     *
     * @param writer 需要写入的writer
     * @param list 目录列表
     * @throws Exception 可能抛出JSON异常
     */
    public static void write(Writer writer, List<NewsCategory> list)
        throws Exception {
        List<ExtTreeNode> extTreeNodeList = new ArrayList<ExtTreeNode>();

        for (NewsCategory category : list) {
            extTreeNodeList.add(ExtTreeNode.fromCategory(category));
        }

        JSON json = JSONSerializer.toJSON(extTreeNodeList);
        json.write(writer);
    }
}
