package anni.core.domain.tree;


/**
 * tree实体的帮助类.
 *
 * @author Lingo
 * @since 2007-06-17
 */
public final class TreeHelper {
    /** * protected constructor. */
    protected TreeHelper() {
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
    public static <T extends TreeEntityBean> boolean checkDeadLock(
        TreeEntityBean<T> current, TreeEntityBean<T> parent) {
        if ((current == null) || (parent == null)
                || current.equals(parent)) {
            return true;
        } else {
            for (TreeEntityBean child : current.getChildren()) {
                boolean isDeadLock = checkDeadLock(child, parent);

                if (isDeadLock) {
                    return true;
                }
            }
        }

        return false;
    }
}
