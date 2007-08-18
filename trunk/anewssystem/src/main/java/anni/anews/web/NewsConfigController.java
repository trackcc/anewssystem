package anni.anews.web;

import javax.servlet.http.HttpServletRequest;

import anni.anews.domain.NewsConfig;

import anni.anews.manager.NewsConfigManager;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsConfigController extends BaseLongController<NewsConfig, NewsConfigManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsConfigController.class);

    /** * constructor. */
    public NewsConfigController() {
        setEditView("/anews/newsconfig/manage");
        // setEditView("/anews/newsconfig/editNewsConfig");
        setListView("/anews/newsconfig/listNewsConfig");
        setSuccessView("redirect:/newsconfig/manage.htm");
    }

    /**
     * @param requestIn HttpServletRequest.
     * @param command Object
     * @param binder ServletRequestDataBinder
     *
     * @throws Exception 异常
     */
    @Override
    protected void preBind(HttpServletRequest requestIn, Object command,
        ServletRequestDataBinder binder) throws Exception {
        //
        NewsConfig config = (NewsConfig) command;
        config.setCommentNeedAudit(0);
        config.setNewsNeedAudit(0);
        config.setCouldComment(0);
    }

    /** * manage. */
    public void manage() {
        logger.info("start");

        long id = 1L;
        NewsConfig config = getEntityDao().get(id);

        if (config == null) {
            config = new NewsConfig();
            config.setCommentNeedAudit(0);
            config.setNewsNeedAudit(0);
            config.setCouldComment(0);
            getEntityDao().save(config);
        }

        mv.addObject("config", config);
        mv.setViewName("/anews/newsconfig/manage");
    }
}
