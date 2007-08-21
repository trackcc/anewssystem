package anni.anews.manager;

import anni.anews.domain.NewsCategory;

import anni.core.dao.HibernateTreeEntityDao;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsCategoryManager extends HibernateTreeEntityDao<NewsCategory> {
    /**
     * 保存.
     *
     * @param o 实体类
     */
    @Override
    public void save(Object o) {
        NewsCategory entity = (NewsCategory) o;
        NewsCategory parent = entity.getParent();
        String hql = null;

        // 每次都取上次最大的code值，如果删除了几个中间值，肯定会出现还有空位，但是最大数据溢出的问题
        if (parent == null) {
            hql = "select max(code) from NewsCategory where parent is null";
        } else {
            hql = "select max(code) from NewsCategory where parent.id="
                + parent.getId();
        }

        Long lastCode = (Long) createQuery(hql).uniqueResult();

        // 只在新增的时候计算编码值
        if (entity.getId() == null) {
            // 如果是这个层次的第一个
            long code = calculateCode(lastCode, entity);
            entity.setCode(code);
        }

        super.save(entity);
    }

    /**
     * 计算节点的编码.
     *
     * @param lastCode 同级最大编码，可能为null
     * @param entity NewsCategory节点
     * @return code
     */
    private long calculateCode(Long lastCode, NewsCategory entity) {
        long base = 1L << (8 * (8 - entity.getLevel()));

        if (lastCode == null) {
            NewsCategory parent = entity.getParent();

            if (parent == null) {
                return base;
            } else {
                return parent.getCode() + base;
            }
        } else {
            return ((lastCode / base) + 1) * base;
        }
    }
}
