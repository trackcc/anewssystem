package anni.core.security.service;

import java.util.List;

import anni.core.security.resource.Resource;

import org.acegisecurity.userdetails.User;


/**
 * 为缓存提供 User 和 Resource 实例.
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public interface AuthenticationService {
    /**
     * 获得所有用户.
     *
     * @return List
     */
    List<User> getUsers();

    /**
     * 获得所有资源.
     *
     * @return List
     */
    List<Resource> getResources();
}
