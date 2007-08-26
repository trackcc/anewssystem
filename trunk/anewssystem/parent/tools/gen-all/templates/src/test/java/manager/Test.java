
package ${pkg}.manager;

import anni.core.test.AbstractDaoTestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ${pkg}.domain.${clz};

public class ${clz}ManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(${clz}ManagerTest.class);
    private ${clz}Manager ${clz?uncap_first}Manager;

    public void set${clz}Manager(${clz}Manager ${clz?uncap_first}Manager) {
        this.${clz?uncap_first}Manager = ${clz?uncap_first}Manager;
    }
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
    }
    @Override
    public void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }
    public void testDefault() {
        assertNotNull(${clz?uncap_first}Manager);
    }
}
