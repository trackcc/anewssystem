package anni.asecurity.manager;

import java.util.List;

import anni.asecurity.domain.Role;
import anni.asecurity.domain.User;

import anni.core.test.AbstractWebTests;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UserManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(UserManagerTest.class);
    private UserManager userManager;
    private RoleManager roleManager;
    private Md5PasswordEncoder encoder;

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public void setPasswordEncoder(Md5PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        userManager.removeAll();
    }

    @Override
    public void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() {
        assertNotNull(userManager);
    }

    public void testTwo() {
        List<User> list = userManager.getAll();
        assertEquals(0, list.size());
    }

    public void testGetUserByLoginidAndPasswd() {
        User user = userManager.getUserByLoginidAndPasswd("admin", "admin");
        assertNull(user);
    }

    public void testGetUserByLoginidAndPasswd2() {
        User user = userManager.getUserByLoginidAndPasswd("admin",
                encoder.encodePassword("admin", null));
        assertNull(user);
    }

    public void testSave() {
        User user = new User();

        userManager.save(user);
        assertNotNull(user);
    }

    public void testInsert() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setStatus(User.STATUS_ENABLED);
        userManager.save(user);
        assertNotNull(user.getId());
    }

    public void testRemove() {
        User user = new User();
        userManager.save(user);

        userManager.remove(user);
        assertNotNull(user);
    }
}
