package anni.anews.web.support;

import java.io.*;

import java.util.*;

import anni.anews.domain.*;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ExtTreeNodeTest extends TestCase {
    protected static Log logger = LogFactory.getLog(ExtTreeNodeTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Category category = new Category();
        category.setId(1L);
        category.setName("name");

        ExtTreeNode extTreeNode = ExtTreeNode.fromCategory(category);

        assertEquals(1L, extTreeNode.getId());
        assertEquals("name", extTreeNode.getText());
        assertTrue(extTreeNode.getAllowEdit());
        assertTrue(extTreeNode.getDraggable());
        assertTrue(extTreeNode.getAllowDelete());

        assertFalse(extTreeNode.getLeaf());
        assertNull(extTreeNode.getCls());
        assertTrue(extTreeNode.getAllowChildren());

        extTreeNode.setCls("cls");
        assertEquals("cls", extTreeNode.getCls());
    }

    public void testWrite() throws Exception {
        List<Category> categoryList = new ArrayList<Category>();
        StringWriter writer = new StringWriter();
        ExtTreeNode.write(writer, categoryList);
        writer.flush();

        String result = writer.toString();
        assertEquals("[]", result);
    }

    public void testWrite2() throws Exception {
        List<Category> categoryList = new ArrayList<Category>();
        Category category = new Category();
        category.setId(1L);
        category.setName("name");
        categoryList.add(category);

        StringWriter writer = new StringWriter();
        ExtTreeNode.write(writer, categoryList);
        writer.flush();

        String result = writer.toString();
        assertEquals("[{\"text\":\"name\",\"allowChildren\":true,\"leaf\":false,\"cls\":\"\",\"allowDelete\":true,\"draggable\":true,\"allowEdit\":true,\"id\":1}]",
            result);
    }
}
