package anni.core.web.listener;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 监听用户是否在线的监听器.
 * <p>用户登录时：session.setAttribute("onlineListenter", new OnlineListener);就会触发valueBound()方法
 * 如果session里有什么需要在valueBound()方法里处理的，需要在之前set到session里去。</p>
 * <p>执行session.removeAttribute("onlineListener");session.invalidate()或者session超时失效，都会触发valueUnbound()方法
 * 如果session里有什么需要在valueUnbound()方法里处理的，需要在之前set到session里去。</p>
 *
 * @author Lingo
 */
public class OnlineListener implements HttpSessionBindingListener {
    /** * 日志. */
    private static Log logger = LogFactory.getLog(OnlineListener.class);

    /** * 线程同步锁. */
    private static final Object LOCK = new Object();

    /**
     * setAttribute时触发的事件.
     * 将当前用户添加到在线用户列表中
     *
     * @param event 绑定事件
     */
    public void valueBound(HttpSessionBindingEvent event) {
        synchronized (LOCK) {
            HttpSession session = event.getSession();
            log(session);

            Map onlineList = getOnlineList(session);
            onlineList.put(session, new Date());
        }
    }

    /**
     * setAttribute时触发的事件.
     * 从在线用户列表中删除当前用户
     *
     * @param event 绑定事件
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        synchronized (LOCK) {
            HttpSession session = event.getSession();
            log(session);

            Map onlineList = getOnlineList(session);
            onlineList.remove(session);
        }
    }

    /**
     * 从ServletContext里获得保存在线列表的Map.
     *
     * @param session 当前会话
     * @return online list
     */
    private Map getOnlineList(HttpSession session) {
        ServletContext application = session.getServletContext();
        Map onlineList = (Map) application.getAttribute("ONLINE_LIST");

        if (onlineList == null) {
            onlineList = new HashMap();
            application.setAttribute("ONLINE_LIST", onlineList);
        }

        return onlineList;
    }

    /**
     * 打印session里所有的属性作为日志信息.
     *
     * @param session HttpSession
     */
    private void log(HttpSession session) {
        for (Enumeration e = session.getAttributeNames();
                e.hasMoreElements();) {
            String attrName = (String) e.nextElement();
            Object attrValue = session.getAttribute(attrName);
            logger.info("属性名：" + attrName + "，属性值：" + attrValue);
        }

        logger.info("创建时间：" + new Date(session.getCreationTime()));
        logger.info("最后访问时间：" + new Date(session.getLastAccessedTime()));
        logger.info("最大等待时间：" + session.getMaxInactiveInterval());
    }
}
