
package ${pkg}.web;

import anni.core.test.AbstractWebTests;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ${pkg}.domain.${clz};

public class ${clz}ControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(${clz}ControllerTest.class);
    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (${clz}Controller) applicationContext.getBean(
                "${pkg}.web.${clz}Controller");
    }
    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }
    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/${clz?lower_case}/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/${projectName}/${clz?lower_case}/list${clz}");
    }
}
