package anni.core.web.freemarker;

import freemarker.ext.jsp.TaglibFactory;

import freemarker.ext.servlet.ServletContextHashModel;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;


/**
 * @since 2007-04-17.
 */
public class TaglibFreeMarkerViewResolver
    extends AbstractTemplateViewResolver {
    /**
     * ServletContextModel.
     */
    private ServletContextHashModel servletContextModel;

    /**
     * TaglibFactory.
     */
    private TaglibFactory taglibFactory;

    /**
    * Sets default viewClass to TaglibFreeMarkerView.
    * @see #setViewClass
    */
    public TaglibFreeMarkerViewResolver() {
        setViewClass(TaglibFreeMarkerView.class);
    }

    /**
     * @param servletContextModelIn ServletContextModel.
     */
    public void setServletContextModel(
        ServletContextHashModel servletContextModelIn) {
        servletContextModel = servletContextModelIn;
    }

    /**
     * @param taglibFactoryIn taglibFactoryIn.
     */
    public void setTaglibFactory(TaglibFactory taglibFactoryIn) {
        taglibFactory = taglibFactoryIn;
    }

    /**
     * Requires TaglibFreeMarkerView.
     * @see TaglibFreeMarkerView
     * @return Class view class
     */
    protected Class requiredViewClass() {
        return TaglibFreeMarkerView.class;
    }

    /**
     * @param viewName view name.
     * @return AbstracturlBasedView view
     * @throws Exception Exception
     */
    protected AbstractUrlBasedView buildView(String viewName)
        throws Exception {
        TaglibFreeMarkerView view = (TaglibFreeMarkerView) super
            .buildView(viewName);
        view.setTaglibFactory(this.taglibFactory);
        view.setServletContextModel(this.servletContextModel);

        return view;
    }
}
