
package ${packageName};

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

<#list importList as item>
import ${item};
</#list>

/**
 * ${className} generated by Lingo.
 *
 * @author Lingo
 * @since ${now?string("yyyy年MM月dd日 a HH时mm分ss秒S")}
 */
${domainUtils.createEntity(pojo)}
${domainUtils.createTable(pojo)}
public class ${className} implements Serializable {
    /** * serial. */
    static final long serialVersionUID = 0L;
<#list fields as item>
    /** * ${item.name}. */
    ${domainUtils.createField(item)}
</#list>

    /** * 构造方法. */
    public ${className}() {
    }

<#list fields as item>
    /** * @return ${item.name}. */
${domainUtils.createGetterAnnotation(item, pojo)}
${domainUtils.createGetter(item)}

    /** * @param ${item.name} ${item.name}. */
${domainUtils.createSetter(item)}

</#list>
}
