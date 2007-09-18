package anni.anews.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import anni.anews.domain.News;
import anni.anews.domain.NewsCategory;
import anni.anews.domain.NewsConfig;

import anni.core.tree.LongTreeHibernateDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 采用编码方式保存无级分类的信息.
 *
 * 位编码
 * 字符编码
 * 无编码
 *
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsCategoryManager extends LongTreeHibernateDao<NewsCategory> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsCategoryManager.class);

    /** * newsConfigManager. */
    private NewsConfigManager newsConfigManager = null;

    /** * @param newsConfigManager NewsConfigManager. */
    public void setNewsConfigManager(NewsConfigManager newsConfigManager) {
        this.newsConfigManager = newsConfigManager;
    }

    /**
     * 获得分类策略.
     *
     * @return strategy
     */
    private int getCategoryStrategy() {
        NewsConfig newsConfig = newsConfigManager.getDefaultConfig();

        return newsConfig.getCategoryStrategy();
    }

    /**
     * 保存.
     *
     * @param o 实体类
     */
    @Override
    public void save(Object o) {
        if (o instanceof NewsCategory) {
            NewsCategory entity = (NewsCategory) o;
            this.save(entity, getCategoryStrategy());
        } else {
            super.save(o);
        }
    }

    /**
     * 保存.
     *
     * @param entity 实体类
     * @param strategy 分类策略
     */
    public void save(NewsCategory entity, int strategy) {
        if (strategy == NewsCategory.STRATEGY_BIT_CODE) {
            calculateByBitCode(entity);
        } else if (strategy == NewsCategory.STRATEGY_CHAR_CODE) {
            calculateByCharCode(entity);

            //} else if (strategy == NewsCategory.STRATEGY_RECURSION) {
            // do nothing
        } else {
            // 默认使用位编码
            calculateByBitCode(entity);
        }

        super.save(entity);
    }

    /**
     * 计算位编码.
     *
     * @param entity NewsCategory
     */
    private void calculateByBitCode(NewsCategory entity) {
        NewsCategory parent = entity.getParent();
        String hql = null;

        // 每次都取上次最大的code值，如果删除了几个中间值，肯定会出现还有空位，可能出现最大数据溢出的问题
        if (parent == null) {
            if (entity.getId() == null) {
                hql = "select bitCode from NewsCategory where parent is null";
            } else {
                hql = "select bitCode from NewsCategory where parent is null and id<>"
                    + entity.getId();
            }
        } else {
            if (entity.getId() == null) {
                hql = "select bitCode from NewsCategory where parent.id="
                    + parent.getId();
            } else {
                hql = "select bitCode from NewsCategory where parent.id="
                    + parent.getId() + " and id<>" + entity.getId();
            }
        }

        List<Long> list = (List<Long>) createQuery(hql).list();
        long base;

        if (parent == null) {
            base = 1L << 56;
        } else {
            base = 1L << (56 - (8 * parent.getLevel()));
        }

        long code = -1L;

        for (int i = 1; i <= 256; i++) {
            if (parent == null) {
                code = base * i;
            } else {
                code = parent.getBitCode() + (base * i);
            }

            if (list.contains(code)) {
                code = -1;
            } else {
                break;
            }
        }

        if (code == -1L) {
            logger.info("超过分类最大允许数量(256)");
        } else {
            entity.setBitCode(code);
        }
    }

    /**
     * 计算字符编码.
     *
     * @param entity NewsCategory
     */
    private void calculateByCharCode(NewsCategory entity) {
        NewsCategory parent = entity.getParent();
        String hql = null;

        // 每次都取上次最大的code值，如果删除了几个中间值，肯定会出现还有空位，可能出现最大数据溢出的问题
        if (parent == null) {
            if (entity.getId() == null) {
                hql = "select charCode from NewsCategory where parent is null";
            } else {
                hql = "select charCode from NewsCategory where parent is null and id<>"
                    + entity.getId();
            }
        } else {
            if (entity.getId() == null) {
                hql = "select charCode from NewsCategory where parent.id="
                    + parent.getId();
            } else {
                hql = "select charCode from NewsCategory where parent.id="
                    + parent.getId() + " and id<>" + entity.getId();
            }
        }

        List<String> list = (List<String>) createQuery(hql).list();

        String code = null;

        for (int i = 1; i < 100; i++) {
            if (parent == null) {
                if (i < 10) {
                    code = "0" + i;
                } else {
                    code = "" + i;
                }
            } else {
                if (i < 10) {
                    code = parent.getCharCode() + "0" + i;
                } else {
                    code = parent.getCharCode() + i;
                }
            }

            if (list.contains(code)) {
                code = null;
            } else {
                break;
            }
        }

        if (code == null) {
            logger.info("超过分类最大允许数量(99)");
        } else {
            entity.setCharCode(code);
        }
    }

    /**
     * 获得分类，以及分类的所有子分类下的所有新闻.
     *
     * @param newsCategory 新闻分类
     * @return List
     */
    public List<News> getAllNews(NewsCategory newsCategory) {
        return this.getAllNews(newsCategory, getCategoryStrategy());
    }

    /**
     * 根据编码策略，获得所有新闻.
     *
     * @param newsCategory 新闻分类
     * @param strategy 策略
     * @return List
     */
    public List<News> getAllNews(NewsCategory newsCategory, int strategy) {
        if (strategy == NewsCategory.STRATEGY_BIT_CODE) {
            return getAllNewsByBitCode(newsCategory);
        } else if (strategy == NewsCategory.STRATEGY_CHAR_CODE) {
            return getAllNewsByCharCode(newsCategory);
        } else if (strategy == NewsCategory.STRATEGY_RECURSION) {
            return getAllNewsByRecursion(newsCategory);
        } else {
            // 默认使用位编码
            return getAllNewsByBitCode(newsCategory);
        }
    }

    /**
     * 根据位编码，获得查询的队列.
     *
     * @param newsCategory NewsCategory
     * @return List
     */
    private List<News> getAllNewsByBitCode(NewsCategory newsCategory) {
        long code = newsCategory.getBitCode();
        long base = 1L << (8 * (8 - newsCategory.getLevel()));
        List<News> newsList = find("from News where newsCategory.code>? and newsCategory.code<?",
                code - 1, (code + base));

        return newsList;
    }

    /**
     * 根据字符编码，获得查询的队列.
     *
     * @param newsCategory NewsCategory
     * @return List
     */
    private List<News> getAllNewsByCharCode(NewsCategory newsCategory) {
        String code = newsCategory.getCharCode();
        List<News> newsList = find("from News where newsCategory.code like ?",
                code + "%");

        return newsList;
    }

    /**
     * 使用递归方式，获得查询的队列.
     *
     * @param newsCategory NewsCategory
     * @return List
     */
    private List<News> getAllNewsByRecursion(NewsCategory newsCategory) {
        Set<Long> idList = new HashSet<Long>();
        getIdList(newsCategory, idList);

        List<News> newsList = find("from News where newsCategory.id in ("
                + idList.toString().substring(1, idList.size() - 1) + ")");

        return newsList;
    }

    /**
     * 递归获得所有子分类的列表.
     *
     * @param newsCategory 分类
     * @param idList 分类主键集合
     */
    private void getIdList(NewsCategory newsCategory, Set<Long> idList) {
        idList.add(newsCategory.getId());

        for (NewsCategory category : newsCategory.getChildren()) {
            idList.add(category.getId());
            getIdList(category, idList);
        }
    }
}
