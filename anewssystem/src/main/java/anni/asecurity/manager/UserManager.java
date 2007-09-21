package anni.asecurity.manager;

import java.util.List;

import anni.asecurity.domain.User;

import anni.core.dao.hibernate.HibernateEntityDao;

import anni.core.security.AuthenticationHelper;
import anni.core.security.cache.AcegiCacheManager;

import org.acegisecurity.GrantedAuthority;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class UserManager extends HibernateEntityDao<User> {
    /**
     * acegiCacheManager.
     */
    private AcegiCacheManager acegiCacheManager = null;

    /**
     * @param acegiCacheManager AcegiCacheManager.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
        this.acegiCacheManager = acegiCacheManager;
    }

    /**
     * 根据用户名和密码获得登陆的用户.
     *
     * @param loginId 用户名
     * @param password 密码
     * @return User 登陆用户
     */
    public User getUserByLoginidAndPasswd(String loginId, String password) {
        String hql = "from User u where u.username=? and u.password=?";

        List<User> list = getSession().createQuery(hql)
                              .setString(0, loginId).setString(1, password)
                              .list();

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 删除用户时需在cache移除用户.
     *
     * @param o user
     */
    @Override
    public void remove(Object o) {
        super.remove(o);

        if (o instanceof User) {
            removeUserInCache(((User) o).getUsername());
        }
    }

    /**
     * 保存User前需要检查当前用户名是否有更改，若有则需先在cache中去除再重新加入.
     *
     * @param o Object User type only.
     */
    @Override
    public void save(Object o) {
        User user = (User) o;
        boolean isNew = (user.getId() == null);
        String orginName = "";

        if (!isNew) {
            User orginUser = get(user.getId());
            orginName = orginUser.getUsername();
            getHibernateTemplate().evict(orginUser);
        }

        super.save(user);

        if (isNew) {
            saveUserInCache(user);
        } else {
            user = get(user.getId());
            removeUserInCache(orginName);
            saveUserInCache(user);
        }
    }

    // ------------------------------------------------------------------------

    /**
     * 注意参数中的User需要已经与hibernate session关联，否则无法lazyload得到roles.
     *
     * @param user 用户
     */
    private void saveUserInCache(User user) {
        GrantedAuthority[] authorities = AuthenticationHelper
            .convertToGrantedAuthority(user.getRoles(), "name");

        if (user.getUsername() != null) {
            acegiCacheManager.addUser(user.getUsername(),
                user.getPassword(), user.isEnabled(), true, true, true,
                authorities);
        }
    }

    /**
     * 根据用户名，从缓存中删除用户.
     *
     * @param username 用户名
     */
    public void removeUserInCache(String username) {
        acegiCacheManager.removeUser(username);
    }
}
