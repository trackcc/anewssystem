package anni.core.domain.tree;

import java.util.Set;

import anni.core.domain.NamedEntityBean;


/**
 * 应用于只有id和name两个字段，并自身关联的无级树型字典表.
 *
 * 实际中使用时，实现类需要使用导入javax.persistence.Column
 * 并对name字段进行如下注释
 * <pre>@Column(unique = true, nullable = false)</pre>
 * 我们考虑使用NamedEntityBean的情况，很可能是除了id就只有name字段的情况
 * 这样，name重复，或者为空，就没有实际意义了，所以在进行这样的解释
 *
 * getParent()获得上级节点
 * getChildren()获得所有下级子节点
 *
 * @author Lingo
 * @since 2007-06-06
 * @param <T> 本身子表关联，T代表的就是本身的类型
 */
public interface TreeEntityBean<T extends TreeEntityBean>
    extends NamedEntityBean {
    /**
     * @return TreeEntityBean parent.
     */
    T getParent();

    /**
     * @param parentIn 上级节点.
     */
    void setParent(T parentIn);

    /**
     * @return Set TreeEntityBeanSet.
     */
    Set<T> getChildren();

    /**
     * @param childrenIn 下级子节点.
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
     * @param parent TreeEntityBean
     * @return boolean 是否形成环状
     */
    public boolean checkDeadLock(T parent);
}
