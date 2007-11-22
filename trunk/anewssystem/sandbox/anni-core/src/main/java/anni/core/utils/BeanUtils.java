package anni.core.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;


/**
 * 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装.
 *
 * @author Lingo
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
    /** * 日志. */
    private static Log logger = LogFactory.getLog(BeanUtils.class);

    /** * 工具类需要的protected构造方法. */
    protected BeanUtils() {
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     *
     * @param object 处理的对象
     * @param propertyName 对象里的变量名称
     * @return Field 获得的变量
     * @throws NoSuchFieldException 如果没有该Field时抛出.
     */
    public static Field getDeclaredField(Object object, String propertyName)
        throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        return getDeclaredField(object.getClass(), propertyName);
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     *
     * @param clazz 需要处理的类型
     * @param propertyName 对象里的变量名称
     * @return Field 获得的变量
     * @throws NoSuchFieldException 如果没有该Field时抛出.
     */
    public static Field getDeclaredField(Class clazz, String propertyName)
        throws NoSuchFieldException {
        Assert.notNull(clazz);
        Assert.hasText(propertyName);

        for (Class superClass = clazz; superClass != Object.class;
                superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
                logger.warn(e);
            }
        }

        throw new NoSuchFieldException("No such field: " + clazz.getName()
            + '.' + propertyName);
    }

    /**
     * 暴力获取对象变量值,忽略private,protected修饰符的限制.
     *
     * @param object 需要处理的对象
     * @param propertyName 对象里的变量名称
     * @return 变量的值
     * @throws NoSuchFieldException 如果没有该Field时抛出
     * @throws IllegalAccessException 目前感觉，绝对不会出这个异常
     */
    public static Object forceGetProperty(Object object,
        String propertyName)
        throws NoSuchFieldException, IllegalAccessException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        Field field = getDeclaredField(object, propertyName);

        boolean accessible = field.isAccessible();
        // field.setAccessible(true);
        setAccessible(field, true);

        Object result = null;

        //try {
        result = field.get(object);
        //} catch (IllegalAccessException e) {
        //    logger.info("Error won't happen");
        //}

        // field.setAccessible(accessible);
        setAccessible(field, accessible);

        return result;
    }

    /**
     * 暴力设置对象变量值,忽略private,protected修饰符的限制.
     *
     * @param object 需要处理的对象
     * @param propertyName 对象里的变量名称
     * @param newValue 需要设置的变量值
     * @throws NoSuchFieldException 如果没有该Field时抛出
     * @throws IllegalAccessException 目前感觉，绝对不会出这个异常
     */
    public static void forceSetProperty(Object object,
        String propertyName, Object newValue)
        throws NoSuchFieldException, IllegalAccessException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        // field.setAccessible(true);
        setAccessible(field, true);

        //try {
        field.set(object, newValue);
        //} catch (IllegalAccessException e) {
        //    logger.info("Error won't happen");
        //}

        // field.setAccessible(accessible);
        setAccessible(field, accessible);
    }

    /**
     * 暴力调用对象方法，忽略private,protected修饰符的限制.
     *
     * @param object 需要处理的对象
     * @param methodName 方法名
     * @param params 需要设置的变量值，不定数目，当作数组看待
     * @return 调用方法后返回对象
     * @throws NoSuchMethodException 如果没有该Method时抛出.
     */
    public static Object invokePrivateMethod(Object object,
        String methodName, Object... params) throws NoSuchMethodException {
        Assert.notNull(object);
        Assert.hasText(methodName);

        Class[] types = new Class[params.length];

        for (int i = 0; i < params.length; i++) {
            types[i] = params[i].getClass();
        }

        logger.fatal(object);
        logger.fatal(methodName);
        logger.fatal(params);
        logger.fatal(types.length);

        Class clazz = object.getClass();
        Method method = null;

        for (Class superClass = clazz; superClass != Object.class;
                superClass = superClass.getSuperclass()) {
            try {
                method = superClass.getDeclaredMethod(methodName, types);

                break;
            } catch (NoSuchMethodException e) {
                // 方法不在当前类定义,继续向上转型
                logger.warn(e);
            }
        }

        if (method == null) {
            throw new NoSuchMethodException("No Such Method:"
                + clazz.getSimpleName() + "." + methodName);
        }

        boolean accessible = method.isAccessible();
        // method.setAccessible(true);
        setAccessible(method, true);

        Object result = null;

        try {
            result = method.invoke(object, params);
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }

        //method.setAccessible(accessible);
        setAccessible(method, accessible);

        return result;
    }

    /**
     * 按Field的类型取得Field列表.
     *
     * @param object 需要处理的对象
     * @param type 需要获得的变量类型
     * @return 指定type的变量列表
     */
    public static List<Field> getFieldsByType(Object object, Class type) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
        }

        return list;
    }

    /**
     * 按fieldName获得Field的类型.
     *
     * @param type 需要处理的类型
     * @param name 变量名
     * @return 变量类型
     * @throws NoSuchFieldException 如果不存在这个名称的变量
     */
    public static Class getPropertyType(Class type, String name)
        throws NoSuchFieldException {
        return getDeclaredField(type, name).getType();
    }

    /**
     * 获得field的getter方法名称.
     * 有个问题，就是，为什么不用java.beans.PropertyDescriptor
     * 问题的答案是，pd必须是getter和setter都存在的时候才能正常获得getter和setter
     * 咱们这里的情况可能是只有其中之一，所以不能这样使用
     *
     * @param type 需要处理的类型
     * @param fieldName 变量名
     * @return getter方法名
     * @throws NoSuchFieldException 没有这个变量的时候抛出异常
     */
    public static String getGetterName(Class type, String fieldName)
        throws NoSuchFieldException {
        Assert.notNull(type, "Type required");
        Assert.hasText(fieldName, "FieldName required");
        logger.info(type.getDeclaredField(fieldName).getType().getName());

        if (type.getDeclaredField(fieldName).getType().getName()
                    .equals("boolean")) {
            return "is" + StringUtils.capitalize(fieldName);
        } else {
            return "get" + StringUtils.capitalize(fieldName);
        }
    }

    /**
     * 获得field的getter函数,如果找不到该方法,返回null.
     *
     * @param type 需要处理的类型
     * @param fieldName 变量名
     * @return getter方法
     */
    public static Method getGetterMethod(Class type, String fieldName) {
        try {
            return type.getMethod(getGetterName(type, fieldName));
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 为field或method设置访问权限的辅助方法.
     *
     * @param obj 需要设置权限的变量
     * @param accessible 访问权限
     */
    public static void setAccessible(final AccessibleObject obj,
        final boolean accessible) {
        AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    obj.setAccessible(accessible);

                    return null; // nothing to return
                }
            });
    }
}
