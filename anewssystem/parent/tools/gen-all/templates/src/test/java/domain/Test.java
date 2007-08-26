
package ${packageName};

import junit.framework.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ${className}Test extends TestCase {
    protected static Log logger = LogFactory.getLog(${className}Test.class);
    @Override
    protected void setUp() {
    }
    @Override
    protected void tearDown() {
    }
    public void testFields() {
        ${className} entity = new ${className}();
<#list fields as item>
  <#if item.type.name=="int">
        entity.set${item.name?cap_first}(0);
        assertEquals(0, entity.get${item.name?cap_first}());
  <#elseif item.type.name=="String">
        entity.set${item.name?cap_first}("${item.name}");
        assertEquals("${item.name}", entity.get${item.name?cap_first}());
  <#else>
        entity.set${item.name?cap_first}(null);
        assertNull(entity.get${item.name?cap_first}());
  </#if>
</#list>
    }
}
