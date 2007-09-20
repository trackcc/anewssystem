package anni.core.json;

import java.io.*;

import java.util.*;

import anni.core.tree.*;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JsonUtilsTest extends TestCase {
    protected static Log logger = LogFactory.getLog(JsonUtilsTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testConstructor() {
        JsonUtils utils = new JsonUtils();
        assertNotNull(utils);
    }

    public void testWrite() throws Exception {
        LongTreeNode node = new LongTreeNode();
        StringWriter writer = new StringWriter();
        String[] excludes = new String[] {"class", "root", "parent"};
        JsonUtils.write(node, writer, excludes, null);

        assertEquals("{\"parentId\":0,\"theSort\":0,\"cls\":\"\",\"leaf\":true,\"qtip\":\"\",\"allowDelete\":true,\"allowEdit\":true,\"draggable\":true,\"id\":0,\"text\":\"\",\"allowChildren\":true,\"name\":\"\",\"children\":[]}",
            writer.toString());
    }

    public void testJson2Bean() throws Exception {
        String json = "{\"id\":1,\"name\":\"name\",\"theSort\":0}";
        String[] excludes = new String[] {"class", "root", "parent"};
        LongTreeNode node = JsonUtils.json2Bean(json, LongTreeNode.class,
                excludes, null);
        assertEquals(new Long(1L), node.getId());
        assertEquals("name", node.getName());
    }

    public void testJson2List() throws Exception {
        String json = "[{\"id\":1},{\"id\":2}]";
        String[] excludes = new String[] {"class", "root", "parent"};
        List<LongTreeNode> list = JsonUtils.json2List(json,
                LongTreeNode.class, excludes, null);
        assertEquals(2, list.size());
    }

    public void testJson2List2() throws Exception {
        String json = "[]";
        String[] excludes = new String[] {"class", "root", "parent"};
        List<LongTreeNode> list = JsonUtils.json2List(json,
                LongTreeNode.class, excludes, null);
        assertEquals(0, list.size());
    }
}
