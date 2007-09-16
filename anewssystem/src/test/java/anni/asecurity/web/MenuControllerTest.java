package anni.asecurity.web;

import anni.asecurity.domain.Menu;

import anni.asecurity.manager.*;

import anni.core.test.PrototypeControllerTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MenuControllerTest extends PrototypeControllerTest {
    protected static Log logger = LogFactory.getLog(MenuControllerTest.class);
    private UserManager userManager = null;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (MenuController) ctx.getBean(
                "anni.asecurity.web.MenuController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/menu/index.htm");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "asecurity/menu/index");
    }

    //public void testCreate() throws Exception {
    //    request.setRequestURI("/menu/create.htm");
    //    mv = controller.handleRequest(request, response);
    //    mvHelper.assertModelAttributeAvailable(mv, "parents");
    //    mvHelper.assertViewName(mv, "/asecurity/menu/editMenu");
    //}

    //public void testUpdate() throws Exception {
    //    request.setRequestURI("/menu/update.htm");
    //    request.addParameter("parent_id", "1");
    //    request.addParameter("id", "3");
    //    try {
    //        mv = controller.handleRequest(request, response);
    //        fail("不可能实现");
    //    } catch (Exception ex) {
    //        assertTrue(true);
    //    }
    //}
    public void testCreateMenu() throws Exception {
        request.setRequestURI("/menu/createMenu.htm");
        session.setAttribute("loginUser", userManager.get(1L));
        mv = controller.handleRequest(request, response);
        // mvHelper.assertModelAttributeAvailable(mv, "repository");
        mvHelper.assertViewName(mv, "asecurity/menu/createMenu");
    }

    /*
        public void testListMenuRole() throws Exception {
            request.setRequestURI("/menu/listMenuRole.htm");
            request.addParameter("roleId", "1");
            mv = controller.handleRequest(request, response);
            mvHelper.assertModelAttributeAvailable(mv, "roleId");
            mvHelper.assertModelAttributeAvailable(mv, "treeText");
            mvHelper.assertViewName(mv, null);
        }
    public void testListMenuRole2() throws Exception {
        request.setRequestURI("/menu/listMenuRole.htm");
        request.addParameter("roleId", "");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "roleId");
        mvHelper.assertModelAttributeAvailable(mv, "treeText");
        mvHelper.assertViewName(mv, null);
    }
    public void testListMenuRole3() throws Exception {
        request.setRequestURI("/menu/listMenuRole.htm");
        request.addParameter("roleId", "not numeric");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "roleId");
        mvHelper.assertModelAttributeAvailable(mv, "treeText");
        mvHelper.assertViewName(mv, null);
    }
    */
    public void testSaveMenuRole() throws Exception {
        request.setRequestURI("/menu/saveMenuRole.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertModelAttributeAvailable(mv, "roleId");
        mvHelper.assertViewName(mv, "forward:/menu/listMenuRole.htm");
    }

    public void testSaveMenuRole2() throws Exception {
        request.setRequestURI("/menu/saveMenuRole.htm");
        request.addParameter("roleId", "2");
        request.addParameter("itemselect", "1,2");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv,
            "forward:/menu/listMenuRole.htm?roleId=2");
    }
}
