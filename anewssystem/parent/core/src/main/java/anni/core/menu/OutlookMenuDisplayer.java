package anni.core.menu;

import java.io.IOException;

import java.text.MessageFormat;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.navigator.displayer.AbstractMenuDisplayer;
import net.sf.navigator.displayer.MenuDisplayerMapping;
import net.sf.navigator.menu.MenuComponent;

import org.apache.commons.collections.FastHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 来自www.springside.org.cn.
 *
 * @author Lingo
 * @since 2007-03-18
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class OutlookMenuDisplayer extends AbstractMenuDisplayer {
    /**
     * 新菜单.
     */
    private static MessageFormat newMenuMessage = new MessageFormat(
            "var Menu{0} = new Array();");

    /**
     * 顶级菜单.
     */
    private static MessageFormat topMenuMessage = new MessageFormat(
            "Menu{0}[{1}] = new Array(''{2}'');");

    /**
     * 子菜单.
     */
    private static MessageFormat subMenuMessage = new MessageFormat(
            "Menu{0}[{1}] = new Array(''{2}'', ''{3}'', ''{4}'', ''{5}'');");

    /**
     * 脚本开始标记.
     */
    private static final String SCRIPT_START = "<script type=\"text/javascript\">\n";

    /**
     * 脚本结束标记.
     */
    private static final String SCRIPT_END = "\n</script>";

    /**
     * 日志.
     */
    protected Log logger = LogFactory.getLog(OutlookMenuDisplayer.class);

    /**
     * 顶级菜单map.
     */
    private Map topMenus = new FastHashMap();

    /**
     * 初始化.
     *
     * @param context PageContext
     * @param mapping MenuDisplayerMapping
     */
    public void init(PageContext context, MenuDisplayerMapping mapping) {
        super.init(context, mapping);

        try {
            out.print(SCRIPT_START);
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    /**
     * 结束.
     *
     * @param context PageContext
     */
    public void end(PageContext context) {
        topMenus.clear();

        try {
            out.print(SCRIPT_END);
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    /**
     * 显示.
     *
     * @param menu MenuComponent
     * @throws JspException jsp异常
     * @throws IOException io异常
     */
    public void display(MenuComponent menu)
        throws JspException, IOException {
        StringBuffer sb = new StringBuffer();
        buildMenuString(menu, sb, isAllowed(menu));
        out.println("\n" + sb);
    }

    /**
     * 创建菜单字符串.
     *
     * @param menu MenuComponent
     * @param sb StringBuffer
     * @param allowed 不知道啥意思
     */
    protected void buildMenuString(MenuComponent menu, StringBuffer sb,
        boolean allowed) {
        String[] args = getTopArgs(menu);
        String parent = getParentName(menu);

        //top menus
        if (StringUtils.isBlank(parent)) {
            sb.append(newMenuMessage.format(args) + "\n");
            sb.append(topMenuMessage.format(args) + "\n");
        }

        List subMenus = menu.getComponents();

        for (int i = 0; i < subMenus.size(); i++) {
            MenuComponent sub = (MenuComponent) subMenus.get(i);
            args = getSubArgs(sub, i + 1);
            sb.append(subMenuMessage.format(args) + "\n");
        }
    }

    /**
     * 获得子菜单的参数.
     *
     * @param menu MenuComponent
     * @param i 第几个参数
     * @return String[]
     */
    protected String[] getSubArgs(MenuComponent menu, int i) {
        String[] args = new String[6];

        String parent = getParentName(menu);
        Integer index = (Integer) topMenus.get(parent);
        args[0] = String.valueOf(index);
        args[1] = String.valueOf(i);
        args[2] = menu.getImage();
        //        args[3] = menu.getName();
        args[3] = menu.getTitle();

        if (menu.getUrl() == null) {
            args[4] = EMPTY;
        } else {
            args[4] = menu.getUrl();
        }

        args[5] = menu.getTarget();

        return args;
    }

    /**
     * 顶级菜单参数.
     *
     * @param menu MenuComponent
     * @return String[]
     */
    protected String[] getTopArgs(MenuComponent menu) {
        String[] args = new String[3];

        int size = topMenus.size();
        topMenus.put(menu.getName(), size + 1);
        // topMenus.put(menu.getTitle(), size + 1);
        args[0] = String.valueOf(size + 1);
        args[1] = "0";
        // args[2] = menu.getName();
        args[2] = menu.getTitle();

        return args;
    }

    /**
     * 获得上级菜单的名称.
     *
     * @param menu MenuCompoent
     * @return name`
     */
    protected String getParentName(MenuComponent menu) {
        String name = null;

        if (menu.getParent() == null) {
            name = "";
        } else {
            name = menu.getParent().getName();

            // name = menu.getParent().getTitle();
        }

        return name;
    }
}
