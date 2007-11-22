package anni.core.security;

import java.util.*;

import junit.framework.TestCase;

import org.acegisecurity.providers.dao.UserCache;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;


public class InCacheDaoImplTest extends TestCase {
    InCacheDaoImpl dao = null;
    UserCache mockCache = null;
    UserDetails mockDetails = null;

    @Override
    protected void setUp() {
        dao = new InCacheDaoImpl();
        mockCache = createMock(UserCache.class);
        mockDetails = createMock(UserDetails.class);
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testLoadUserByUsername() {
        expect(mockCache.getUserFromCache("user")).andReturn(mockDetails);
        replay(mockCache);

        dao.setUserCache(mockCache);

        UserDetails ud = dao.loadUserByUsername("user");
        verify();

        assertEquals(mockDetails, ud);
    }
}
