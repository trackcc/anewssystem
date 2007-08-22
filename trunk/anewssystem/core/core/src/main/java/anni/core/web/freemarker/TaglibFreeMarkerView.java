package anni.core.web.freemarker;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.ext.jsp.TaglibFactory;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;


/**
 * @version $Id: TaglibFreeMarkerView.java,v 1.1 2005/07/04 15:02:14 turelto Exp $
 */
public class TaglibFreeMarkerView extends FreeMarkerView {
    /**
     * ServletContextModel.
     */
    private ServletContextHashModel servletContextModel;

    /**
     * TaglibFactory.
     */
    private TaglibFactory taglibFactory;

    /**
     * @param servletContextModelIn ServletContextModel.
     */
    public void setServletContextModel(
        ServletContextHashModel servletContextModelIn) {
        servletContextModel = servletContextModelIn;
    }

    /**
     * @param taglibFactoryIn TaglibFactory.
     */
    public void setTaglibFactory(TaglibFactory taglibFactoryIn) {
        taglibFactory = taglibFactoryIn;
    }

    /**
     * 开始渲染.
     *
     * @param model Map
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     */
    protected void doRender(Map model, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        exposeModelAsRequestAttributes(model, request);

        if (servletContextModel != null) {
            model.put(FreemarkerServlet.KEY_APPLICATION,
                servletContextModel);
        }

        if (taglibFactory != null) {
            model.put(FreemarkerServlet.KEY_JSP_TAGLIBS, taglibFactory);
        }

        model.put(FreemarkerServlet.KEY_REQUEST,
            new HttpRequestHashModel(request, response,
                ObjectWrapper.DEFAULT_WRAPPER));

        super.doRender(model, request, response);
    }

    /**
     * 把模型中的数据导出到request的attributes中.
     *
     * @param model 模型
     * @param request 请求
     * @throws Exception 异常
     */
    protected void exposeModelAsRequestAttributes(Map model,
        HttpServletRequest request) throws Exception {
        Iterator it = model.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();

            if (!(entry.getKey() instanceof String)) {
                throw new ServletException("Invalid key ["
                    + entry.getKey()
                    + "] in model Map - only Strings allowed as model keys");
            }

            String modelName = (String) entry.getKey();
            Object modelValue = entry.getValue();

            if (modelValue != null) {
                request.setAttribute(modelName, modelValue);

                if (logger.isDebugEnabled()) {
                    logger.debug("Added model object '" + modelName
                        + "' of type [" + modelValue.getClass().getName()
                        + "] to request in TaglibFreeMarkerView '"
                        + getBeanName() + "'");
                }
            } else {
                request.removeAttribute(modelName);

                if (logger.isDebugEnabled()) {
                    logger.debug("Removed model object '" + modelName
                        + "' from request in TaglibFreeMarkerView '"
                        + getBeanName() + "'");
                }
            }
        }
    }
}
