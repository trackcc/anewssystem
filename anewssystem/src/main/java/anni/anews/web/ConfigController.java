package anni.anews.web;

import javax.servlet.http.HttpServletRequest;

import anni.anews.domain.Config;

import anni.anews.manager.ConfigManager;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * @author Lingo.
 * @since 2007年08月08日 下午 18时10分12秒781
 */
public class ConfigController extends BaseLongController<Config, ConfigManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(ConfigController.class);

    /** * constructor. */
    public ConfigController() {
        setEditView("config/manage");
        setSuccessView("redirect:/config/manage.htm");
    }

    /**
     * @param requestIn HttpServletRequest.
     * @param command Object
     * @param binder ServletRequestDataBinder
     */
    @Override
    protected void preBind(HttpServletRequest requestIn, Object command,
        ServletRequestDataBinder binder) throws Exception {
        //
        Config config = (Config) command;
        config.setCommentNeedAudit(0);
        config.setNewsNeedAudit(0);
        config.setCouldComment(0);
    }

    /** * manage. */
    public void manage() {
        logger.info("start");

        long id = 1L;
        Config config = getEntityDao().get(id);

        if (config == null) {
            config = new Config();
            config.setCommentNeedAudit(0);
            config.setNewsNeedAudit(0);
            config.setCouldComment(0);
            getEntityDao().save(config);
        }

        mv.addObject("config", config);
    }
}
