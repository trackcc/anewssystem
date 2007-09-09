package anni.asecurity.web;

import anni.asecurity.domain.User;

import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UserControllerTest extends PrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(UserControllerTest.class);

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (UserController) ctx.getBean(
                "anni.asecurity.web.UserController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/user/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/asecurity/user/listUser");
    }

    public void testSelectRoles() throws Exception {
        request.setRequestURI("/user/selectRoles.htm");
        request.addParameter("userId", "1");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "user");
        mvHelper.assertModelAttributeAvailable(mv, "roles");
        mvHelper.assertViewName(mv, null);
    }

    public void testList() throws Exception {
        request.setRequestURI("/user/list.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "/asecurity/user/listUser");
    }

    public void testAuthRoles() throws Exception {
        request.setRequestURI("/user/authRoles.htm");
        request.addParameter("itemlist", "1");
        request.addParameter("itemlist", "2");
        session.setAttribute("userId", new Long(2L));
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, "forward:/user/selectRoles.htm");
    }

    public void testAuthRoles2() throws Exception {
        request.setRequestURI("/user/authRoles.htm");
        request.addParameter("itemlist", "1");
        request.addParameter("itemlist", "2");
        request.addParameter("auth", "true");
        session.setAttribute("userId", new Long(2L));
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, "forward:/user/selectRoles.htm");
    }

    public void testCreate() throws Exception {
        request.setRequestURI("/user/create.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "statusEnum");
        mvHelper.assertViewName(mv, "/asecurity/user/editUser");
    }

    public void testUpdate() throws Exception {
        request.setRequestURI("/user/update.htm");
        request.addParameter("id", "2");

        try {
            mv = controller.handleRequest(request, response);
            fail("failure");

            // mvHelper.assertModelAttributeAvailable(mv, "user");
            // mvHelper.assertViewName(mv, "redirect:/user/list.htm");
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testUpdate2() throws Exception {
        request.setRequestURI("/user/update.htm");
        request.addParameter("id", "2");
        request.addParameter("pswd", "test");
        request.addParameter("repeatpswd", "test");

        try {
            mv = controller.handleRequest(request, response);
            fail("failure");

            //mvHelper.assertModelAttributeAvailable(mv, "user");
            //mvHelper.assertViewName(mv, "redirect:/user/list.htm");
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testUpdate3() throws Exception {
        request.setRequestURI("/user/update.htm");
        request.addParameter("id", "2");
        request.addParameter("pswd", "test");
        request.addParameter("repeatpswd", "test1111111111111");

        try {
            mv = controller.handleRequest(request, response);
            fail("failure");

            //mvHelper.assertModelAttributeAvailable(mv, "user");
            //mvHelper.assertViewName(mv, "/asecurity/user/editUser");
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testInsert() throws Exception {
        request.setRequestURI("/user/insert.htm");
        request.addParameter("username", "test");
        request.addParameter("password", "test");

        mv = controller.handleRequest(request, response);

        mvHelper.assertViewName(mv, null);
    }
}
