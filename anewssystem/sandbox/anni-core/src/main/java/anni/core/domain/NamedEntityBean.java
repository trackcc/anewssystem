package anni.core.domain;

import java.io.Serializable;


/**
 * 应用于只有id和name两个字段的字典表时，应该实现这个接口.
 * 这样有利于对领域模型进行分类封装
 * 用到了这个类，也说明，我开始慢慢从理论向实践过渡了
 *
 * 实际中使用时，实现类需要使用导入javax.persistence.Column
 * 并对name字段进行如下注释
 * <pre>@Column(unique = true, nullable = false)</pre>
 * 我们考虑使用NamedEntityBean的情况，很可能是除了id就只有name字段的情况
 * 这样，name重复，或者为空，就没有实际意义了，所以在进行这样的解释
 *
 * @author Lingo
 * @since 2007-06-05
 */
public interface NamedEntityBean extends Serializable {
    /**
     * 名称在这里代表唯一命名.
     *
     * @return String name
     */
    String getName();

    /**
     * 设置这个唯一的名字.
     *
     * @param nameIn name.
     */
    void setName(String nameIn);
}
