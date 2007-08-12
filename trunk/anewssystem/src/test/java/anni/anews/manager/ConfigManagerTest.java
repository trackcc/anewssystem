package anni.anews.manager;

import anni.anews.domain.Config;

import anni.core.test.AbstractDaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConfigManagerTest extends AbstractDaoTestCase {
    protected static Log logger = LogFactory.getLog(ConfigManagerTest.class);
    private ConfigManager configManager;

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
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
        assertNotNull(configManager);
    }
}
