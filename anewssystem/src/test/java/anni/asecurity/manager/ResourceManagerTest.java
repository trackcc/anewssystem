package anni.asecurity.manager;

import java.util.List;

import anni.asecurity.domain.Resource;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ResourceManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(ResourceManagerTest.class);
    private ResourceManager resourceManager;

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
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
        assertNotNull(resourceManager);
    }

    public void testTwo() {
        List<Resource> list = resourceManager.getAll();
        assertNotNull(list);
    }

    public void testSave() {
        Resource resource = new Resource("resource1", "URL", "/**", "");
        resourceManager.save(resource);
        assertNotNull(resource.getId());
    }

    public void testRemove() {
        Resource resource = new Resource("resource1", "URL", "/**", "");
        resourceManager.save(resource);
        assertNotNull(resource.getId());

        resourceManager.remove(resource);
        assertNotNull(resource);
    }
}
