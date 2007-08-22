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
 * 使用Integer作为主键
 *
 * @author Lingo
 * @since 2007-06-05
 */
@MappedSuperclass
public class IntegerEntityBean implements Serializable {
    /**
     * 持久化.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 主键.
     */
    protected Integer id;

    /**
     * 无参数构造方法.
     */
    public IntegerEntityBean() {
    }

    /**
     * 设置id的构造方法.
     *
     * @param idIn id
     */
    public IntegerEntityBean(Integer idIn) {
        id = idIn;
    }

    /**
     * 获得id，生成策略是自动生成.
     *
     * @return Integer id
     */
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    /**
     * @param idIn Interger.
     */
    public void setId(Integer idIn) {
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
        if (obj instanceof IntegerEntityBean) {
            if (id == null) {
                return super.equals(obj);
            } else {
                return id.equals(((IntegerEntityBean) obj).id);
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
