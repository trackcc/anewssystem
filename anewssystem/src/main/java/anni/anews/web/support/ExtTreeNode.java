package anni.anews.web.support;

import java.io.Writer;

import java.util.ArrayList;
import java.util.List;

import anni.anews.domain.Category;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;


/**
 * @author Lingo.
 * @since 2007年08月08日 下午 19时19分12秒781
 */
public class ExtTreeNode {
    /** * id. */
    private long id;

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

    /** * @param leaf boolean. */
    public boolean getLeaf() {
        return leaf;
    }

    /** * @return leaf. */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /** * @param cls String. */
    public String getCls() {
        return cls;
    }

    /** * @return cls. */
    public void setCls(String cls) {
        this.cls = cls;
    }

    /** * @param draggable boolean. */
    public boolean getDraggable() {
        return draggable;
    }

    /** * @return draggable. */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    /** * @param allowEdit boolean. */
    public boolean getAllowEdit() {
        return allowEdit;
    }

    /** * @return allowEdit. */
    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    /** * @param allowDelete boolean. */
    public boolean getAllowDelete() {
        return allowDelete;
    }

    /** * @return allowDelete. */
    public void setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    /** * @param allowChildren boolean. */
    public boolean getAllowChildren() {
        return allowChildren;
    }

    /** * @return allowChildren. */
    public void setAllowChildren(boolean allowChildren) {
        this.allowChildren = allowChildren;
    }

    /**
    * 把category转换成extjs树形需要的格式.
    *
    * @param category 分类实体类
    * @return ExtTreeNode
    */
    public static ExtTreeNode fromCategory(Category category) {
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
     */
    public static void write(Writer writer, List<Category> list)
        throws Exception {
        List<ExtTreeNode> extTreeNodeList = new ArrayList<ExtTreeNode>();

        for (Category category : list) {
            extTreeNodeList.add(ExtTreeNode.fromCategory(category));
        }

        JSON json = JSONSerializer.toJSON(extTreeNodeList);
        json.write(writer);
    }
}
