package anni.aerp.web;

import anni.core.json.JsonController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 求购管理系统的主控制器.
 * @author Lingo
 * @since 2007-11-02
 */
public class ErpController extends JsonController {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(ErpController.class);

    /**
     * 登录成功后，系统主页.
     */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp/erp/index");
    }

    /**
     * 登录页面.
     */
    public void login() {
        logger.info("start");
        mv.setViewName("aerp/erp/login");
    }

    /**
     * 用户登录后，右侧默认显示的欢迎页面.
     */
    public void welcome() {
        logger.info("start");
        mv.setViewName("aerp/erp/welcome");
    }
}
