package anni.core.web.freemarker;

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


/**
 * 把freemarker使用的ObjectWrapper由默认的DefaultObjectWrapper替换成ObjectsWrapper.
 * 为的就是可以使用map的get方法
 *
 * @author Lingo
 * @since 2007-06-08
 */
public class CustomerFreeMarkerConfigurer extends FreeMarkerConfigurer {
    /**
     * 设置BEANS_WRAPPER.
     *
     * @param config Configuration
     * @throws IOException io
     * @throws TemplateException template
     */
    @Override
    protected void postProcessConfiguration(Configuration config)
        throws IOException, TemplateException {
        config.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
    }
}
