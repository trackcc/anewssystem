package anni.core.security.resource;

import org.acegisecurity.GrantedAuthority;

import org.springframework.util.Assert;


/**
 * ResourceDetails的实现类.
 * resString 资源串， 如Url资源串 /admin/index.jsp, Method资源串 com.abc.service.userManager.save 等
 * resType 资源类型，如URL, METHOD 等
 * authorities 该资源所拥有的权限
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public class Resource implements ResourceDetails {
    /**
     * serializable.
     */
    private static final long serialVersionUID = -1L;

    /**
     * URL.
     */
    public static final String RESOURCE_TYPE_URL = "URL";

    /**
     * METHOD.
     */
    public static final String RESOURCE_TYPE_METHOD = "METHOD";

    /**
     * TAG.
     */
    public static final String RESOURCE_TYPE_TAG = "TAG";

    /**
     * resource string.
     */
    private String resString;

    /**
     * resource type.
     */
    private String resType;

    /**
     * 授权.
     */
    private GrantedAuthority[] authorities;

    /**
     * 构造方法.
     *
     * @param resString resource string
     * @param resType resource type
     * @param authorities array
     */
    public Resource(String resString, String resType,
        GrantedAuthority[] authorities) {
        Assert.notNull(resString,
            "Cannot pass null or empty values to resource string");
        Assert.notNull(resType,
            "Cannot pass null or empty values to resource type");
        this.resString = resString;
        this.resType = resType;
        setAuthorities(authorities);
    }

    /**
     * @param rhs Resource.
     * @return boolean
     */
    @Override
    public boolean equals(Object rhs) {
        if (!(rhs instanceof Resource)) {
            System.out.println(rhs);

            return false;
        }

        Resource resauth = (Resource) rhs;

        if (!getResString().equals(resauth.getResString())) {
            return false;
        }

        if (!getResType().equals(resauth.getResType())) {
            return false;
        }

        if (resauth.getAuthorities().length != getAuthorities().length) {
            return false;
        }

        for (int i = 0; i < getAuthorities().length; i++) {
            if (!getAuthorities()[i].equals(resauth.getAuthorities()[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * @return hash code.
     */
    @Override
    public int hashCode() {
        int code = 168;

        if (getAuthorities() != null) {
            for (int i = 0; i < getAuthorities().length; i++) {
                code *= (getAuthorities()[i].hashCode() % 7);
            }
        }

        if (getResString() != null) {
            code *= (getResString().hashCode() % 7);
        }

        return code;
    }

    /**
     * @return resource string.
     */
    public String getResString() {
        return resString;
    }

    /**
     * @param resString resource string.
     */
    public void setResString(String resString) {
        this.resString = resString;
    }

    /**
     * @return array.
     */
    public GrantedAuthority[] getAuthorities() {
        if (authorities == null) {
            this.authorities = new GrantedAuthority[0];

            return this.authorities;
        } else {
            int len = authorities.length;
            GrantedAuthority[] auths = new GrantedAuthority[len];
            System.arraycopy(authorities, 0, auths, 0, len);

            return auths;
        }
    }

    /**
     * @return resource type.
     */
    public String getResType() {
        return resType;
    }

    /**
     * @param resType resource type.
     */
    public void setResType(String resType) {
        this.resType = resType;
    }

    /**
     * @param authorities array.
     */
    public void setAuthorities(GrantedAuthority[] authorities) {
        Assert.notNull(authorities,
            "Cannot pass a null GrantedAuthority array");

        for (int i = 0; i < authorities.length; i++) {
            Assert.notNull(authorities[i],
                "Granted authority element " + i
                + " is null - GrantedAuthority[] cannot contain any null elements");
        }

        int len = authorities.length;
        this.authorities = new GrantedAuthority[len];
        System.arraycopy(authorities, 0, this.authorities, 0, len);
    }
}
