package anni.core.tree;

import java.util.ArrayList;
import java.util.List;

import anni.core.json.JsonController;
import anni.core.json.JsonUtils;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 操作树型的基类.
 *
 * @author Lingo
 * @since 2007-09-15
 * @param <T> LongTreeNode的子类
 * @param <D> LongTreeHibernateDao的子类
 */
public class LongTreeController<T extends LongTreeNode<T>, D extends LongTreeHibernateDao<T>>
    extends JsonController<T, D> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(LongTreeController.class);

    /**
     * 一次性获得整棵树的数据.
     *
     * @throws Exception 异常
     */
    public void getAllTree() throws Exception {
        logger.info(params());

        String hql = "from " + getEntityClass().getSimpleName()
            + " where parent is null order by theSort asc, id desc";

        List<T> list = getEntityDao().find(hql);

        JsonUtils.write(list, response.getWriter(), getExcludesForAll(),
            getDatePattern());
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
        String hql = "from " + getEntityClass().getSimpleName();
        String orderBy = " order by theSort asc, id desc";

        if (parentId == -1L) {
            // 根节点
            list = getEntityDao()
                       .find(hql + " where parent is null" + orderBy);
        } else {
            list = getEntityDao()
                       .find(hql + " where parent.id=?" + orderBy, parentId);
        }

        JsonUtils.write(list, response.getWriter(),
            getExcludesForChildren(), getDatePattern());
    }

    /**
     * 根据id获取一条记录.
     *
     * @throws Exception 写入json可能出现异常
     */
    public void loadData() throws Exception {
        long id = getLongParam("id", -1L);
        T entity = getEntityDao().get(id);

        if (entity != null) {
            List<T> list = new ArrayList<T>();
            list.add(entity);
            JsonUtils.write(list, response.getWriter(),
                getExcludesForChildren(), getDatePattern());
        }
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
        T node = JsonUtils.json2Bean(data, getEntityClass(),
                getExcludesForChildren(), getDatePattern());

        T entity = getEntityDao().get(node.getId());

        // 更新上级分类
        Long parentId = node.getParentId();

        if (parentId != null) {
            T parent = getEntityDao().get(parentId);
            node.setParent(parent);
        }

        if (entity == null) {
            // 添加
            // 只有在id = null的情况下，才执行insert，否则执行update
            node.setId(null);
            getEntityDao().save(node);
            entity = node;
        } else {
            // 修改
            entity.setName(node.getName());
            getEntityDao().save(entity);
        }

        response.getWriter()
                .print("{success:true,id:" + entity.getId() + "}");
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
    }

    /**
     * 保存排序结果.
     * 参数是JSONArray，保存了id列表，排序时候根据id列表修改theSort字段
     *
     * @throws Exception 异常
     */
    public void sortTree() throws Exception {
        String data = getStrParam("data", "");
        List<T> list = JsonUtils.json2List(data, getEntityClass(),
                getExcludesForChildren(), getDatePattern());

        for (int i = 0; i < list.size(); i++) {
            T node = list.get(i);
            Long id = node.getId();
            Long parentId = node.getParentId();

            T entity = getEntityDao().get(id);

            if (entity != null) {
                T parent = getEntityDao().get(parentId);
                entity.setParent(parent);
                entity.setTheSort(i);
                getEntityDao().save(entity);
            }
        }
    }

    /**
     * updateTree.
     * 修改信息信息
     *
     * @throws Exception 异常
     */
    public void updateTree() throws Exception {
        String data = getStrParam("data", "");
        JSONObject jsonObject = JSONObject.fromObject(data);

        T entity = getEntityDao().get(jsonObject.getLong("id"));
        JsonUtils.json2Bean(jsonObject, entity, getExcludesForChildren(),
            getDatePattern());

        getEntityDao().save(entity);

        response.getWriter().print("{success:true,info:\"success\"}");
    }

    /**
     * 迭代取得所有节点时候，使用的exclude设置.
     *
     * @return 不需要转化成json的属性列表，默认是空的
     */
    public String[] getExcludesForAll() {
        return new String[] {"class", "root", "parent"};
    }

    /**
     * 只取得直接子节点时，使用的exclude设置.
     *
     * @return 不需要转换json的属性数组
     */
    public String[] getExcludesForChildren() {
        return new String[] {"class", "root", "parent", "children"};
    }
}
