package anni.anews.manager;

import java.util.List;

import anni.anews.domain.News;
import anni.anews.domain.NewsCategory;

import anni.core.dao.ECHibernateEntityDao;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsManager extends ECHibernateEntityDao<News> {
    /**
     * 获得分类，已经分类的所有子分类下的所有新闻.
     *
     * @param newsCateogry 新闻分类
     * @return List
     */
    public List<News> getAllChildren(NewsCategory newsCategory) {
        long code = newsCategory.getCode();
        long base = 1L << (8 * (8 - newsCategory.getLevel()));
        List<News> newsList = find("from News where newsCategory.code>? and newsCategory.code<?",
                code - 1, (code + base));

        return newsList;
    }
}
