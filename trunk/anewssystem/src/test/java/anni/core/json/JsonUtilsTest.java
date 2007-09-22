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

    public void testJson2Bean2() throws Exception {
        String json = "{\"id\":1,\"name\":\"name\",\"theSort\":0}";
        String[] excludes = new String[] {"class", "root", "parent"};
        LongTreeNode node = JsonUtils.json2Bean(json, LongTreeNode.class,
                excludes, null);
        assertEquals(new Long(1L), node.getId());
        assertEquals("name", node.getName());
    }

    public void testJson2Bean() throws Exception {
        String json = "{theByte:1,theShort:2,theInteger:3,theLong:4,theFloat:5,theDouble:6,theDate:'2007年09月23日',theBoolean:true}";
        String[] excludes = new String[] {"class", "root", "parent"};
        TestNode node = JsonUtils.json2Bean(json, TestNode.class,
                excludes, "yyyy年MM月dd日");
        assertEquals(new Byte((byte) 1), node.getTheByte());
        assertEquals(new Short((short) 2), node.getTheShort());
        assertEquals(new Integer(3), node.getTheInteger());
        assertEquals(new Long(4), node.getTheLong());
        assertEquals(new Float(5), node.getTheFloat());
        assertEquals(new Double(6), node.getTheDouble());
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

    public static class TestNode {
        private Byte theByte;
        private Short theShort;
        private Integer theInteger;
        private Long theLong;
        private Float theFloat;
        private Double theDouble;
        private Date theDate;

        // theBoolean
        private Boolean theBoolean;

        public Byte getTheByte() {
            return theByte;
        }

        public void setTheByte(Byte theByte) {
            this.theByte = theByte;
        }

        public Short getTheShort() {
            return theShort;
        }

        public void setTheShort(Short theShort) {
            this.theShort = theShort;
        }

        public Integer getTheInteger() {
            return theInteger;
        }

        public void setTheInteger(Integer theInteger) {
            this.theInteger = theInteger;
        }

        public Long getTheLong() {
            return theLong;
        }

        public void setTheLong(Long theLong) {
            this.theLong = theLong;
        }

        public Float getTheFloat() {
            return theFloat;
        }

        public void setTheFloat(Float theFloat) {
            this.theFloat = theFloat;
        }

        public Double getTheDouble() {
            return theDouble;
        }

        public void setTheDouble(Double theDouble) {
            this.theDouble = theDouble;
        }

        public Date getTheDate() {
            return theDate;
        }

        public void setTheDate(Date theDate) {
            this.theDate = theDate;
        }

        public Boolean getTheBoolean() {
            return theBoolean;
        }

        public void setTheBoolean(Boolean theBoolean) {
            this.theBoolean = theBoolean;
        }
    }
}
