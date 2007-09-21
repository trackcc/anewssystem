package anni.asecurity.web;

import anni.asecurity.domain.User;

import anni.core.test.AbstractPrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UserControllerTest extends AbstractPrototypeControllerTest {
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
        request.setRequestURI("/user/index.htm");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "asecurity/user/index");
    }

    public void testGetRoles() throws Exception {
        request.setRequestURI("/user/getRoles.htm");
        request.addParameter("id", "1");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "user");
        //mvHelper.assertModelAttributeAvailable(mv, "roles");
        mvHelper.assertViewName(mv, null);
        assertEquals("", response.getContentAsString());
    }

    /*
        public void testList() throws Exception {
            request.setRequestURI("/user/list.htm");
            mv = controller.handleRequest(request, response);
            mvHelper.assertModelAttributeAvailable(mv, "page");
            mvHelper.assertViewName(mv, "/asecurity/user/listUser");
        }
    */
    public void testAuth() throws Exception {
        request.setRequestURI("/user/auth.htm");
        request.addParameter("ids", "1,2");
        request.addParameter("userId", "2");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }

    public void testAuth2() throws Exception {
        request.setRequestURI("/user/auth.htm");
        request.addParameter("ids", "1,2");
        request.addParameter("userId", "2");
        request.addParameter("isAuth", "true");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }

    /*
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
    */
}
