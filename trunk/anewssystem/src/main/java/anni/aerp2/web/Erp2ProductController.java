package anni.aerp2.web;

import anni.aerp2.domain.Erp2Product;

import anni.aerp2.manager.Erp2ProductManager;

import anni.core.dao.support.Page;

import anni.core.grid.LongGridController;

import anni.core.json.JsonUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


/**
 * @author Lingo.
 * @since 2007年11月03日 下午 12时54分44秒937
 */
public class Erp2ProductController extends LongGridController<Erp2Product, Erp2ProductManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(Erp2ProductController.class);

    /** * constructor. */
    public Erp2ProductController() {
        //setEditView("/aerp2/erp2product/editErp2Product");
        //setListView("/aerp2/erp2product/listErp2Product");
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp2/erp2product/index");
    }

    /**
     * 分页浏览记录.
     *
     * @throws Exception 异常
     */
    @Override
    public void pagedQuery() throws Exception {
        logger.info(params());

        // 分页
        int pageSize = getIntParam("limit", 1);
        int start = getIntParam("start", 0);
        int pageNo = (start / pageSize) + 1;

        // 排序
        String sort = getStrParam("sort", null);
        String dir = getStrParam("dir", "asc");

        // 查询
        // 品名
        String name = getStrParam("name", "").trim();

        // 类别
        int type = getIntParam("type", -1);

        // 供应商
        String supplier = getStrParam("supplier", "").trim();

        // 序号
        long no = getLongParam("no", -1L);

        Criteria criteria;

        if (sort != null) {
            boolean isAsc = dir.equalsIgnoreCase("asc");
            criteria = getEntityDao().createCriteria(sort, isAsc);
        } else {
            criteria = getEntityDao().createCriteria("id", false);
        }

        if (!name.equals("")) {
            criteria = criteria.add(Restrictions.like("name",
                        "%" + name + "%"));
        }

        if (type != -1) {
            criteria = criteria.add(Restrictions.eq("type", type));
        }

        if (!supplier.equals("")) {
            criteria = criteria.createAlias("erp2Supplier", "supplier")
                               .add(Restrictions.like("supplier.name",
                        "%" + supplier + "%"));
        }

        if (no != -1L) {
            criteria = criteria.add(Restrictions.eq("id", no));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 分页查询供应商名称，autocomplete的参数是query.
     *
     * @throws Exception 异常
     */
    public void pagedQueryForCombo() throws Exception {
        // 分页
        int pageSize = getIntParam("limit", 1);
        int start = getIntParam("start", 0);
        int pageNo = (start / pageSize) + 1;

        // 查询
        String query = getStrParam("query", "");

        Criteria criteria = getEntityDao().createCriteria("id", false);

        if (!query.equals("")) {
            criteria = criteria.add(Restrictions.like("name",
                        "%" + query + "%"));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(),
            new String[] {
                "erp2Supplier", "num", "total", "material", "price",
                "descn", "factory", "hibernateLazyInitializer"
            }, getDatePattern());
    }

    /** * @return excludes. */
    public String[] getExcludes() {
        return new String[] {
            "hibernateLazyInitializer", "erp2Products", "region",
            "erp2InviteBids", "erp2Bid", "erp2Bids", "erp2BuyOrderInfos",
            "erp2BuyOrders"
        };
    }
}
