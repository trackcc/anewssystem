package anni.asecurity.web.support.extjs;

import anni.core.domain.tree.TreeEntityBean;


/**
 * 与extjs结合，主键为long的可排序树.
 *
 * @author Lingo
 * @since 2007-09-09
 * @param <T> LongSortedTreeEntityBean的子类
 */
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
