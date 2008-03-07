package anni.core.web.prototype;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import anni.core.dao.EntityDao;
import anni.core.dao.support.Page;

import anni.core.utils.GenericsUtils;

import org.apache.commons.lang.StringUtils;

import org.extremecomponents.table.limit.Limit;

import org.springframework.beans.BeanUtils;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.util.Assert;

import org.springframework.validation.BindingResult;

import org.springframework.web.servlet.ModelAndView;


/**
 * SpringSide 基于命名规则的CRUD Controller基类.
 * 结合了Multi-Action 与 Form Controller的优点，
 * 本类演示的仅仅是封装的一种可能性,大家可以根据自己的项目自由扩展或重定义.
 * 来自www.springside.org.cn
 *
 *
 * @author calvin
 * @param <T> 实体类
 * @param <D> 实体类dao
 */
public class BaseController<T, D extends EntityDao<T>>
    extends ExtendController {
    /**
     * DAO所管理的Entity类型.
     */
    protected Class<T> entityClass;

    /**
     * 对应的dao.
     */
    protected D entityDao;

    /**
     * list()方法使用的view.
     */
    protected String listView;

    /**
     * edit()方法使用的view.
     */
    protected String editView;

    /**
     * show()方法使用的view.
     */
    protected String showView;

    /**
     * success()方法使用的view.
     */
    protected String successView;

    /**
     * 构造方法，初始化一系列泛型参数.
     */
    public BaseController() {
        // 根据T,反射获得entityClass
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());

        String entityClassName = getEntityClass()
                                     .getSimpleName();

        // String uncap = StringUtils.uncapitalize(entityClassName);
        String uncap = entityClassName.toLowerCase(Locale.CHINA);

        listView = "/" + uncap + "/list" + entityClassName;
        editView = "/" + uncap + "/edit" + entityClassName;
        showView = "/" + uncap + "/show" + entityClassName;
        successView = "redirect:/" + uncap + "/list.htm";
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
     * @param entityDaoIn EntityDao
     */
    public void setEntityDao(D entityDaoIn) {
        entityDao = entityDaoIn;
    }

    /**
     * 设置listView.
     * @param listViewIn listView
     */
    public void setListView(String listViewIn) {
        listView = listViewIn;
    }

    /**
     * 设置editView.
     * @param editViewIn editView
     */
    public void setEditView(String editViewIn) {
        editView = editViewIn;
    }

    /**
     * 设置successView.
     * @param successViewIn successView
     */
    public void setSuccessView(String successViewIn) {
        successView = successViewIn;
    }

    /**
     * 设置successView.
     * @param showViewIn showView
     */
    public void setShowView(String showViewIn) {
        showView = showViewIn;
    }

    /**
     * 设置默认函数为list().
     *
     * @throws Exception 异常
     */
    public void index() throws Exception {
        list();
    }

    /**
     * 显示对象列表页面的模板方法.
     *
     * @throws Exception 异常
     */
    public void list() throws Exception {
        mv = new ModelAndView(listView);

        Limit limit = ExtremeTablePage.getLimit(request,
                Page.DEFAULT_PAGE_SIZE);

        Page page = getEntityDao()
                        .pagedQuery(limit.getPage(),
                limit.getCurrentRowsDisplayed(), null,
                ExtremeTablePage.getSort(limit));
        mv.addObject("page", page.getResult());
        mv.addObject("totalRows",
            Integer.valueOf((int) page.getTotalCount()));
        referenceData(mv.getModel());
        onList();
    }

    /**
     * 显示创建新对象的输入页面的模板方法.
     *
     * @throws Exception 异常
     */
    public void create() throws Exception {
        // 这里仅仅是简单的跳转，实际中可能需要添加父子关系对应的数据
        mv = new ModelAndView(editView);
        referenceData(mv.getModel());
        onCreate();
    }

    /**
     * 保存新增对象的模板方法.
     *
     * @throws Exception 异常
     */
    public void insert() throws Exception {
        mv = new ModelAndView(successView);

        T entity = (T) BeanUtils.instantiateClass(getEntityClass());

        // 进行数据绑定，与数据校验
        BindingResult result = bindObject(request, entity);

        if (result.hasErrors()) {
            //mv.setViewName(editView);
            //mv.addAllObjects(result.getModel());
            //referenceData(mv.getModel());
            onBindError(result, entity);

            return;
        }

        entityDao.save(entity);
        mv.addObject(entity);
        saveMessage("数据新增成功!");
        onInsert();
    }

    /**
     * 显示对象修改时的输入页面的模板方法.
     *
     * @throws Exception 异常
     */
    public void edit() throws Exception {
        mv = new ModelAndView(editView);

        // 根据id获得需要修改的实体类
        Serializable id = getPrimaryKey();
        T entity = getEntityDao().get(id);
        mv.addObject(entity);
        referenceData(mv.getModel());
        onEdit();
    }

    /**
     * 显示对象详情页面的模板方法。该页面中的对象属性不能修改.
     *
     * @throws Exception 异常
     */
    public void show() throws Exception {
        mv = new ModelAndView(showView);

        // 根据id获得需要修改的实体类
        Serializable id = getPrimaryKey();
        T entity = getEntityDao().get(id);
        mv.addObject(entity);
        referenceData(mv.getModel());
        onShow();
    }

    /**
     * 保存对象更新的模板方法.
     *
     * @throws Exception 异常
     */
    public void update() throws Exception {
        mv = new ModelAndView(successView);

        Serializable id = getPrimaryKey();
        T entity = getEntityDao().get(id);

        // 进行数据绑定，与数据校验
        BindingResult result = bindObject(request, entity);

        if (result.hasErrors()) {
            //mv.setViewName(editView);
            //mv.addObject(getEntityDao().get(id));
            //mv.addAllObjects(result.getModel());
            //referenceData(mv.getModel());
            onBindError(result, entity);

            return;
        }

        entityDao.save(entity);
        saveMessage("数据修改成功!");
        mv.addObject(entity);
        onUpdate();
    }

    /**
     * 查询对象的模板方法.
     *
     * @throws Exception 异常
     */
    public void query() throws Exception {
        mv = new ModelAndView(listView);
        referenceData(mv.getModel());
        onQuery();
    }

    /**
     * 删除选择的对象的模板方法.
     *
     * @throws Exception 异常
     */
    public void removeAll() throws Exception {
        mv = new ModelAndView(successView);

        //String[] ids = request.getParameterValues("itemlist");
        Serializable[] ids = getPrimaryKeys();
        int success = 0;

        if (ids != null) {
            for (Serializable id : ids) {
                try {
                    getEntityDao().removeById(id);
                    success++;
                } catch (DataIntegrityViolationException e) {
                    saveMessage(onRemoveSelectedFailure(id));
                }
            }

            saveMessage("成功删除" + success + "条纪录!");
        }

        onRemoveAll();
    }

    /**
     * 根据id删除一条记录.
     *
     * @throws Exception 异常
     */
    public void remove() throws Exception {
        mv = new ModelAndView(successView);

        Serializable id = getPrimaryKey();

        try {
            getEntityDao().removeById(id);
            saveMessage("成功删除纪录!");
        } catch (DataIntegrityViolationException ex) {
            saveMessage(onRemoveSelectedFailure(id));
        }

        onRemove();
    }

    // ----------------------------------------------------
    // 子类实现的回调方法
    // ----------------------------------------------------
    /** * onList. */
    protected void onList() throws Exception {
    }

    /** * onCreate. */
    protected void onCreate() throws Exception {
    }

    /** * onInsert. */
    protected void onInsert() throws Exception {
    }

    /** * onEdit. */
    protected void onEdit() throws Exception {
    }

    /** * onUpdate. */
    protected void onUpdate() throws Exception {
    }

    /** * onRemove. */
    protected void onRemove() throws Exception {
    }

    /** * onRemoveAll. */
    protected void onRemoveAll() throws Exception {
    }

    /** * onShow. */
    protected void onShow() throws Exception {
    }

    /** * onQuery. */
    protected void onQuery() throws Exception {
    }

    /**
     * 回调函数.校验出错时,将出错的对象及出错信息放回model.
     * 在{@link #onInsert}和{@link #onUpdate}中调用.
     */
    protected void onBindError(BindingResult result, T entity) {
        mv.setViewName(editView);
        mv.addAllObjects(result.getModel());
        referenceData(mv.getModel());
    }

    /**
     * 在删除选中的记录失败时，根据指定id返回失败信息.
     * @param id 失败记录的id
     * @return 失败信息
     */
    protected String onRemoveSelectedFailure(Serializable id) {
        return "〈" + getEntityDao().get(id) + "〉与其他数据关联，删除失败";
    }

    /**
     * 设置相关数据.
     *
     * @param model ModelAndView中的数据模型
     */
    protected void referenceData(Map model) {
    }

    /**
     * @return 从request中获得pojo的id.
     */
    protected Serializable getPrimaryKey() {
        return getLongParam("id", -1L);
    }

    /**
     * @return 从request中获得pojo的id数组.
     */
    protected Serializable[] getPrimaryKeys() {
        return getLongParams("itemlist");
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
}
