package anni.asecurity.manager;

import java.util.List;

import anni.asecurity.domain.Role;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RoleManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(RoleManagerTest.class);
    private RoleManager roleManager;

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
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
        assertNotNull(roleManager);
    }

    public void testTwo() {
        List<Role> list = roleManager.getAll();
        assertEquals(0, list.size());
    }

    public void testSave() {
        Role role = new Role();
        role.setName("name");
        role.setDescn("descn");

        roleManager.save(role);
        assertNotNull(role);
    }

    public void testRemove() {
        Role role = new Role();
        role.setName("name");
        role.setDescn("descn");

        roleManager.remove(role);
        assertNotNull(role);
    }
}
