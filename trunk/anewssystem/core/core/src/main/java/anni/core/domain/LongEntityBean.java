package anni.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;


/**
 * 实体类.
 * 默认实现了id, Serializable接口和equals(), hashCode()方法的超类
 * 使用Long作为主键
 *
 * @author Lingo
 * @since 2007-06-05
 */
@MappedSuperclass
public class LongEntityBean implements Serializable {
    /**
     * 持久化.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 主键.
     */
    protected Long id;

    /**
     * 无参数构造方法.
     */
    public LongEntityBean() {
    }

    /**
     * 设置id的构造方法.
     *
     * @param idIn id
     */
    public LongEntityBean(Long idIn) {
        id = idIn;
    }

    /**
     * 获得id，生成策略是自动生成.
     *
     * @return Long id
     */
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /**
     * @param idIn Long.
     */
    public void setId(Long idIn) {
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
        if (obj instanceof LongEntityBean) {
            if (id == null) {
                return super.equals(obj);
            } else {
                return id.equals(((LongEntityBean) obj).id);
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
