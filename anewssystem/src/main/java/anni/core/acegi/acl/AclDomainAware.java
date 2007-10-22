package anni.core.acegi.acl;

import java.io.Serializable;


/**
 * 标示哪些doamin需要acl保护.
 * 实际使用时，需要让被保护的domain类实现AclDomainAware接口
 *
 * @author sshwsfc@gmail.com
 * @since 2007-10-12
 */
public interface AclDomainAware {
    /**
     * 取得需要保护的domain的id.
     *
     * @return id
     */
    Serializable getId();
}
