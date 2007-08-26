package anni.core.security;

import org.acegisecurity.providers.dao.UserCache;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;


/**
 * 登陆时从缓存里获取用户.
 * 而不是像@link org.acegisecurity.acl.basic.jdbc.JdbcDaoImpl 那样从数据库中获取用户实例
 * 实现loadUserByUsername(String username) 方法
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public class InCacheDaoImpl implements UserDetailsService {
    /**
     * 用户缓存.
     */
    private UserCache userCache;

    /**
     * @return UserCase.
     */
    public UserCache getUserCache() {
        return userCache;
    }

    /**
     * @param userCacheIn userCache.
     */
    public void setUserCache(UserCache userCacheIn) {
        userCache = userCacheIn;
    }

    /**
     * 根据用户名读取用户信息.
     * 可能抛出异常：
     * throws org.acegisecurity.userdetails.UsernameNotFoundException 找不到用户
     * throws org.springframework.dao.DataAccessException 数据无法访问
     *
     * @param username 用户名
     * @return UserDetails
     */
    public UserDetails loadUserByUsername(String username) {
        UserDetails ud = getUserCache()
                             .getUserFromCache(username);

        return ud;
    }
}
