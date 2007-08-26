package anni.core.mock.db.hibernate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.FlushMode;


public class ProxyHelper implements InvocationHandler {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(ProxyHelper.class);

    /**
     *
     */
    public static Object getInstance(Class clz) {
        ProxyHelper proxy = new ProxyHelper();

        return Proxy.newProxyInstance(clz.getClassLoader(),
            new Class[] {clz}, proxy);
    }

    /**
     *
     */
    public Object invoke(Object proxy, Method m, Object[] args)
        throws Throwable {
        logger.info(m);

        String methodName = m.getName();

        return ProxyHelper.generateReturn(proxy, m, args);
    }

    /**
     *
     */
    public static Object generateReturn(Object proxy, Method m,
        Object[] args) throws Throwable {
        Class clz = m.getReturnType();

        try {
            if (clz.isInterface()) {
                return ProxyHelper.getInstance(clz);
            } else if (clz.equals(void.class)) {
                return null;
            } else if (clz.equals(byte.class)) {
                return (byte) 0;
            } else if (clz.equals(short.class)) {
                return (short) 0;
            } else if (clz.equals(int.class)) {
                return 0;
            } else if (clz.equals(long.class)) {
                return 0L;
            } else if (clz.equals(float.class)) {
                return 0F;
            } else if (clz.equals(double.class)) {
                return 0D;
            } else if (clz.equals(char.class)) {
                return ' ';
            } else if (clz.equals(boolean.class)) {
                return false;
            } else {
                return clz.newInstance();
            }
        } catch (Exception ex) {
            logger.error(ex);

            return null;
        }
    }
}
