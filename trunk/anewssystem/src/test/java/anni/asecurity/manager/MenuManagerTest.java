package anni.asecurity.manager;

import java.util.List;

import anni.asecurity.domain.Menu;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MenuManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(MenuManagerTest.class);
    private MenuManager menuManager;

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
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
        assertNotNull(menuManager);
    }

    public void testLoadTopMenus() {
        List<Menu> list = menuManager.loadTops("theSort", "asc");
        assertEquals(0, list.size());
    }

    public void testAdminMenus() {
        List<Integer> list = menuManager.loadUserMenus(1L);
        assertEquals(0, list.size());
    }

    public void testEmployeeMenus() {
        List<Integer> list = menuManager.loadUserMenus(2L);
        assertEquals(0, list.size());
    }
}
