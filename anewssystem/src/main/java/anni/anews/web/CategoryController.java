package anni.anews.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import anni.anews.domain.Category;

import anni.anews.manager.CategoryManager;

import anni.anews.web.support.ExtTreeNode;

import anni.core.web.prototype.StreamView;
import anni.core.web.prototype.TreeLongController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月08日 下午 18时10分12秒781
 */
public class CategoryController extends TreeLongController<Category, CategoryManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(CategoryController.class);

    /**
     * 根据传入参数，返回对应id分类的子分类，用于显示分类的树形结构.
     * 如果没有指定id的值，则返回所有根节点
     */
    public void getChildren() throws Exception {
        logger.info(params());

        long parentId = getLongParam("node", -1L);
        List<Category> categoryList = null;

        if (parentId == -1L) {
            // 根节点
            categoryList = getEntityDao()
                               .find("from Category where parent is null order by theSort asc, id desc");
        } else {
            categoryList = getEntityDao()
                               .find("from Category where parent.id=? order by theSort asc, id desc",
                    parentId);
        }

        response.setCharacterEncoding("UTF-8");
        ExtTreeNode.write(response.getWriter(), categoryList);

        StreamView view = new StreamView("application/json");
        mv.setView(view);
    }

    /**
     * 添加一个分类.
     * 目前还不没有返回成功或者错误信息
     * 校验方式，当输入的data参数不存在的时候，直接返回
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
        Category category;

        if (id == -1L) {
            // create
            category = new Category();
            getEntityDao().save(category);
        } else {
            // update
            category = getEntityDao().get(id);

            if (category == null) {
                mv.setView(new StreamView());

                return;
            }

            // 因为脑子乱了，用最简单的方式前进，先清空外键关系
            Category oldParent = category.getParent();
            Category oldTop = category.getTop();

            if (oldParent != null) {
                oldParent.getChildren().remove(category);
                category.setParent(null);
                oldTop.getAllChildren().remove(category);
                category.setTop(null);
                getEntityDao().save(oldParent);
                getEntityDao().save(oldTop);
                getEntityDao().save(category);
            }
        }

        long parentId = jsonObject.getLong("parentId");

        if (parentId != -1L) {
            Category parent = getEntityDao().get(parentId);

            if ((parent != null) && !category.checkDeadLock(parent)) {
                category.setParent(parent);
                parent.getChildren().add(category);

                Category top;

                if (parent.getTop() != null) {
                    // 上级分类是不是顶级分类
                    top = parent.getTop();
                } else {
                    // 上级分类是顶级分类
                    top = parent;
                }

                category.setTop(top);
                top.getAllChildren().add(category);

                getEntityDao().save(parent);
                getEntityDao().save(top);
                getEntityDao().save(category);
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
     * 参数是JSONArray，保存了id列表，排序时候根据id列表修改theSort字段.
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
            Category category = getEntityDao().get(id);

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
            Category parent = getEntityDao().get(parentId);

            if ((parent != null) && !category.checkDeadLock(parent)) {
                category.setParent(parent);
                parent.getChildren().add(category);

                Category top;

                if (parent.getParent() != null) {
                    // 非顶级分类
                    top = parent.getParent();
                } else {
                    top = parent;
                }

                category.setTop(top);
                top.getAllChildren().add(category);
                getEntityDao().save(parent);
                getEntityDao().save(top);
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
    }
}
