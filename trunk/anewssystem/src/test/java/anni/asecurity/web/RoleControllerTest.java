package anni.asecurity.web;

import anni.asecurity.domain.Role;

import anni.core.test.AbstractPrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RoleControllerTest extends AbstractPrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(RoleControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (RoleController) ctx.getBean(
                "anni.asecurity.web.RoleController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/role/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/asecurity/role/listRole");
    }

    public void testSelectResources() throws Exception {
        request.setRequestURI("/role/selectResources.htm");
        request.addParameter("roleId", "1");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "resources");
        mvHelper.assertViewName(mv, "/asecurity/role/selectResources");
    }

    public void testAuthResources() throws Exception {
        request.setRequestURI("/role/authResources.htm");
        request.addParameter("itemlist", "1");
        request.addParameter("itemlist", "2");
        session.setAttribute("roleId", new Long(2L));
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, "forward:/role/selectResources.htm");
    }

    public void testAuthResources2() throws Exception {
        request.setRequestURI("/role/authResources.htm");
        request.addParameter("itemlist", "1");
        request.addParameter("itemlist", "2");
        request.addParameter("auth", "true");
        session.setAttribute("roleId", new Long(2L));
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, "forward:/role/selectResources.htm");
    }
}
