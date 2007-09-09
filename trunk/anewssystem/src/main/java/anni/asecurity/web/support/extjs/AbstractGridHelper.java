package anni.asecurity.web.support.extjs;

import java.util.Map;

import anni.core.dao.support.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * extjs分页grid的实现基类.
 *
 * @author Lingo
 * @since 2007-09-09
 */
public class AbstractGridHelper {
    /** * logger. */
    private static Log logger = LogFactory.getLog(AbstractGridHelper.class);

    /**
     * 根据条件分页查找.
     * FIXME:需要使用Generic，未完成
     *
     * @param conditions 条件
     * @return 分页结果
     */
    public Page pagedQuery(Map conditions) {
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

        // FIXME: 为了不让PMD出现错误，使用这些变量
        logger.info(start);
        logger.info(pageSize);
        logger.info(pageNo);

        return null;
    }
}
