package anni.core.dao.support;

import java.util.*;

import junit.framework.TestCase;


public class PageTest extends TestCase {
    Page page;

    @Override
    protected void setUp() {
        page = new Page();
    }

    @Override
    protected void tearDown() {
    }

    public void testPage() {
        assertNotNull(page);
        assertEquals(0, page.getTotalCount());
        assertEquals(0, page.getTotalPageCount());
        assertEquals(15, page.getPageSize());
        assertTrue(page.getResult() instanceof ArrayList);

        List list = (List) page.getResult();
        assertEquals(0, list.size());
        assertEquals(1, page.getCurrentPageNo());
        assertFalse(page.hasNextPage());
        assertFalse(page.hasPreviousPage());

        assertEquals(0, page.getStartOfPage(1));
        assertEquals(15, page.getStartOfPage(2, 15));
    }

    public void testCustomPage() {
        List<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        page = new Page(0, list.size(), 5, list);

        assertEquals(10, page.getTotalCount());
        assertEquals(2, page.getTotalPageCount());
        assertEquals(5, page.getPageSize());
        assertEquals(list, page.getResult());
        assertEquals(1, page.getCurrentPageNo());
        assertTrue(page.hasNextPage());
        assertFalse(page.hasPreviousPage());
    }
}
