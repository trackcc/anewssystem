package anni.asecurity.manager;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import anni.asecurity.domain.Resource;
import anni.asecurity.domain.Role;

import anni.core.dao.hibernate.HibernateEntityDao;

import anni.core.security.AuthenticationHelper;
import anni.core.security.cache.AcegiCacheManager;
import anni.core.security.resource.ResourceDetails;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;

import org.acegisecurity.userdetails.UserDetails;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class RoleManager extends HibernateEntityDao<Role> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(RoleManager.class);

    /**
     * AcegiCacheManager.
     */
    private AcegiCacheManager acegiCacheManager = null;

    /**
     * @param acegiCacheManagerIn acegiCacheManager.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManagerIn) {
        acegiCacheManager = acegiCacheManagerIn;
    }

    /**
     * @param o role.
     */
    @Override
    public void save(Object o) {
        super.save(o);

        Role role = (Role) o;
        Set<Resource> resources = role.getResources();

        for (Resource resource : resources) {
            saveRoleInCache(resource);
        }
    }

    /**
     * @param o role.
     */
    @Override
    public void remove(Object o) {
        super.remove(o);

        Role role = (Role) o;
        removeAuthoritiesInCache(role.getName());
    }

    /**
     * 将资源保存到缓存里.
     *
     * @param resc 资源
     */
    public void saveRoleInCache(Resource resc) {
        GrantedAuthority[] authorities = AuthenticationHelper
            .convertToGrantedAuthority(resc.getRoles(), "name");
        logger.info(java.util.Arrays.asList(authorities));

        ResourceDetails rd = acegiCacheManager.getResourceFromCache(resc
                .getResString());
        rd.setAuthorities(authorities);
    }

    /**
     * 删除缓存中的授权.
     *
     * @param authority 授权
     */
    private void removeAuthoritiesInCache(String authority) {
        GrantedAuthorityImpl auth = new GrantedAuthorityImpl(authority);
        List<String> rescs = acegiCacheManager.getAllResources();

        for (Iterator iter = rescs.iterator(); iter.hasNext();) {
            String str = (String) iter.next();
            ResourceDetails resc = acegiCacheManager.getResourceFromCache(str);
            GrantedAuthority[] auths = resc.getAuthorities();
            int idx = ArrayUtils.indexOf(auths, auth);

            if (idx >= 0) {
                auths = (GrantedAuthority[]) ArrayUtils.remove(auths, idx);
                resc.setAuthorities(auths);
            }
        }

        List<String> users = acegiCacheManager.getAllUsers();

        for (Iterator iter = users.iterator(); iter.hasNext();) {
            String username = (String) iter.next();
            UserDetails user = acegiCacheManager.getUser(username);
            GrantedAuthority[] auths = user.getAuthorities();
            int idx = ArrayUtils.indexOf(auths, auth);

            if (idx >= 0) {
                auths = (GrantedAuthority[]) ArrayUtils.remove(auths, idx);
                user = new org.acegisecurity.userdetails.User(user
                        .getUsername(), user.getPassword(),
                        user.isEnabled(), true, true, true, auths);
                acegiCacheManager.addUser(user);
            }
        }
    }
}
