package anni.anews.web;

import java.util.Collection;
import java.util.List;

import anni.anews.domain.NewsCategory;

import anni.anews.manager.NewsCategoryManager;

import anni.anews.web.support.ExtTreeNode;

import anni.asecurity.web.support.extjs.TreeHelper;

import anni.core.web.prototype.StreamView;
import anni.core.web.prototype.TreeLongController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsCategoryController extends TreeLongController<NewsCategory, NewsCategoryManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsCategoryController.class);

    /** * constructor. */
    public NewsCategoryController() {
        setEditView("/anews/newscategory/editNewsCategory");
        setListView("/anews/newscategory/listNewsCategory");
    }

    /**
     * 一次性获得整棵树的数据.
     *
     * @throws Exception 异常
     */
    public void getAllTree() throws Exception {
        String hql = "from NewsCategory where parent is null order by theSort asc, id desc";
        List<NewsCategory> list = getEntityDao().find(hql);

        StringBuffer buff = appendCategory(list);

        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(buff.toString());

        StreamView view = new StreamView("application/json");
        mv.setView(view);
    }

    /** * appendCategory. */
    private StringBuffer appendCategory(Collection<NewsCategory> list) {
        StringBuffer buff = new StringBuffer();
        buff.append("[");

        for (NewsCategory category : list) {
            buff.append("{\"text\":\"").append(category.getName())
                .append("\",\"id\":\"").append(category.getId())
                .append("\",\"depth\":\"").append(category.getLevel() - 1)
                .append("\",\"leaf\":").append(category.isLeaf())
                .append(",\"allowEdit\":true,\"draggable\":true,\"allowDelete\":true,\"allowChildren\":true")
                .append(",\"checked\":\"false\"");

            if (!category.isLeaf()) {
                buff.append(",\"children\":")
                    .append(appendCategory(category.getChildren()));
            }

            buff.append("},");
        }

        if (buff.length() > 1) {
            buff.deleteCharAt(buff.length() - 1);
        }

        buff.append("]");

        return buff;
    }

    /**
     * 根据传入参数，返回对应id分类的子分类，用于显示分类的树形结构.
     * 如果没有指定id的值，则返回所有根节点
     *
     * @throws Exception 异常
     */
    public void getChildren() throws Exception {
        logger.info(params());

        long parentId = getLongParam("node", -1L);
        List<NewsCategory> categoryList = null;

        if (parentId == -1L) {
            // 根节点
            categoryList = getEntityDao()
                               .find("from NewsCategory where parent is null order by theSort asc, id desc");
        } else {
            categoryList = getEntityDao()
                               .find("from NewsCategory where parent.id=? order by theSort asc, id desc",
                    parentId);
        }

        response.setCharacterEncoding("UTF-8");
        //ExtTreeNode.write(response.getWriter(), categoryList);
        response.getWriter().print(TreeHelper.createTreeJson(categoryList));

        StreamView view = new StreamView("application/json");
        mv.setView(view);
    }

    /**
     * 添加一个分类.
     * 目前还不没有返回成功或者错误信息
     * 校验方式，当输入的data参数不存在的时候，直接返回
     *
     * @throws Exception 异常
     */
    public void insertTree() throws Exception {
        logger.info(params());

        String data = getStrParam("data", "");

        if (data.equals("")) {
            mv.setView(new StreamView());

            return;
        }

        JSONObject jsonObject = JSONObject.fromString(data);
        long id = jsonObject.getLong("id");
        NewsCategory category;

        if (id == -1L) {
            // create
            category = new NewsCategory();
            getEntityDao().save(category);
        } else {
            // update
            category = getEntityDao().get(id);

            if (category == null) {
                mv.setView(new StreamView());

                return;
            }
        }

        long parentId = jsonObject.getLong("parentId");

        if (parentId != -1L) {
            NewsCategory parent = getEntityDao().get(parentId);

            if ((parent != null) && !category.checkDeadLock(parent)) {
                // 老上级
                NewsCategory oldParent = category.getParent();

                if ((oldParent != null) && (oldParent != parent)) {
                    // 更新上级分类
                    oldParent.getChildren().remove(category);
                    parent.getChildren().add(category);
                    category.setParent(parent);
                    getEntityDao().save(parent);
                    getEntityDao().save(oldParent);
                    getEntityDao().save(category);
                } else {
                    // 添加上级分类
                    parent.getChildren().add(category);
                    category.setParent(parent);
                    getEntityDao().save(parent);
                    getEntityDao().save(category);
                }
            }
        }

        String name = jsonObject.getString("text");
        // 数据校验，name长度不能超过50，不能包含html标签
        name = name.replaceAll("<", "&lt;");
        name = name.replaceAll(">", "&gt;");

        if (name.length() > 50) {
            mv.setView(new StreamView());

            return;
        }

        category.setName(name);
        getEntityDao().save(category);
        response.getWriter()
                .print("{success:true,id:" + category.getId() + "}");
        mv.setView(new StreamView());
    }

    /**
     * 根据id删除一个分类，由于级联关系，会同时删除所有子分类.
     *
     * @throws Exception 异常
     */
    public void removeTree() throws Exception {
        logger.info(params());

        long id = getLongParam("id", -1L);

        if (id != -1L) {
            getEntityDao().removeById(id);
        }

        StreamView view = new StreamView("application/json");
        mv.setView(view);
    }

    /**
     * 保存排序结果.
     * 参数是JSONArray，保存了id列表，排序时候根据id列表修改theSort字段
     *
     * @throws Exception 异常
     */
    public void sortTree() throws Exception {
        logger.info(params());

        String data = getStrParam("data", "");

        if (data.equals("")) {
            mv.setView(new StreamView());

            return;
        }

        JSONArray jsonArray = JSONArray.fromString(data);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong("id");
            long parentId = jsonObject.getLong("parentId");
            NewsCategory category = getEntityDao().get(id);

            if (category == null) {
                continue;
            }

            // 因为脑子乱了，用最简单的方式前进，先清空外键关系
            //Category oldParent = category.getParent();
            //Category oldTop = category.getTop();

            //if (oldParent != null) {
            //    oldParent.getChildren().remove(category);
            //    category.setParent(null);
            //    oldTop.getAllChildren().remove(category);
            //    category.setTop(null);
            //    getEntityDao().save(oldParent);
            //    getEntityDao().save(oldTop);
            //    getEntityDao().save(category);
            //}
            NewsCategory parent = getEntityDao().get(parentId);

            if (parent == null) {
                NewsCategory oldParent = category.getParent();

                if (oldParent != null) {
                    oldParent.getChildren().remove(category);
                }

                category.setParent(null);
            } else if (!category.checkDeadLock(parent)) {
                category.setParent(parent);
                parent.getChildren().add(category);

                getEntityDao().save(parent);
            } else {
                logger.info(category);
                logger.info(parent);
                logger.info(category.checkDeadLock(parent));
            }

            category.setTheSort(i);
            getEntityDao().save(category);
        }

        StreamView view = new StreamView("application/json");
        mv.setView(view);
    }

    /** * tree. */
    public void tree() {
        logger.info("start");
        mv.setViewName("/anews/newscategory/tree");
    }
}
