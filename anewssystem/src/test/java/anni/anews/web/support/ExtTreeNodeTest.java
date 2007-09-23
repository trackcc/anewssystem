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
        NewsCategory category = new NewsCategory();
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

        extTreeNode.setLeaf(true);
        assertTrue(extTreeNode.getLeaf());

        assertNotNull(extTreeNode.getQtip());
        extTreeNode.setQtip("qtip");
        assertEquals("qtip", extTreeNode.getQtip());
    }

    public void testWrite() throws Exception {
        List<NewsCategory> categoryList = new ArrayList<NewsCategory>();
        StringWriter writer = new StringWriter();
        ExtTreeNode.write(writer, categoryList);
        writer.flush();

        String result = writer.toString();
        assertEquals("[]", result);
    }

    /*
        public void testWrite2() throws Exception {
            List<NewsCategory> categoryList = new ArrayList<NewsCategory>();
            NewsCategory category = new NewsCategory();
            category.setId(1L);
            category.setName("name");
            categoryList.add(category);
    
            StringWriter writer = new StringWriter();
            ExtTreeNode.write(writer, categoryList);
            writer.flush();
    
            String result = writer.toString();
            //assertEquals("", result);
            assertEquals("[{\"text\":\"name\",\"allowChildren\":true,\"leaf\":false,\"cls\":\"\",\"qtip\":\"tooltip\",\"allowDelete\":true,\"draggable\":true,\"allowEdit\":true,\"id\":1}]",
                result);
        }
    */
}
