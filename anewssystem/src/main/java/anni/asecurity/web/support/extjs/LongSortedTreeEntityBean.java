package anni.asecurity.web.support.extjs;

import java.util.Set;

import anni.core.domain.tree.TreeEntityBean;


/** * 与extjs结合，主键为long的可排序树. */
public interface LongSortedTreeEntityBean<T extends LongSortedTreeEntityBean>
    extends TreeEntityBean<T> {
    /** * @return id. */
    Long getId();

    /** * @param id Long. */
    void setId(Long id);

    /** * @return theSort. */
    Integer getTheSort();

    /** * @param theSort Integer. */
    void setTheSort(Integer theSort);
}
