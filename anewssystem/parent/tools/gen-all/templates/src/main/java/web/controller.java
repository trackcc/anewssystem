package ${pkg}.web;

import ${pkg}.manager.${pojo}Manager;
import ${pkg}.domain.${pojo};
import anni.core.web.prototype.BaseLongController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since ${now?string("yyyy年MM月dd日 a HH时mm分ss秒S")}
 */
public class ${pojo}Controller extends BaseLongController<${pojo}, ${pojo}Manager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(${pojo}Controller.class);
    /** * constructor. */
    public ${pojo}Controller() {
        setEditView("/${projectName}/${pojo?lower_case}/edit${pojo}");
        setListView("/${projectName}/${pojo?lower_case}/list${pojo}");
    }
}
