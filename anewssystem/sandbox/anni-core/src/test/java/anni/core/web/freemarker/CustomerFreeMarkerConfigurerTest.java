package anni.core.web.freemarker;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;


public class CustomerFreeMarkerConfigurerTest extends TestCase {
    CustomerFreeMarkerConfigurer conf = null;
    Configuration c;

    @Override
    protected void setUp() {
        conf = new CustomerFreeMarkerConfigurer();
    }

    @Override
    protected void tearDown() {
    }

    public void testDefault() throws Exception {
        assertNotNull(conf);

        c = EasyMock.createMock(Configuration.class);
        c.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        EasyMock.replay(c);
        conf.postProcessConfiguration(c);
        EasyMock.verify();
    }
}
