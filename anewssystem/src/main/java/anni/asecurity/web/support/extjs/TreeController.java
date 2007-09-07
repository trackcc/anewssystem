package anni.asecurity.web.support.extjs;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import anni.core.dao.HibernateTreeEntityDao;

import anni.core.web.prototype.BaseController;
import anni.core.web.prototype.StreamView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;


public class TreeController<T extends LongSortedTreeEntityBean<T>, D extends HibernateTreeEntityDao<T>>
    extends BaseController<T, D> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(TreeController.class);

    /**
     * 绑定选中的parent menu.
     *
     * @param request 请求
     * @param command 需要绑定的command
     * @param binder 绑定工具
     * @throws Exception 异常
     */
    @Override
    protected void preBind(HttpServletRequest request, Object command,
        ServletRequestDataBinder binder) throws Exception {
        binder.setDisallowedFields(new String[] {"parent_id"});

        T entity = (T) command;

        try {
            Long id = getLongParam("parent_id", -1L);

            if (id != -1L) {
                T parent = entityDao.get(id);

                // check dead lock -- 不允许将自表外键关系设置成环状
                if (entity.checkDeadLock(parent)) {
                    binder.getBindingResult()
                          .rejectValue("parent", "不能把父节点设置为子节点的叶子",
                        new Object[0], "不能把父节点设置为子节点的叶子");
                } else {
                    entity.setParent(parent);
                }
            }
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
    }

    /**
     * 向模型中设置关联数据.
     *
     * @param model ModelAndView中的数据模型
     */
    @Override
    protected void referenceData(Map model) {
        model.put("parents", entityDao.getAll());
    }

    /**
     * 一次性获得整棵树的数据.
     *
     * @throws Exception 异常
     */
    public void getAllTree() throws Exception {
        String hql = "from " + getEntityClass().getSimpleName()
            + " where parent is null order by theSort asc, id desc";
        List<T> list = getEntityDao().find(hql);

        String json = TreeHelper.createAllTreeJson(list);

        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());

        StreamView view = new StreamView("application/json");
        mv.setView(view);
    }

    /**
     * 根据传入参数，返回对应id分类的子分类，用于显示分类的树形结构.
     * 如果没有指定id的值，则返回所有根节点
     *
     * @throws Exception 异常
     */
    public void getChildren() throws Exception {
        long parentId = getLongParam("node", -1L);
        List<T> list = null;
        String hql = "from " + getEntityClass().getSimpleName() + " ";
        String orderBy = " order by theSort asc, id desc";

        if (parentId == -1L) {
            // 根节点
            list = getEntityDao()
                       .find(hql + "where parent is null" + orderBy);
        } else {
            list = getEntityDao()
                       .find(hql + "where parent.id=?" + orderBy, parentId);
        }

        response.setCharacterEncoding("UTF-8");

        String json = TreeHelper.createTreeJson(list);
        response.getWriter().print(json);

        StreamView view = new StreamView("application/json");
        mv.setView(view);
    }

    /**
     * 根据id获取一条记录.
     */
    public void loadData() throws Exception {
        long id = getLongParam("id", -1L);
        T entity = getEntityDao().get(id);

        if (entity != null) {
            response.setCharacterEncoding("UTF-8");

            List<T> list = new ArrayList<T>();
            list.add(entity);
            TreeHelper.write(list, response.getWriter(), getExcludes(),
                getDatePattern());
        }

        mv.setView(new StreamView("application/json"));
    }

    /**
     * 添加一个分类.
     * 目前还不没有返回成功或者错误信息
     * 校验方式，当输入的data参数不存在的时候，直接返回
     *
     * @throws Exception 异常
     */
    public void insertTree() throws Exception {
        String data = getStrParam("data", "");
        logger.info(data);

        if (data.equals("")) {
            mv.setView(new StreamView());

            return;
        }

        // data={"id":1,text:"text",parentId:-1}
        TreeNode node = TreeHelper.createBeanFromJson(data);
        long id = node.getId();
        T entity;

        if (id == -1L) {
            // create
            entity = getEntityClass().newInstance();
            getEntityDao().save(entity);
        } else {
            // update
            entity = getEntityDao().get(id);

            if (entity == null) {
                mv.setView(new StreamView());

                return;
            }
        }

        long parentId = node.getParentId();

        T parent = getEntityDao().get(parentId);

        if ((parent != null) && !entity.checkDeadLock(parent)) {
            // 老上级
            T oldParent = entity.getParent();

            if (oldParent == parent) {
                // 没改变分类级别。忽略更新
            } else if (oldParent != null) {
                // 更新上级分类
                oldParent.getChildren().remove(entity);
                parent.getChildren().add(entity);
                entity.setParent(parent);
                getEntityDao().save(parent);
                getEntityDao().save(oldParent);
                getEntityDao().save(entity);
            } else {
                // 添加上级分类
                parent.getChildren().add(entity);
                entity.setParent(parent);
                getEntityDao().save(parent);
                getEntityDao().save(entity);
            }
        } else {
            logger.info(parentId + "," + parent);
            logger.info(id + "," + entity);
        }

        entity.setName(node.getText());
        getEntityDao().save(entity);
        response.getWriter()
                .print("{success:true,id:" + entity.getId() + "}");
        mv.setView(new StreamView("application/json"));
    }

    /**
     * 根据id删除一个分类，由于级联关系，会同时删除所有子分类.
     *
     * @throws Exception 异常
     */
    public void removeTree() throws Exception {
        long id = getLongParam("id", -1L);

        if (id != -1L) {
            getEntityDao().removeById(id);
        }

        mv.setView(new StreamView("application/json"));
    }

    /**
     * 保存排序结果.
     * 参数是JSONArray，保存了id列表，排序时候根据id列表修改theSort字段
     *
     * @throws Exception 异常
     */
    public void sortTree() throws Exception {
        String data = getStrParam("data", "");

        if (data.equals("")) {
            mv.setView(new StreamView());

            return;
        }

        List<TreeNode> list = TreeHelper.createBeanListFromJson(data);

        for (int i = 0; i < list.size(); i++) {
            TreeNode node = list.get(i);
            long id = node.getId();
            long parentId = node.getParentId();
            T entity = getEntityDao().get(id);

            if (entity == null) {
                continue;
            }

            T parent = getEntityDao().get(parentId);

            if (parent == null) {
                T oldParent = entity.getParent();

                if (oldParent != null) {
                    oldParent.getChildren().remove(entity);
                }

                entity.setParent(null);
            } else if (!entity.checkDeadLock(parent)) {
                entity.setParent(parent);
                parent.getChildren().add(entity);

                getEntityDao().save(parent);
            } else {
                logger.info(entity);
                logger.info(parent);
                logger.info(entity.checkDeadLock(parent));
            }

            entity.setTheSort(i);
            getEntityDao().save(entity);
        }

        mv.setView(new StreamView("application/json"));
    }

    /** * onUpdate. */
    @Override
    public void onUpdate() throws Exception {
        response.getWriter().print("{success:true,info:\"success\"}");
        mv.setView(new StreamView("application/json"));
    }

    /** * getExcludes(). */
    public String[] getExcludes() {
        return new String[0];
    }

    /** * getDatePattern(). */
    public String getDatePattern() {
        return "yyyy-MM-dd";
    }
}
