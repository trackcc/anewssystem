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
     * @param resStringIn resource string
     * @param resTypeIn resource type
     * @param authoritiesIn array
     */
    public Resource(String resStringIn, String resTypeIn,
        GrantedAuthority[] authoritiesIn) {
        Assert.notNull(resStringIn,
            "Cannot pass null or empty values to resource string");
        Assert.notNull(resTypeIn,
            "Cannot pass null or empty values to resource type");
        resString = resStringIn;
        resType = resTypeIn;
        setAuthorities(authoritiesIn);
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
     * @param resStringIn resource string.
     */
    public void setResString(String resStringIn) {
        resString = resStringIn;
    }

    /**
     * @return array.
     */
    public GrantedAuthority[] getAuthorities() {
        return authorities;
    }

    /**
     * @return resource type.
     */
    public String getResType() {
        return resType;
    }

    /**
     * @param resTypeIn resource type.
     */
    public void setResType(String resTypeIn) {
        resType = resTypeIn;
    }

    /**
     * @param authoritiesIn array.
     */
    public void setAuthorities(GrantedAuthority[] authoritiesIn) {
        Assert.notNull(authoritiesIn,
            "Cannot pass a null GrantedAuthority array");

        for (int i = 0; i < authoritiesIn.length; i++) {
            Assert.notNull(authoritiesIn[i],
                "Granted authority element " + i
                + " is null - GrantedAuthority[] cannot contain any null elements");
        }

        authorities = authoritiesIn;
    }
}
