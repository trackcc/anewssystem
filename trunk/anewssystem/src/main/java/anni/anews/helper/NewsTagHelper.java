package anni.anews.helper;

import java.util.List;
import java.util.Map;

import anni.anews.manager.NewsTagManager;

import anni.core.dao.support.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo
 * @since 2007-08-31
 */
public class NewsTagHelper {
    /** * logger. */
    private Log logger = LogFactory.getLog(NewsTagHelper.class);

    /** * newsTagManager. */
    private NewsTagManager newsTagManager = null;

    /** * @param newsTagManager NewsTagManager. */
    public void setNewsTagManager(NewsTagManager newsTagManager) {
        this.newsTagManager = newsTagManager;
    }

    /**
     * 分页查询关键字.
     *
     * @param conditions Map
     * @return page
     */
    public Page getItems(Map conditions) {
        logger.info(conditions);

        int start = 0;
        int pageSize = 10;
        int pageNo = (start / pageSize) + 1;

        try {
            start = Integer.parseInt(conditions.get("start").toString());
            pageSize = Integer.parseInt(conditions.get("limit").toString());
            pageNo = (start / pageSize) + 1;
        } catch (Exception ex) {
            logger.info(ex);
        }

        return newsTagManager.pagedQuery("from NewsTag", pageNo, pageSize);
    }

    /** * @param id long. */
    public void removeRow(long id) {
        logger.info(id);
        newsTagManager.removeById(id);
    }

    /** * @param list List. */
    public void removeAll(List<Long> list) {
        logger.info(list);

        for (Long id : list) {
            newsTagManager.removeById(id);
        }
    }

    /** * @param list List. */
    public void save(List<String> list) {
        logger.info(list);

        for (String name : list) {
            newsTagManager.createOrGet(name);
        }
    }
}
