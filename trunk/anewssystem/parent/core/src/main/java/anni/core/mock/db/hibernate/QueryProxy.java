package anni.core.mock.db.hibernate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;


public class QueryProxy implements InvocationHandler {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(QueryProxy.class);
    private String hql;
    private Query query;

    /**
     *
     */
    public static Object getInstance(Class clz, String hql) {
        QueryProxy proxy = new QueryProxy();
        proxy.hql = hql;
        proxy.query = (Query) Proxy.newProxyInstance(clz.getClassLoader(),
                new Class[] {clz}, proxy);

        return proxy.query;
    }

    /**
     *
     */
    public Object invoke(Object proxy, Method m, Object[] args)
        throws Throwable {
        logger.info(m);

        String methodName = m.getName();

        if (methodName.equals("list")) {
            Class queryType = null;

            for (Class domainClass : MockFactoryBean.domainMap.keySet()) {
                String domainClassName = domainClass.getSimpleName();

                if (hql.indexOf(domainClassName) != -1) {
                    queryType = domainClass;
                }
            }

            if (hql.indexOf("count") != -1) {
                List<Long> result = new ArrayList<Long>();
                long count = 0L;

                try {
                    count = (long) MockFactoryBean.domainMap.get(queryType)
                                                            .values().size();
                } catch (Exception ex) {
                    logger.error(ex);
                }

                result.add(count);

                return result;
            } else {
                List result = new ArrayList();

                try {
                    result.addAll(MockFactoryBean.domainMap.get(queryType)
                                                           .values());
                } catch (Exception ex) {
                    logger.error(ex);
                }

                return result;
            }
        } else if (methodName.equals("setFirstResult")) {
            return query;
        } else if (methodName.equals("setMaxResults")) {
            return query;
        }

        return ProxyHelper.generateReturn(proxy, m, args);
    }
}
