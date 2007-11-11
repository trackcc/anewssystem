package anni.aerp2.web;

import anni.aerp2.domain.Erp2Bid;
import anni.aerp2.domain.Erp2BuyContract;
import anni.aerp2.domain.Erp2Product;
import anni.aerp2.domain.Erp2Supplier;

import anni.aerp2.manager.Erp2BidManager;
import anni.aerp2.manager.Erp2BuyContractManager;
import anni.aerp2.manager.Erp2ProductManager;
import anni.aerp2.manager.Erp2SupplierManager;

import anni.core.dao.support.Page;

import anni.core.grid.LongGridController;
import anni.core.grid.LongGridController;

import anni.core.json.JsonUtils;

import anni.core.utils.DateUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;

import org.springframework.validation.BindingResult;


/**
 * @author Lingo.
 * @since 2007年11月12日 上午 02时05分11秒750
 */
public class Erp2BuyContractController extends LongGridController<Erp2BuyContract, Erp2BuyContractManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(Erp2BuyContractController.class);

    /** * erp2SupplierManager. */
    private Erp2SupplierManager erp2SupplierManager = null;

    /** * constructor. */
    public Erp2BuyContractController() {
        //setEditView("/aerp2/erp2buycontract/editErp2BuyContract");
        //setListView("/aerp2/erp2buycontract/listErp2BuyContract");
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp2/erp2buycontract/index");
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
        // 合同编号
        String code = getStrParam("code", "").trim();

        // 合同状态
        String status = getStrParam("status", "").trim();

        // 合同名称
        String name = getStrParam("name", "").trim();

        // 签订时间时间
        String contractDate = getStrParam("contractDate", "").trim();

        Criteria criteria;

        if (sort != null) {
            boolean isAsc = dir.equalsIgnoreCase("asc");
            criteria = getEntityDao().createCriteria(sort, isAsc);
        } else {
            criteria = getEntityDao().createCriteria("id", false);
        }

        if (!code.equals("")) {
            criteria = criteria.add(Restrictions.like("code",
                        "%" + code + "%"));
        }

        if (!status.equals("")) {
            criteria = criteria.add(Restrictions.like("status",
                        "%" + status + "%"));
        }

        if (!name.equals("")) {
            criteria = criteria.add(Restrictions.like("name",
                        "%" + name + "%"));
        }

        if (!contractDate.equals("")) {
            criteria = criteria.add(Restrictions.eq("contractDate",
                        DateUtils.str2Date(contractDate)));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 添加.
     *
     * @throws Exception 异常
     */
    public void insert() throws Exception {
        logger.info(params());

        long id = getLongParam("id", -1L);

        Erp2BuyContract entity = getEntityDao().get(id);

        if (entity == null) {
            entity = new Erp2BuyContract();
        }

        BindingResult bindingResult = bindObject(request, entity);

        if (bindingResult.hasErrors()) {
            logger.info(bindingResult);
            response.getWriter().print("{success:false,info:'数据绑定错误'}");

            return;
        }

        long supplierId = getLongParam("supplier", -1L);
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
        Erp2BuyContract entity = getEntityDao().get(id);
        mv.addObject("entity", entity);
        mv.setViewName("aerp2/erp2buycontract/view");
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {
            "hibernateLazyInitializer", "erp2Bids", "erp2Supplier"
        };
    }

    /** * @return datePattern. */
    @Override
    public String getDatePattern() {
        return "yyyy-MM-dd";
    }

    /** * @param erp2SupplierManager Erp2SupplierManager. */
    public void setErp2SupplierManager(
        Erp2SupplierManager erp2SupplierManager) {
        this.erp2SupplierManager = erp2SupplierManager;
    }
}
