package anni.core.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import anni.core.utils.GenericsUtils;

import anni.core.web.prototype.ExtendController;
import anni.core.web.prototype.StreamView;

import org.apache.commons.lang.StringUtils;

import org.springframework.util.Assert;

import org.springframework.web.servlet.ModelAndView;


/**
 * 操作json的controller.
 *
 * @author Lingo
 * @since 2007-09-19
 * @param <T> bean
 * @param <D> dao
 */
public class JsonController<T, D> extends ExtendController {
    /** * json视图. */
    public static final StreamView JSON_VIEW = new StreamView(
            "application/json");

    /** * json默认编码. */
    public static final String JSON_DEFAULT_ENCODING = "UTF-8";

    /** * DAO所管理的Entity类型. */
    protected Class<T> entityClass;

    /** * 对应的dao. */
    protected D entityDao;

    /** * 响应编码. */
    protected String responseEncoding = JSON_DEFAULT_ENCODING;

    /**
     * 构造方法，初始化一系列泛型参数.
     */
    public JsonController() {
        // 根据T,反射获得entityClass
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    /**
     * @see MultiActionController#handleRequest.
     * @param request 请求
     * @param response 响应
     * @return ModelAndView mv
     * @throws Exception 异常
     */
    @Override
    protected ModelAndView handleRequestInternal(
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        response.setCharacterEncoding(getResponseEncoding());
        super.handleRequestInternal(request, response);

        if (mv.getViewName() == null) {
            mv.setView(JSON_VIEW);
        }

        return mv;
    }

    /**
     * 取得entityClass.
     * JDK1.4不支持泛型的子类可以抛开Class&lt;T&gt; entityClass
     * 重载此函数达到相同效果。
     *
     * @return Class 具体类型
     */
    protected Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 获得EntityDao类进行CRUD操作,可以在子类重载.
     * @return D 实体dao
     */
    protected D getEntityDao() {
        Assert.notNull(entityDao, "dao未能成功初始化");

        return entityDao;
    }

    /**
     * 为spring留个接口，注入EntityDao.
     *
     * @param entityDao EntityDao
     */
    public void setEntityDao(D entityDao) {
        this.entityDao = entityDao;
    }

    /**
     * 回调函数，声明CommandName--对象的名字,默认为首字母小写的类名.
     * 问题是hibernate用cglib对类进行加强，得到的类名就变成resource$$EnhancerByCGLIB$$3daeb8bf
     * 为了这个问题晚上两点还在翻spring的代码，真是晕哦
     *
     * @see #bindObject(HttpServletRequest, Object)
     * @param command 对象
     * @return 对象名称
     */
    @Override
    protected String getCommandName(Object command) {
        return StringUtils.uncapitalize(getEntityClass().getSimpleName());
    }

    /**
     * getDatePattern().
     *
     * @return 格式化日期类型的字符串
     */
    public String getDatePattern() {
        return "yyyy年MM月dd日";
    }

    /** * @return encoding. */
    public String getResponseEncoding() {
        return responseEncoding;
    }

    /** * @param responseEncoding String. */
    public void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }
}
