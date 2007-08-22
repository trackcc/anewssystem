package anni.core.mock.db.hibernate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.*;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import org.hibernate.classic.Session;


public class SessionProxy implements InvocationHandler {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(SessionProxy.class);
    private SessionFactory sessionFactory;

    /**
     *
     */
    public static Object getInstance(Class clz,
        SessionFactory sessionFactory) {
        SessionProxy proxy = new SessionProxy();
        proxy.sessionFactory = sessionFactory;

        return Proxy.newProxyInstance(clz.getClassLoader(),
            new Class[] {clz}, proxy);
    }

    /**
     *
     */
    public Object invoke(Object proxy, Method m, Object[] args)
        throws Throwable {
        String methodName = m.getName();

        if (methodName.equals("getSessionFactory")) {
            return sessionFactory;
        } else if (methodName.equals("createQuery")) {
            String hql = (String) args[0];
            Query query = (Query) QueryProxy.getInstance(Query.class, hql);

            return query;
        } else if (methodName.equals("saveOrUpdate")) {
            Object object = args[0];
            Map valueMap = MockFactoryBean.domainMap.get(object.getClass());

            if (valueMap != null) {
                Method method = object.getClass()
                                      .getDeclaredMethod("getId",
                        new Class[0]);
                Object obj = method.invoke(object, new Object[0]);
                Long id = (Long) obj;

                if (obj == null) {
                    id = System.currentTimeMillis();

                    Method[] methods = object.getClass()
                                             .getDeclaredMethods();

                    for (Method tempMethod : methods) {
                        if (tempMethod.getName().equals("setId")) {
                            method = tempMethod;
                        }
                    }

                    Object[] objects = new Object[] {id};
                    method.invoke(object, objects);
                }

                valueMap.put(id, object);
            } else {
                logger.error(object);
            }

            return null;
        } else if (methodName.equals("load")) {
            logger.info(Arrays.asList(args));

            Class domainClass = (Class) args[0];
            Long id = (Long) args[1];
            Map valueMap = MockFactoryBean.domainMap.get(domainClass);

            return valueMap.get(id);
        } else if (methodName.equals("delete")) {
            Object object = args[0];
            Map valueMap = MockFactoryBean.domainMap.get(object.getClass());

            if (valueMap != null) {
                Method method = object.getClass()
                                      .getDeclaredMethod("getId",
                        new Class[0]);
                Object obj = method.invoke(object, new Object[0]);
                Long id = (Long) obj;
                valueMap.remove(id);
            }

            return null;
        } else if (methodName.equals("getFlushMode")) {
            return FlushMode.AUTO;
        } else if (methodName.equals("isOpen")) {
            return true;
        }

        return ProxyHelper.generateReturn(proxy, m, args);
    }
}
