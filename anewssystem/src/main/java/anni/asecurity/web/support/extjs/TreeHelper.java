package anni.asecurity.web.support.extjs;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import anni.core.web.json.DateJsonValueProcessor;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 树的帮助类.
 */
public class TreeHelper {
    /** * logger. */
    private static Log logger = LogFactory.getLog(TreeHelper.class);

    /**
     * 为异步树，一次加载所有的节点，生成json.
     */
    public static String createAllTreeJson(
        Collection<?extends LongSortedTreeEntityBean> list) {
        return createJSONArray(list, true).toString();
    }

    /**
     *
     */
    public static JSONArray createJSONArray(
        Collection<?extends LongSortedTreeEntityBean> list,
        boolean wantToAll) {
        JSONArray jsonArray = new JSONArray();

        for (LongSortedTreeEntityBean bean : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", bean.getId());
            jsonObject.put("text", bean.getName());
            jsonObject.put("leaf", bean.isLeaf());
            jsonObject.put("allowEdit", true);
            jsonObject.put("draggable", true);
            jsonObject.put("allowDelete", true);
            jsonObject.put("allowChildren", true);

            // 加上这个属性以后，直接跑出checkbox来了，奇怪
            //jsonObject.put("checked", false);
            if (wantToAll && !bean.isLeaf()) {
                jsonObject.put("children",
                    createJSONArray(bean.getChildren(), true));
            }

            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }

    /**
     * 把tree列表转化成json数组.
     */
    public static String createTreeJson(
        Collection<?extends LongSortedTreeEntityBean> list) {
        return createJSONArray(list, false).toString();
    }

    /**
     * data={"id":"1","text":"1","parentId":-1}.
     */
    public static TreeNode createBeanFromJson(String data) {
        logger.info(data);

        JSONObject jsonObject = JSONObject.fromString(data);
        TreeNode node = new TreeNode();
        node.setId(jsonObject.getLong("id"));
        node.setText(jsonObject.getString("text"));
        node.setParentId(jsonObject.getLong("parentId"));

        return node;
    }

    /**
     * data=[{"id":"1","parentId":-1},{"id":"2","parentId":-1}].
     */
    public static List<TreeNode> createBeanListFromJson(String data) {
        logger.info(data);

        List<TreeNode> list = new ArrayList<TreeNode>();

        JSONArray jsonArray = JSONArray.fromString(data);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            TreeNode node = new TreeNode();
            node.setId(jsonObject.getLong("id"));
            node.setParentId(jsonObject.getLong("parentId"));
            list.add(node);
        }

        return list;
    }

    /**
     * 不允许将自表外键关系设置成环状.
     * 就是说，当前的current的子节点中，如果包含parent，就不能把parent设置为current的上级节点
     *
     * @param current TreeEntityBean
     * @param parent TreeEntityBean
     * @param <T> TreeEntityBean
     * @return boolean 是否形成环状
     */
    public static <T extends LongSortedTreeEntityBean> boolean checkDeadLock(
        LongSortedTreeEntityBean<T> current,
        LongSortedTreeEntityBean<T> parent) {
        if ((current == null) || (parent == null)
                || current.equals(parent)) {
            return true;
        } else {
            for (LongSortedTreeEntityBean child : current.getChildren()) {
                boolean isDeadLock = checkDeadLock(child, parent);

                if (isDeadLock) {
                    return true;
                }
            }
        }

        return false;
    }

    /** * write. */
    public static void write(Object bean, Writer writer,
        String[] excludes, String datePattern) throws Exception {
        JsonConfig jsonConfig = JsonConfig.getInstance();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(true);
        jsonConfig.registerJsonValueProcessor(Date.class,
            new DateJsonValueProcessor(datePattern));

        JSON json = JSONSerializer.toJSON(bean);

        json.write(writer);
    }
}
