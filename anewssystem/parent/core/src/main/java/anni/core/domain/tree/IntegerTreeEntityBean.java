package anni.core.domain.tree;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import anni.core.domain.IntegerNamedEntityBean;


/**
 * id是Integer的Tree类实现.
 *
 * @author Lingo
 * @since 2007-06-06
 * @param <T> 本身子表关联，T代表的就是本身的类型
 */
@MappedSuperclass
public class IntegerTreeEntityBean<T extends IntegerTreeEntityBean>
    extends IntegerNamedEntityBean implements TreeEntityBean<T> {
    /** * parent. */
    protected T parent = null;

    /** * children. */
    protected Set<T> children = new HashSet<T>(0);

    /**
     * @return parent.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    public T getParent() {
        return parent;
    }

    /**
     * @param parentIn LongTreeEntityBean.
     */
    public void setParent(T parentIn) {
        parent = parentIn;
    }

    /**
     * @return children.
     */
    @OneToMany(cascade =  {
        CascadeType.PERSIST, CascadeType.MERGE}
    , fetch = FetchType.LAZY, mappedBy = "parent")
    @OrderBy("id")
    public Set<T> getChildren() {
        return children;
    }

    /**
     * @param childrenIn LongTreeEntityBeanSet.
     */
    public void setChildren(Set<T> childrenIn) {
        children = childrenIn;
    }

    /**
     * @return 是否为根节点.
     */
    @Transient
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * @return 是否为叶子节点.
     */
    @Transient
    public boolean isLeaf() {
        return (children == null) || (children.size() == 0);
    }

    /**
     * 不允许将自表外键关系设置成环状.
     * 就是说，当前bean的子节点中，如果包含entityBean，就不能把entityBean设置为当前bean的上级节点
     *
     * @param entityBean TreeEntityBean
     * @return boolean 是否形成环状
     */
    @Transient
    public boolean checkDeadLock(T entityBean) {
        return TreeHelper.checkDeadLock(this, entityBean);
    }
}
