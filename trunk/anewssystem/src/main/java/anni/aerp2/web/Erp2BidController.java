package anni.aerp2.web;

import anni.aerp2.domain.Erp2Bid;
import anni.aerp2.domain.Erp2Product;

import anni.aerp2.manager.Erp2BidManager;
import anni.aerp2.manager.Erp2ProductManager;

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
 * @since 2007年11月07日 上午 11时12分24秒515
 */
public class Erp2BidController extends LongGridController<Erp2Bid, Erp2BidManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(Erp2BidController.class);

    /** * erp2ProductManager. */
    private Erp2ProductManager erp2ProductManager = null;

    /** * constructor. */
    public Erp2BidController() {
        //setEditView("/aerp2/erp2bid/editErp2Bid");
        //setListView("/aerp2/erp2bid/listErp2Bid");
    }

    /** * @param erp2ProductManager Erp2ProductManager. */
    public void setErp2ProductManager(
        Erp2ProductManager erp2ProductManager) {
        this.erp2ProductManager = erp2ProductManager;
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp2/erp2bid/index");
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
        // 标书编号
        String code = getStrParam("code", "").trim();

        // 产品名称
        String productName = getStrParam("productName", "").trim();

        // 标书开始时间
        String startDate = getStrParam("startDate", "").trim();

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

        if (!productName.equals("")) {
            criteria = criteria.createAlias("erp2Product", "product")
                               .add(Restrictions.like("product.name",
                        "%" + productName + "%"));
        }

        if (!startDate.equals("")) {
            criteria = criteria.add(Restrictions.eq("startDate",
                        DateUtils.str2Date(startDate)));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 分页查询标书名称，autocomplete的参数是query.
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
            criteria = criteria.add(Restrictions.like("code",
                        "%" + query + "%"));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(),
            new String[] {
                "erp2Product", "startDate", "openDate", "endDate", "descn",
                "parameter", "productNum", "hibernateLazyInitializer"
            }, getDatePattern());
    }

    /**
     * 添加标书.
     *
     * @throws Exception 异常
     */
    public void insert() throws Exception {
        logger.info(params());

        long id = getLongParam("id", -1L);

        Erp2Bid entity = getEntityDao().get(id);

        if (entity == null) {
            entity = new Erp2Bid();
        }

        BindingResult bindingResult = bindObject(request, entity);

        if (bindingResult.hasErrors()) {
            logger.info(bindingResult);
            response.getWriter().print("{success:false,info:'数据绑定错误'}");

            return;
        }

        long productId = getLongParam("product", -1L);
        Erp2Product product = erp2ProductManager.get(productId);
        entity.setErp2Product(product);

        //
        entityDao.save(entity);
        logger.info("success");
        response.getWriter().print("{success:true}");
    }

    /** * view. */
    public void view() {
        long id = getLongParam("id", -1L);
        Erp2Bid entity = getEntityDao().get(id);
        mv.addObject("entity", entity);
        mv.setViewName("aerp2/erp2bid/view");
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
}
