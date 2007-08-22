package anni.core.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * 实体类.
 * 默认实现了id, Serializable接口和equals(), hashCode()方法的超类
 * 使用String作为主键
 *
 * @author Lingo
 * @since 2007-06-05
 */
@MappedSuperclass
public class StringEntityBean implements Serializable {
    /**
     * 持久化.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 主键.
     */
    protected String id;

    /**
     * 无参数构造方法.
     */
    public StringEntityBean() {
    }

    /**
     * 设置id的构造方法.
     *
     * @param idIn id
     */
    public StringEntityBean(String idIn) {
        id = idIn;
    }

    /**
     * 获得id，生成策略是自动生成.
     *
     * @return String id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getId() {
        return id;
    }

    /**
     * @param idIn String.
     */
    public void setId(String idIn) {
        id = idIn;
    }

    /**
     * 检验两个实体类是否相同.
     *
     * @param obj Object
     * @return boolean 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringEntityBean) {
            if (id == null) {
                return super.equals(obj);
            } else {
                return id.equals(((StringEntityBean) obj).id);
            }
        } else {
            return false;
        }
    }

    /**
     * 计算哈希码.
     *
     * @return int 哈希码
     */
    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        } else {
            return id.hashCode();
        }
    }
}
