package anni.core.mock.db.hibernate;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anni.core.dao.support.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import org.hibernate.classic.Session;


public class SessionFactoryProxy implements InvocationHandler {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(SessionFactoryProxy.class);
    private SessionFactory sessionFactory;

    /**
     *
     */
    public static Object getInstance(Class clz) {
        SessionFactoryProxy proxy = new SessionFactoryProxy();
        proxy.sessionFactory = (SessionFactory) Proxy.newProxyInstance(clz
                .getClassLoader(), new Class[] {clz}, proxy);

        return proxy.sessionFactory;
    }

    /**
     *
     */
    public Object invoke(Object proxy, Method m, Object[] args)
        throws Throwable {
        logger.info(m);

        Class clz = m.getReturnType();

        String methodName = m.getName();

        if (methodName.equals("openSession")) {
            return (Session) SessionProxy.getInstance(Session.class,
                sessionFactory);
        } else if (methodName.equals("toString")) {
            return "_";
        }

        return ProxyHelper.generateReturn(proxy, m, args);
    }
}
