package anni.asecurity.manager;

import anni.asecurity.domain.Resource;

import anni.core.dao.hibernate.HibernateEntityDao;

import anni.core.security.AuthenticationHelper;
import anni.core.security.cache.AcegiCacheManager;
import anni.core.security.resource.ResourceDetails;

import org.acegisecurity.GrantedAuthority;

import org.apache.commons.lang.StringUtils;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class ResourceManager extends HibernateEntityDao<Resource> {
    /**
     * AcegiCacheManager.
     */
    private AcegiCacheManager acegiCacheManager = null;

    /**
     * @param acegiCacheManager AcegiCacheManager.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
        this.acegiCacheManager = acegiCacheManager;
    }

    /**
     * @param o resource.
     */
    @Override
    public void save(Object o) {
        Resource resource = (Resource) o;
        boolean isNew = (resource.getId() == null);
        String orginString = "";

        if (!isNew) {
            Resource orginResc = get(resource.getId());
            orginString = orginResc.getResString();
            getHibernateTemplate().evict(orginResc);
        }

        super.save(o);

        if (!isNew
                && !StringUtils.equals(resource.getResString(), orginString)) {
            removeRescInCache(orginString);
        }

        saveRescInCache(resource);
    }

    /**
     * @param o resource.
     */
    @Override
    public void remove(Object o) {
        super.remove(o);

        Resource resource = (Resource) o;
        removeRescInCache(resource.getResString());
    }

    /**
     * 把资源保存里缓存.
     *
     * @param resc 资源
     */
    private void saveRescInCache(Resource resc) {
        GrantedAuthority[] authorities = AuthenticationHelper
            .convertToGrantedAuthority(resc.getRoles(), "name");
        ResourceDetails rd = new anni.core.security.resource.Resource(resc
                .getResString(), resc.getResType(), authorities);
        acegiCacheManager.addResource(rd);
    }

    /**
     * 从缓存里删除资源.
     *
     * @param resString 资源内容
     */
    public void removeRescInCache(String resString) {
        acegiCacheManager.removeResource(resString);
    }
}
