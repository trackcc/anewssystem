package anni.core.domain;

import java.io.Serializable;

import java.util.Set;


/**
 * 应用于只有id和name两个字段，并自身关联的无级树型字典表.
 *
 * 实际上树节点只能与本身关联，无论是children和parent都应该是TreeEntityNode或者其子类
 * getName()返回的是节点名称，但这里的节点名称在不同级别的情况下是可以重复的。
 * 不能重复的应该是getPath()整个路径不能重复
 *
 * getParent()获得上级节点
 * getChildren()获得所有下级子节点
 *
 * @author Lingo
 * @since 2007-06-06
 * @param <T> 本身子表关联，T代表的就是本身的类型
 */
public interface TreeEntityBean<T extends TreeEntityBean>
    extends Serializable {
    /**
     * 获得上级节点.
     *
     * @return TreeEntityBean 上级节点
     */
    T getParent();

    /**
     * 设置上级节点.
     *
     * @param parentIn 上级节点
     */
    void setParent(T parentIn);

    /**
     * 获得所有下级节点.
     *
     * @return Set 获得所有下级子节点
     */
    Set<T> getChildren();

    /**
     * 设置所有下级节点.
     *
     * @param childrenIn 下级子节点
     */
    void setChildren(Set<T> childrenIn);

    /**
     * 是否为根节点.
     * 如果getParent() == null就是根节点
     *
     * @return boolean
     */
    boolean isRoot();

    /**
     * 是否为叶子节点.
     * 如果getChildren().size() == 0就是叶子节点
     *
     * @return boolean
     */
    boolean isLeaf();

    /**
     * 不允许将自表外键关系设置成环状.
     * 就是说，当前的current的子节点中，如果包含parent，就不能把parent设置为current的上级节点
     *
     * @param parent 上级节点
     * @return boolean 是否形成环状
     */
    boolean checkDeadLock(T parent);
}
