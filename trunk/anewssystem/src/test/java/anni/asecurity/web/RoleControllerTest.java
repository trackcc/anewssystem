package anni.asecurity.web;

import anni.asecurity.domain.Role;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RoleControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(RoleControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (RoleController) applicationContext.getBean(
                "anni.asecurity.web.RoleController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/role/index.htm");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "asecurity/role/index");
    }

    public void testGetResources() throws Exception {
        request.setRequestURI("/role/getResources.htm");
        request.addParameter("roleId", "1");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "resources");
        mvHelper.assertViewName(mv, null);
    }

    public void testAuth() throws Exception {
        request.setRequestURI("/role/auth.htm");
        request.addParameter("ids", "1,2");
        session.setAttribute("roleId", new Long(2L));
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }

    public void testAuth2() throws Exception {
        request.setRequestURI("/role/auth.htm");
        request.addParameter("ids", "1,2");
        request.addParameter("isAuth", "true");
        session.setAttribute("roleId", new Long(2L));
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }
}
