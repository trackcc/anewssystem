package anni.aerp2.web;

import anni.aerp2.domain.Erp2Bid;
import anni.aerp2.domain.Erp2InviteBid;
import anni.aerp2.domain.Erp2Supplier;

import anni.aerp2.manager.Erp2BidManager;
import anni.aerp2.manager.Erp2InviteBidManager;
import anni.aerp2.manager.Erp2SupplierManager;

import anni.core.dao.support.Page;

import anni.core.grid.LongGridController;

import anni.core.json.JsonUtils;

import anni.core.utils.DateUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;

import org.springframework.validation.BindingResult;


/**
 * @author Lingo.
 * @since 2007年11月07日 下午 15时09分53秒984
 */
public class Erp2InviteBidController extends LongGridController<Erp2InviteBid, Erp2InviteBidManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(Erp2InviteBidController.class);

    /** * erp2BidManager. */
    private Erp2BidManager erp2BidManager = null;

    /** * erp2SupplierManager. */
    private Erp2SupplierManager erp2SupplierManager = null;

    /** * constructor. */
    public Erp2InviteBidController() {
        //setEditView("/aerp2/erp2invitebid/editErp2InviteBid");
        //setListView("/aerp2/erp2invitebid/listErp2InviteBid");
    }

    /** * @param erp2BidManager Erp2BidManager. */
    public void setErp2BidManager(Erp2BidManager erp2BidManager) {
        this.erp2BidManager = erp2BidManager;
    }

    /** * @param erp2SupplierManager Erp2SupplierManager. */
    public void setErp2SupplierManager(
        Erp2SupplierManager erp2SupplierManager) {
        this.erp2SupplierManager = erp2SupplierManager;
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp2/erp2invitebid/index");
    }

    /** * stat. */
    public void stat() {
        mv.setViewName("aerp2/erp2invitebid/stat");
    }

    /**
     * 为投标汇总，分页浏览记录.
     *
     * @throws Exception 异常
     */
    public void pagedQueryForStat() throws Exception {
        logger.info(params());

        // 分页
        int pageSize = getIntParam("limit", 1);
        int start = getIntParam("start", 0);
        int pageNo = (start / pageSize) + 1;

        // 排序
        String sort = getStrParam("sort", null);
        String dir = getStrParam("dir", "asc");

        // 查询
        // 标书标号
        String bidCode = getStrParam("bidCode", "").trim();

        // 供应商名称
        String supplierName = getStrParam("supplierName", "").trim();

        // 投标日期
        String bidDate = getStrParam("bidDate", "").trim();

        Criteria criteria;

        if (sort != null) {
            boolean isAsc = dir.equalsIgnoreCase("asc");
            criteria = getEntityDao().createCriteria(sort, isAsc);
        } else {
            criteria = getEntityDao().createCriteria("id", false);
        }

        if (!bidCode.equals("")) {
            criteria = criteria.createAlias("erp2Bid", "bid")
                               .add(Restrictions.like("bid.code",
                        "%" + bidCode + "%"));
        }

        if (!supplierName.equals("")) {
            criteria = criteria.createAlias("erp2Supplier", "supplier")
                               .add(Restrictions.like("supplier.name",
                        "%" + supplierName + "%"));
        }

        if (!bidDate.equals("")) {
            criteria = criteria.add(Restrictions.eq("bidDate",
                        DateUtils.str2Date(bidDate)));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(), getExcludes(),
            getDatePattern());
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
        // 标书标号
        String bidCode = getStrParam("bidCode", "").trim();

        // 投标日期
        String bidDate = getStrParam("bidDate", "").trim();

        Criteria criteria;

        if (sort != null) {
            boolean isAsc = dir.equalsIgnoreCase("asc");
            criteria = getEntityDao().createCriteria(sort, isAsc);
        } else {
            criteria = getEntityDao().createCriteria("id", false);
        }

        if (!bidCode.equals("")) {
            criteria = criteria.createAlias("erp2Bid", "bid")
                               .add(Restrictions.like("bid.code",
                        "%" + bidCode + "%"));
        }

        if (!bidDate.equals("")) {
            criteria = criteria.add(Restrictions.eq("bidDate",
                        DateUtils.str2Date(bidDate)));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 分页返回指定标书标号的供应商的名字，和标书id.
     *
     * @throws Exception 异常
     */
    public void pagedQueryByBidId() throws Exception {
        logger.info(params());

        // 分页
        int pageSize = getIntParam("limit", 1);
        int start = getIntParam("start", 0);
        int pageNo = (start / pageSize) + 1;

        // 排序
        String sort = getStrParam("sort", null);
        String dir = getStrParam("dir", "asc");

        // 查询
        // 标书id
        long bidId = getLongParam("bidCode", -1L);

        // 对供应商的名称，进行模糊查询
        String query = getStrParam("query", "").trim();

        Criteria criteria = getEntityDao().createCriteria("id", false);

        if (bidId != -1L) {
            criteria = criteria.createAlias("erp2Bid", "bid")
                               .add(Restrictions.eq("bid.id", bidId));
        }

        if (!query.equals("")) {
            criteria = criteria.createAlias("erp2Supplier", "supplier")
                               .add(Restrictions.like("supplier.name",
                        "%" + query + "%"));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(),
            new String[] {
                "hibernateLazyInitializer", "erp2InviteBids", "region",
                "bidDate", "price", "address", "tel", "area", "zip",
                "email", "erp2Product", "descn", "startDate", "erp2Bid",
                "erp2SuccessBids"
            }, getDatePattern());
    }

    /**
     * 添加投标单.
     *
     * @throws Exception 异常
     */
    public void insert() throws Exception {
        logger.info(params());

        long id = getLongParam("id", -1L);

        Erp2InviteBid entity = getEntityDao().get(id);

        if (entity == null) {
            entity = new Erp2InviteBid();
        }

        BindingResult bindingResult = bindObject(request, entity);

        if (bindingResult.hasErrors()) {
            logger.info(bindingResult);
            response.getWriter().print("{success:false,info:'数据绑定错误'}");

            return;
        }

        long bidId = getLongParam("bidCode", -1L);
        Erp2Bid bid = erp2BidManager.get(bidId);
        entity.setErp2Bid(bid);

        long supplierId = getLongParam("supplierName", -1L);
        Erp2Supplier supplier = erp2SupplierManager.get(supplierId);
        entity.setErp2Supplier(supplier);

        //
        entityDao.save(entity);
        logger.info("success");
        response.getWriter().print("{success:true}");
    }

    /** * view. */
    public void view() {
        long id = getLongParam("id", -1L);
        Erp2InviteBid entity = getEntityDao().get(id);
        mv.addObject("entity", entity);
        mv.setViewName("aerp2/erp2invitebid/view");
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {"hibernateLazyInitializer", "erp2InviteBids"};
    }

    /** * @return datePattern. */
    @Override
    public String getDatePattern() {
        return "yyyy-MM-dd";
    }
}
