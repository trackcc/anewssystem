package anni.anews.manager;

import anni.anews.domain.NewsCategory;
import anni.anews.domain.NewsConfig;

import anni.core.dao.ECHibernateEntityDao;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsConfigManager extends ECHibernateEntityDao<NewsConfig> {
    /** * @return NewsConfig. */
    public NewsConfig getDefaultConfig() {
        NewsConfig newsConfig = get(NewsConfig.DEFAULT_CONFIG_ID);

        if (newsConfig == null) {
            newsConfig = new NewsConfig();
            // newsConfig.setId(NewsConfig.DEFAULT_CONFIG_ID);
            newsConfig.setNewsNeedAudit(0);
            newsConfig.setCommentNeedAudit(0);
            newsConfig.setCouldComment(0);
            newsConfig.setCategoryStrategy(NewsCategory.STRATEGY_BIT_CODE);
            newsConfig.setTemplateName(NewsConfig.DEFAULT_TEMPLATE_NAME);
            save(newsConfig);
        }

        return newsConfig;
    }
}
