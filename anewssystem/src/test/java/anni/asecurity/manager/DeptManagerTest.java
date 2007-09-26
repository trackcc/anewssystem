package anni.asecurity.manager;

import java.util.List;

import anni.asecurity.domain.Dept;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DeptManagerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(DeptManagerTest.class);
    private DeptManager deptManager;

    public void setDeptManager(DeptManager deptManager) {
        this.deptManager = deptManager;
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
        assertNotNull(deptManager);
    }

    public void testSize() {
        List<Dept> list = deptManager.getAll();
        assertEquals(0, list.size());
    }

    public void testLoadTopDepts() {
        List<Dept> list = deptManager.loadTops("id", "asc");
        assertEquals(0, list.size());
    }
}
