package anni.asecurity.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import anni.core.tree.LongTreeNode;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


/**
 * Region generated by Lingo.
 *
 * @author Lingo
 * @since 2007年10月24日
 */
@Entity
@Table(name = "A_SECURITY_REGION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Region extends LongTreeNode<Region> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * parent. */
    private Region parent;

    /** * name. */
    private String name;

    /** * theSort. */
    private Integer theSort;

    /** * descn. */
    private String descn;

    /** * children. */
    private Set<Region> children = new HashSet<Region>(0);

    /** * 构造方法. */
    public Region() {
    }

    /** * @return id. */
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /** * @param id id. */
    public void setId(Long id) {
        this.id = id;
    }

    /** * @return parent. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    public Region getParent() {
        return parent;
    }

    /** * @param parent Region. */
    public void setParent(Region parent) {
        this.parent = parent;

        if (parent != null) {
            setParentId(parent.getId());
        } else {
            setParentId(null);
        }
    }

    /** * @return name. */
    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    /** * @param name name. */
    public void setName(String name) {
        this.name = name;
    }

    /** * @return theSort. */
    @Column(name = "THE_SORT")
    public Integer getTheSort() {
        return theSort;
    }

    /** * @param theSort Integer. */
    public void setTheSort(Integer theSort) {
        this.theSort = theSort;
    }

    /** * @return descn. */
    @Column(name = "DESCN", length = 200)
    public String getDescn() {
        return descn;
    }

    /** * @param descn descn. */
    public void setDescn(String descn) {
        this.descn = descn;
    }

    /** * @return children. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    public Set<Region> getChildren() {
        return children;
    }

    /** * @param children Set. */
    public void setChildren(Set<Region> children) {
        this.children = children;
    }
}