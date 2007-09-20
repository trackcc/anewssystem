package anni.core.test;

import java.io.Serializable;

import java.util.*;

import anni.core.dao.hibernate.HibernateEntityDao;
import anni.core.dao.support.Page;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.LogFactory;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;

import org.hibernate.transform.ResultTransformer;

import org.springframework.mock.web.*;

import org.springframework.web.servlet.ModelAndView;


public class MockCriteria implements Criteria {
    public String getAlias() {
        return null;
    }

    public Criteria setProjection(Projection arg) {
        return this;
    }

    public Criteria add(Criterion arg) {
        return this;
    }

    public Criteria addOrder(Order arg) {
        return this;
    }

    public Criteria setFetchMode(String arg0, FetchMode arg1)
        throws HibernateException {
        return this;
    }

    public Criteria setLockMode(LockMode arg) {
        return this;
    }

    public Criteria setLockMode(String arg0, LockMode arg1) {
        return this;
    }

    public Criteria createAlias(String arg0, String arg1)
        throws HibernateException {
        return this;
    }

    public Criteria createAlias(String arg0, String arg1, int arg2)
        throws HibernateException {
        return this;
    }

    public Criteria createCriteria(String arg) throws HibernateException {
        return this;
    }

    public Criteria createCriteria(String arg0, int arg1)
        throws HibernateException {
        return this;
    }

    public Criteria createCriteria(String arg0, String arg1)
        throws HibernateException {
        return this;
    }

    public Criteria createCriteria(String arg0, String arg1, int arg2)
        throws HibernateException {
        return this;
    }

    public Criteria setResultTransformer(ResultTransformer arg) {
        return this;
    }

    public Criteria setMaxResults(int arg) {
        return this;
    }

    public Criteria setFirstResult(int arg) {
        return this;
    }

    public Criteria setFetchSize(int arg) {
        return this;
    }

    public Criteria setTimeout(int arg) {
        return this;
    }

    public Criteria setCacheable(boolean arg) {
        return this;
    }

    public Criteria setCacheRegion(String arg) {
        return this;
    }

    public Criteria setComment(String arg) {
        return this;
    }

    public Criteria setFlushMode(FlushMode arg) {
        return this;
    }

    public Criteria setCacheMode(CacheMode arg) {
        return this;
    }

    public List list() throws HibernateException {
        return new ArrayList();
    }

    public ScrollableResults scroll() throws HibernateException {
        return null;
    }

    public ScrollableResults scroll(ScrollMode arg)
        throws HibernateException {
        return null;
    }

    public Object uniqueResult() throws HibernateException {
        return null;
    }
}
