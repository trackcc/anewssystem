package anni.asecurity.menu;

import java.util.Collection;
import java.util.List;

import anni.asecurity.domain.*;

import anni.core.test.PrototypeControllerTest;

import net.sf.navigator.menu.MenuRepository;


public class MenuFactoryTest extends PrototypeControllerTest {
    MenuFactory menuFactory = null;

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        menuFactory = (MenuFactory) ctx.getBean("menuFactory");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testCreator() {
        Object result = menuFactory.creator(MenuFactory.OUTLOOK_LIKE_MENU,
                2L);
        MenuRepository repo = (MenuRepository) result;
        assertNotNull(repo);
        assertEquals(0, repo.getTopMenus().size());
    }
}
