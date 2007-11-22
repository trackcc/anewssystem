package anni.core.page;

import java.io.Serializable;

import java.util.ArrayList;


/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 * 来自www.springside.org.cn
 *
 * <p>使用构造方法Page(long start, long totalCount, int pageSize, Object data)，
 * 设置开始记录的索引位置，记录总数，每页显示记录数，和当前页包含的数据（一般是List）</p>
 *
 * <p>使用的时候，<ul>
 * <li>${page.totalCount}获得一共有多少条记录</li>
 * <li>${page.totalPageCount}获得一共有多少页</li>
 * <li>${page.currentPageNo}获得目前是第几页</li></ul></p>
 *
 * <p>感觉其他的方法，如hasNextPage()是否有上一页，hasPreviousPage()是否有下一页，用的比较少。
 * getStartOfPage()是静态方法，可以计算当前页的第一条记录，在所有记录中排第几。</p>
 *
 * @author ajax
 * @author calvin
 * @since 2007-03-30
 */
public class Page implements Serializable {
    /** * 持久化. */
    private static final long serialVersionUID = -1L;

    /** * 默认分页数. */
    public static final int DEFAULT_PAGE_SIZE = 15;

    /** * 每页的记录数. */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /** * 当前页第一条数据在List中的位置,从0开始. */
    private long start;

    /** * 当前页中存放的记录,类型一般为List. */
    private Object data;

    /** * 总记录数. */
    private long totalCount;

    /** * 构造方法，只构造空页. */
    public Page() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<Object>());
    }

    /**
     * 默认构造方法.
     *
     * @param start      本页数据在数据库中的起始位置
     * @param totalCount 数据库中总记录条数
     * @param pageSize   本页容量
     * @param data       本页包含的数据
     */
    public Page(long start, long totalCount, int pageSize, Object data) {
        this.pageSize = pageSize;
        this.start = start;
        this.totalCount = totalCount;
        this.data = data;
    }

    /**
     * 返回数据库里一同有几条记录.
     * （可能不是数据库呢，不过这里基本都是数据库）
     *
     * @return long 总记录数
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 计算一共会分几页.
     *
     * @return long 总页数
     */
    public long getTotalPageCount() {
        if ((totalCount % pageSize) == 0) {
            return totalCount / pageSize;
        } else {
            return (totalCount / pageSize) + 1;
        }
    }

    /**
     * 返回每页会有几条记录.
     *
     * @return int 每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 当前页包含的记录，一般是List.
     *
     * @return Object 本页包含的数据
     */
    public Object getResult() {
        return data;
    }

    /**
     * 目前是第几页.
     * 根据本页第一条记录和每页记录数计算出来
     *
     * @return long 当前页码，从1开始
     */
    public long getCurrentPageNo() {
        return (start / pageSize) + 1;
    }

    /**
     * 是否有下一页.
     *
     * @return boolean 是否有下一页
     */
    public boolean hasNextPage() {
        return this.getCurrentPageNo() < this.getTotalPageCount();
    }

    /**
     * 是否有上一页.
     *
     * @return boolean 是否有上一页
     */
    public boolean hasPreviousPage() {
        return this.getCurrentPageNo() > 1;
    }

    /**
     * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
     *
     * @see #getStartOfPage(int,int)
     * @param pageNo 页码
     * @return int 第一条记录的位置
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据在数据集的位置.
     *
     * @param pageNo   从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }
}
