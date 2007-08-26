package anni.core.mock.db.hibernate;

import java.beans.*;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;


/**
 * hibernate的mock方式.
 *
 * @author Lingo
 * @since 2007-07-11
 */
public class MockFactoryBean extends AnnotationSessionFactoryBean {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(MockFactoryBean.class);

    /**
     *
     */
    static Map<Class, Map> domainMap = new HashMap<Class, Map>();
    private Class[] annotatedClasses;

    public SessionFactory buildSessionFactory() {
        return new MockSessionFactory();
    }

    public SessionFactory wrapSessionFactoryIfNecessary(
        SessionFactory sessionFactory) {
        return sessionFactory;
    }

    public void afterSessionFactoryCreation() {
        //
    }

    public void setAnnotatedClasses(Class[] annotatedClasses) {
        super.setAnnotatedClasses(annotatedClasses);
        this.annotatedClasses = annotatedClasses;
    }

    public void init() {
        logger.info("init");

        try {
            XMLDecoder d = new XMLDecoder(new BufferedInputStream(
                        new FileInputStream("db.xml")));
            Object result = d.readObject();
            domainMap = (Map) result;
            d.close();
        } catch (Exception ex) {
            logger.error(ex);
            domainMap = new HashMap<Class, Map>();
        }

        for (Class clz : annotatedClasses) {
            if (domainMap.get(clz) == null) {
                domainMap.put(clz, new HashMap());
            }
        }
    }

    public void destroy() {
        logger.info("destory");

        try {
            XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
                        new FileOutputStream("db.xml")));
            e.writeObject(domainMap);
            e.close();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
}
