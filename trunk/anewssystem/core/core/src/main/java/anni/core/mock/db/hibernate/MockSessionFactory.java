package anni.core.mock.db.hibernate;

import java.io.Serializable;

import java.sql.Connection;

import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import org.hibernate.classic.Session;

import org.hibernate.engine.FilterDefinition;

import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;

import org.hibernate.stat.Statistics;


public class MockSessionFactory implements SessionFactory {
    public Reference getReference() throws NamingException {
        return null;
    }

    public Session openSession(Connection arg0) {
        return null;
    }

    public Session openSession(Interceptor arg0) throws HibernateException {
        return null;
    }

    public Session openSession(Connection arg0, Interceptor arg1) {
        return null;
    }

    public Session openSession() throws HibernateException {
        return (Session) SessionProxy.getInstance(Session.class, this);
    }

    public Session getCurrentSession() throws HibernateException {
        return null;
    }

    public ClassMetadata getClassMetadata(Class arg0)
        throws HibernateException {
        return null;
    }

    public ClassMetadata getClassMetadata(String arg0)
        throws HibernateException {
        return null;
    }

    public CollectionMetadata getCollectionMetadata(String arg0)
        throws HibernateException {
        return null;
    }

    public Map getAllClassMetadata() throws HibernateException {
        return null;
    }

    public Map getAllCollectionMetadata() throws HibernateException {
        return null;
    }

    public Statistics getStatistics() {
        return null;
    }

    public void close() throws HibernateException {
    }

    public boolean isClosed() {
        return false;
    }

    public void evict(Class arg0) throws HibernateException {
    }

    public void evict(Class arg0, Serializable arg1)
        throws HibernateException {
    }

    public void evictEntity(String arg0) throws HibernateException {
    }

    public void evictEntity(String arg0, Serializable arg1)
        throws HibernateException {
    }

    public void evictCollection(String arg0) throws HibernateException {
    }

    public void evictCollection(String arg0, Serializable arg1)
        throws HibernateException {
    }

    public void evictQueries() throws HibernateException {
    }

    public void evictQueries(String arg0) throws HibernateException {
    }

    public StatelessSession openStatelessSession() {
        return null;
    }

    public StatelessSession openStatelessSession(Connection arg0) {
        return null;
    }

    public Set getDefinedFilterNames() {
        return null;
    }

    public FilterDefinition getFilterDefinition(String arg0)
        throws HibernateException {
        return null;
    }
}
