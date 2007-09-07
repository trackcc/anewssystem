package anni.core.domain.tree;


/**
 * 抽象Tree实体.
 * 提供几个通用的工具方法，并且与javax.persistence无关
 *
 * @author Lingo
 * @since 2007-06-17
 * @param <T> AbstractTreeEntityBean
 */
public abstract class AbstractTreeEntityBean<T extends AbstractTreeEntityBean<T>>
    implements TreeEntityBean<T> {
    /**
     * @return 是否为根节点.
     */
    public boolean isRoot() {
        return this.getParent() == null;
    }

    /**
     * @return 是否为叶子节点.
     */
    public boolean isLeaf() {
        return (this.getChildren() == null)
        || (this.getChildren().size() == 0);
    }

    /**
     * 不允许将自表外键关系设置成环状.
     * 就是说，当前的current的子节点中，如果包含parent，就不能把parent设置为current的上级节点
     *
     * @param parent TreeEntityBean
     * @return boolean 是否形成环状
     */
    public boolean checkDeadLock(T parent) {
        return TreeHelper.checkDeadLock(this, parent);
    }
}
