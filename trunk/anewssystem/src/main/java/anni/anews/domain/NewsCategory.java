package anni.anews.domain;

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
import javax.persistence.Transient;

import anni.core.tree.LongTreeNode;

/*
 *import org.compass.annotations.Searchable;
 *import org.compass.annotations.SearchableProperty;
 */
import org.hibernate.annotations.GenericGenerator;


/**
 * @authtor Lingo.
 * @since 2007-08-22
 */
@Entity
//@Searchable(root = false, alias = "a_news_category")
@Table(name = "A_NEWS_CATEGORY")
public class NewsCategory extends LongTreeNode<NewsCategory> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * 位编码策略. */
    public static final int STRATEGY_BIT_CODE = 0;

    /** * 字符编码策略. */
    public static final int STRATEGY_CHAR_CODE = 1;

    /** * 递归策略. */
    public static final int STRATEGY_RECURSION = 2;

    /** * id. */
    private Long id;

    /** * parent. */
    private NewsCategory parent;

    /** * name. */
    //@SearchableProperty(name = "categoryname")
    private String name;

    /** * theSort. */
    private Integer theSort;

    /** * status. */
    private Integer status;

    /** * children. */
    private Set<NewsCategory> children = new HashSet<NewsCategory>(0);

    /** * newses. */
    private Set<News> newses = new HashSet<News>(0);

    /** * bitCode. */
    private Long bitCode;

    /** * charCode. */
    private String charCode;

    /** * 构造方法. */
    public NewsCategory() {
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

    /** * @return NewsCategory. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    public NewsCategory getParent() {
        return parent;
    }

    /** * @param parent NewsCategory. */
    public void setParent(NewsCategory parent) {
        this.parent = parent;
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

    /** * @param theSort theSort. */
    public void setTheSort(Integer theSort) {
        this.theSort = theSort;
    }

    /** * @return status. */
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    /** * @param status status. */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** * @return Set. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    public Set<NewsCategory> getChildren() {
        return children;
    }

    /** * @param children Set. */
    public void setChildren(Set<NewsCategory> children) {
        this.children = children;
    }

    /** * @return newses. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "newsCategory")
    public Set<News> getNewses() {
        return newses;
    }

    /** * @param newses newses. */
    public void setNewses(Set<News> newses) {
        this.newses = newses;
    }

    // ====================================================
    // 编码
    // ====================================================

    /** * @return bitCode. */
    @Column(name = "BIT_CODE")
    public Long getBitCode() {
        return bitCode;
    }

    /** * @param bitCode Long. */
    public void setBitCode(Long bitCode) {
        this.bitCode = bitCode;
    }

    /** * @return charCode. */
    @Column(name = "CHAR_CODE")
    public String getCharCode() {
        return charCode;
    }

    /** * @param charCode String. */
    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    /**
     * 分类深度.
     *
     * @return int
     */
    @Transient
    public int getLevel() {
        return getLevelByBitCode();
    }

    /**
     * 根据位编码计算深度.
     *
     * @return int
     */
    @Transient
    public int getLevelByBitCode() {
        if (bitCode == null) {
            if (parent == null) {
                return 1;
            } else {
                return parent.getLevel() + 1;
            }
        } else {
            for (int i = 7; i > 0; i--) {
                long base = 1L << (8 * i);

                if ((bitCode % base) == 0) {
                    return 8 - i;
                }
            }

            return 8;
        }
    }

    /**
     * 根据字符编码计算深度.
     *
     * @return int
     */
    @Transient
    public int getLevelByCharCode() {
        if (charCode == null) {
            if (parent == null) {
                return 1;
            } else {
                return parent.getLevel() + 1;
            }
        } else {
            return charCode.length() / 2;
        }
    }

    /**
     * 使用递归计算深度.
     *
     * @return int
     */
    @Transient
    public int getLevelByRecursion() {
        int level = 1;
        NewsCategory upper = parent;

        while (upper != null) {
            upper = upper.getParent();
            level++;
        }

        return level;
    }
}
