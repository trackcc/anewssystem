package anni.aerp2.web;

import java.util.List;

import anni.aerp2.domain.Erp2BuyOrder;
import anni.aerp2.domain.Erp2BuyOrderInfo;
import anni.aerp2.domain.Erp2Product;
import anni.aerp2.domain.Erp2Supplier;

import anni.aerp2.manager.Erp2BuyOrderInfoManager;
import anni.aerp2.manager.Erp2BuyOrderManager;
import anni.aerp2.manager.Erp2ProductManager;
import anni.aerp2.manager.Erp2SupplierManager;

import anni.core.dao.support.Page;

import anni.core.grid.LongGridController;

import anni.core.json.JsonUtils;

import anni.core.utils.DateUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


/**
 * @author Lingo.
 * @since 2007年11月10日 下午 22时07分07秒984
 */
public class Erp2BuyOrderController extends LongGridController<Erp2BuyOrder, Erp2BuyOrderManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(Erp2BuyOrderController.class);

    /** * erp2ProductManager. */
    private Erp2ProductManager erp2ProductManager = null;

    /** * erp2BuyOrderInfoManager. */
    private Erp2BuyOrderInfoManager erp2BuyOrderInfoManager = null;

    /** * erp2SupplierManager. */
    private Erp2SupplierManager erp2SupplierManager = null;

    /** * constructor. */
    public Erp2BuyOrderController() {
        //setEditView("/aerp2/erp2buyorder/editErp2BuyOrder");
        //setListView("/aerp2/erp2buyorder/listErp2BuyOrder");
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp2/erp2buyorder/index");
    }

    /**
     * update修改一条采购订单.
     *
     * @throws Exception json操作出现的异常
     */
    public void update() throws Exception {
        String data = getStrParam("data", null);
        logger.info(data);

        JSONObject jsonObject = JSONObject.fromObject(data);
        JSONObject buyOrderValue = jsonObject.getJSONObject(
                "buyOrderValue");
        JSONArray infos = jsonObject.getJSONArray("infos");

        long id = buyOrderValue.getLong("id");
        Erp2BuyOrder entity = getEntityDao().get(id);

        //
        entity = JsonUtils.json2Bean(buyOrderValue, entity,
                new String[] {
                    "hibernateLazyInitializer", "id", "erp2Supplier"
                }, "yyyy-MM-dd");
        logger.info(entity.getId());

        try {
            long supplierId = buyOrderValue.getLong("supplierName");
            Erp2Supplier supplier = erp2SupplierManager.get(supplierId);
            entity.setErp2Supplier(supplier);
        } catch (Exception ex) {
            // 如果没有修改供应商的信息，这个字段就有会有问题，因为不会是id，而是直接的name
            // 所以不是整数，而是字符串，所以会出问题。
        }

        getEntityDao().save(entity);

        //
        List<Erp2BuyOrderInfo> infoList = JsonUtils.json2List(infos,
                Erp2BuyOrderInfo.class,
                new String[] {
                    "hibernateLazyInitializer", "id", "erp2BuyOrder",
                    "erp2Product"
                }, "yyyy-MM-dd");

        for (int i = 0; i < infos.size(); i++) {
            JSONObject jsonObj = infos.getJSONObject(i);
            long infoId = jsonObj.getLong("id");
            Erp2BuyOrderInfo info = null;
            info.setErp2BuyOrder(entity);

            if (infoId == -1L) {
                info = JsonUtils.json2Bean(jsonObj,
                        Erp2BuyOrderInfo.class, new String[] {"id"},
                        "yyyy-MM-dd");
            } else {
                info = erp2BuyOrderInfoManager.get(infoId);
                info = JsonUtils.json2Bean(jsonObj, info,
                        new String[] {"id"}, "yyyy-MM-dd");
            }

            long productId = jsonObj.getLong("productId");
            Erp2Product product = erp2ProductManager.get(productId);
            info.setErp2Product(product);

            erp2BuyOrderInfoManager.save(info);
        }

        response.getWriter().print("{success:true}");
    }

    /**
     * insert添加一条采购订单.
     *
     * @throws Exception json操作出现的异常
     */
    public void insert() throws Exception {
        String data = getStrParam("data", null);
        logger.info(data);

        JSONObject jsonObject = JSONObject.fromObject(data);
        JSONObject buyOrderValue = jsonObject.getJSONObject(
                "buyOrderValue");
        JSONArray infos = jsonObject.getJSONArray("infos");

        //
        Erp2BuyOrder entity = JsonUtils.json2Bean(buyOrderValue,
                Erp2BuyOrder.class,
                new String[] {
                    "hibernateLazyInitializer", "id", "erp2Supplier"
                }, "yyyy-MM-dd");
        logger.info(entity.getId());

        long supplierId = buyOrderValue.getLong("supplierName");
        Erp2Supplier supplier = erp2SupplierManager.get(supplierId);
        entity.setErp2Supplier(supplier);

        getEntityDao().save(entity);

        //
        List<Erp2BuyOrderInfo> infoList = JsonUtils.json2List(infos,
                Erp2BuyOrderInfo.class,
                new String[] {
                    "hibernateLazyInitializer", "id", "erp2BuyOrder",
                    "erp2Product"
                }, "yyyy-MM-dd");

        for (int i = 0; i < infoList.size(); i++) {
            Erp2BuyOrderInfo info = infoList.get(i);
            info.setErp2BuyOrder(entity);

            JSONObject jsonInfo = infos.getJSONObject(i);
            long productId = jsonInfo.getLong("productId");
            Erp2Product product = erp2ProductManager.get(productId);
            info.setErp2Product(product);

            erp2BuyOrderInfoManager.save(info);
        }

        response.getWriter().print("{success:true}");
    }

    /**
     * 分页浏览记录.
     *
     * @throws Exception 异常
     */
    @Override
    public void pagedQuery() throws Exception {
        // 分页
        int pageSize = getIntParam("limit", 1);
        int start = getIntParam("start", 0);
        int pageNo = (start / pageSize) + 1;

        // 排序
        String sort = getStrParam("sort", null);
        String dir = getStrParam("dir", "asc");

        // 查询
        // 订单编号
        String code = getStrParam("code", "").trim();

        // 状态
        int status = getIntParam("status", -1);

        // 订单名称
        String name = getStrParam("name", "").trim();

        // 采购期期
        String orderDate = getStrParam("orderDate", "").trim();

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

        if (status != -1) {
            criteria = criteria.add(Restrictions.eq("status", status));
        }

        if (!name.equals("")) {
            criteria = criteria.add(Restrictions.like("name",
                        "%" + name + "%"));
        }

        if (!orderDate.equals("")) {
            criteria = criteria.add(Restrictions.eq("orderDate",
                        DateUtils.str2Date(orderDate)));
        }

        Page page = getEntityDao().pagedQuery(criteria, pageNo, pageSize);

        JsonUtils.write(page, response.getWriter(), getExcludes(),
            getDatePattern());
    }

    /** * @throws Exception json可能出现的异常. */
    public void getBuyOrderInfosByBuyOrder() throws Exception {
        long id = getLongParam("id", -1L);
        JsonUtils.write(getEntityDao().get(id).getErp2BuyOrderInfos(),
            response.getWriter(),
            new String[] {
                "erp2BuyOrder", "hibernateLazyInitializer", "erp2Supplier",
                "erp2BuyOrderInfos", "erp2Bids"
            }, getDatePattern());
    }

    /** * view. */
    public void view() {
        long id = getLongParam("id", -1L);
        mv.addObject("entity", getEntityDao().get(id));
        mv.setViewName("aerp2/erp2buyorder/view");
    }

    /**
     * audit审核.
     */
    public void audit() {
        long id = getLongParam("id", -1L);
        Erp2BuyOrder entity = getEntityDao().get(id);
        entity.setStatus(1);
        getEntityDao().save(entity);
        mv.setViewName("aerp2/erp2buyorder/audit");
    }

    /**
     * dismissed驳回.
     */
    public void dismissed() {
        long id = getLongParam("id", -1L);
        Erp2BuyOrder entity = getEntityDao().get(id);
        entity.setStatus(0);
        getEntityDao().save(entity);
        mv.setViewName("aerp2/erp2buyorder/dismissed");
    }

    /** * @param erp2ProductManager Erp2ProductManager. */
    public void setErp2ProductManager(
        Erp2ProductManager erp2ProductManager) {
        this.erp2ProductManager = erp2ProductManager;
    }

    /** * @param erp2BuyOrderInfoManager Erp2BuyOrderInfoManager. */
    public void setErp2BuyOrderInfoManager(
        Erp2BuyOrderInfoManager erp2BuyOrderInfoManager) {
        this.erp2BuyOrderInfoManager = erp2BuyOrderInfoManager;
    }

    /** * @param erp2SupplierManager Erp2SupplierManager. */
    public void setErp2SupplierManager(
        Erp2SupplierManager erp2SupplierManager) {
        this.erp2SupplierManager = erp2SupplierManager;
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {
            "hibernateLazyInitializer", "region", "province", "city",
            "town", "erp2BuyOrderInfos"
        };
    }

    /** * @return datePattern. */
    @Override
    public String getDatePattern() {
        return "yyyy-MM-dd";
    }
}
