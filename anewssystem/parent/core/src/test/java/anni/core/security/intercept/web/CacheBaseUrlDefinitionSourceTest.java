package anni.core.security.intercept.web;

import java.lang.reflect.*;

import java.util.*;

import anni.core.security.AuthenticationHelper;
import anni.core.security.cache.*;
import anni.core.security.resource.*;
import anni.core.security.service.*;

import junit.framework.TestCase;

import net.sf.ehcache.*;

import org.acegisecurity.*;

import org.acegisecurity.intercept.method.MethodDefinitionSource;

import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.dao.cache.EhCacheBasedUserCache;

import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.*;
import org.aspectj.lang.reflect.CodeSignature;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.util.Assert;


public class CacheBaseUrlDefinitionSourceTest extends TestCase {
    CacheBaseUrlDefinitionSource source = null;
    EhCacheBasedResourceCache resourceCache = null;
    EhCacheBasedUserCache userCache = null;
    ResourceDetails resourceDetails = null;
    AcegiCacheManager manager = null;
    Cache userEhCache = null;
    Cache resourceEhCache = null;
    GrantedAuthority[] authorities = null;

    @Override
    protected void setUp() {
        source = new CacheBaseUrlDefinitionSource();
        resourceCache = new EhCacheBasedResourceCache();
        userCache = new EhCacheBasedUserCache();

        authorities = new GrantedAuthority[] {
                new GrantedAuthorityImpl("all")
            };
        resourceDetails = new Resource("/", "URL", authorities);
        userEhCache = new Cache("test user", 20, false, false, 0, 0);
        resourceEhCache = new Cache("test resource", 20, false, false, 0, 0);
        userEhCache.initialise();
        resourceEhCache.initialise();

        userCache.setCache(userEhCache);
        resourceCache.setCache(resourceEhCache);
        resourceCache.putResourceInCache(resourceDetails);
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDefault() {
        assertNull(source.getConfigAttributeDefinitions());
        source.setConvertUrlToLowercaseBeforeComparison(true);
        assertTrue(source.isConvertUrlToLowercaseBeforeComparison());
        source.setUseAntPath(true);
        assertTrue(source.isUseAntPath());
    }

    public void testProtectAllResource() {
        source.setProtectAllResource(true);
        assertTrue(source.isProtectAllResource());
    }

    public void testLookupAttributes() {
        try {
            ConfigAttributeDefinition def = source.lookupAttributes(null);
            fail();
        } catch (NullPointerException ex) {
            assertEquals(null, ex.getMessage());
        }
    }

    public void testLookupAttributes2() {
        try {
            ConfigAttributeDefinition def = source.lookupAttributes("/");
            assertTrue(true);
        } catch (NullPointerException ex) {
            fail(ex.toString());
        }
    }

    public void testLookupAttributes3() {
        resourceCache.removeAllResources();
        //resourceDetails = new Resource("/", "URL", authorities);
        resourceCache.putResourceInCache(resourceDetails);
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);

        ConfigAttributeDefinition def = source.lookupAttributes(
                "/index.jsp");
        assertNull(def);
    }

    public void testLookupAttributes4() {
        resourceCache.removeAllResources();
        resourceDetails = new Resource("/", "URL", authorities);
        resourceCache.putResourceInCache(resourceDetails);
        resourceDetails = new Resource("/index.jsp", "URL", authorities);
        resourceCache.putResourceInCache(resourceDetails);
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);

        ConfigAttributeDefinition def = source.lookupAttributes(
                "/index.jsp?a=2");
        assertNotNull(def);
    }

    public void testLookupAttributes5() {
        resourceCache.removeAllResources();
        resourceDetails = new Resource("/", "URL", authorities);
        resourceCache.putResourceInCache(resourceDetails);
        resourceDetails = new Resource("/INDEX.jsp", "URL", authorities);
        resourceCache.putResourceInCache(resourceDetails);
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);

        source.setUseAntPath(false);
        source.setConvertUrlToLowercaseBeforeComparison(true);
        source.setProtectAllResource(true);

        ConfigAttributeDefinition def = source.lookupAttributes(
                "/index.jsp");
        assertNotNull(def);
    }
}
