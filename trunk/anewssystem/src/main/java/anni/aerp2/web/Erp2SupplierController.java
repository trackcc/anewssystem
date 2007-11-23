package anni.aerp2.web;

import java.util.List;

import anni.aerp2.domain.Erp2Product;
import anni.aerp2.domain.Erp2Supplier;

import anni.aerp2.manager.Erp2ProductManager;
import anni.aerp2.manager.Erp2SupplierManager;

import anni.asecurity.domain.Region;

import anni.asecurity.manager.RegionManager;

import anni.core.dao.support.Page;

import anni.core.grid.LongGridController;

import anni.core.json.JsonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


/**
 * @author Lingo.
 * @since 2007年11月03日 下午 12时54分44秒937
 */
public class Erp2SupplierController extends LongGridController<Erp2Supplier, Erp2SupplierManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(Erp2SupplierController.class);

    /** * regionManager. */
    private RegionManager regionManager = null;

    /** * erp2ProductManager. */
    private Erp2ProductManager erp2ProductManager = null;

    /** * constructor. */
    public Erp2SupplierController() {
        //setEditView("/aerp2/erp2supplier/editErp2Supplier");
        //setListView("/aerp2/erp2supplier/listErp2Supplier");
    }

    /** * @param regionManager RegionManager. */
    public void setRegionManager(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    /** * @param erp2ProductManager erp2ProductManager. */
    public void setErp2ProductManager(
        Erp2ProductManager erp2ProductManager) {
        this.erp2ProductManager = erp2ProductManager;
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp2/erp2supplier/index");
    }

    /**
     * insert添加一条供应商信息.
     *
     * @throws Exception json操作出现的异常
     */
    public void insert() throws Exception {
        String data = getStrParam("data", null);
        logger.info(data);

        JSONObject jsonObject = JSONObject.fromObject(data);
        JSONObject supplierValue = jsonObject.getJSONObject(
                "supplierValue");
        JSONArray products = jsonObject.getJSONArray("products");

        //
        Erp2Supplier entity = JsonUtils.json2Bean(supplierValue,
                Erp2Supplier.class,
                new String[] {
                    "hibernateLazyInitializer", "id", "province", "city",
                    "town", "erp2Supplier"
                }, "yyyy-MM-dd");
        logger.info(entity.getId());

        // 新需求要求：县是可以不填写的，如果不填写县，就把市当作region，设置到供应商信息中。
        // 我决定这是一个很正当的需求
        try {
            long townId = supplierValue.getLong("town");
            Region region = regionManager.get(townId);
            entity.setRegion(region);

            try {
                entity.setArea(region.getParent().getParent().getName()
                    + "-" + region.getParent().getName() + "-"
                    + region.getName());
            } catch (NullPointerException ex) {
                entity.setArea("");
            }
        } catch (Exception e) {
            // 如果取得town失败，就是说town发送过来是空的，就是没选，那么我们操作city
            long cityId = supplierValue.getLong("city");
            Region region = regionManager.get(cityId);
            entity.setRegion(region);

            try {
                entity.setArea(region.getParent().getName() + "-"
                    + region.getName());
            } catch (NullPointerException ex) {
                entity.setArea("");
            }
        }

        getEntityDao().save(entity);

        //
        List<Erp2Product> productList = JsonUtils.json2List(products,
                Erp2Product.class,
                new String[] {
                    "hibernateLazyInitializer", "id", "erp2Product"
                }, "yyyy-MM-dd");

        for (Erp2Product product : productList) {
            product.setErp2Supplier(entity);
            entity.getErp2Products().add(product);
            logger.info(product.getId());

            erp2ProductManager.save(product);
        }

        response.getWriter().print("{success:true}");
    }

    /**
     * update修改一条供应商信息.
     *
     * @throws Exception json可能抛出异常
     */
    public void update() throws Exception {
        String data = getStrParam("data", null);
        logger.info(data);

        JSONObject jsonObject = JSONObject.fromObject(data);
        JSONObject supplierValue = jsonObject.getJSONObject(
                "supplierValue");
        long id = supplierValue.getLong("id");
        JSONArray products = jsonObject.getJSONArray("products");

        //
        Erp2Supplier entity = getEntityDao().get(id);
        entity = JsonUtils.json2Bean(supplierValue, entity,
                new String[] {
                    "hibernateLazyInitializer", "province", "city", "town",
                    "erp2Supplier"
                }, "yyyy-MM-dd");
        logger.info(entity.getId());

        try {
            long townId = supplierValue.getLong("town");
            Region region = regionManager.get(townId);
            entity.setRegion(region);

            entity.setArea(region.getParent().getParent().getName() + "-"
                + region.getParent().getName() + "-" + region.getName());
        } catch (NullPointerException ex) {
            entity.setArea("");
        }

        getEntityDao().save(entity);

        for (int i = 0; i < products.size(); i++) {
            JSONObject jsonObj = products.getJSONObject(i);
            long productId = jsonObj.getLong("id");
            Erp2Product product = null;

            if (productId == -1L) {
                product = JsonUtils.json2Bean(jsonObj, Erp2Product.class,
                        new String[] {"id"}, "yyyy-MM-dd");
            } else {
                product = erp2ProductManager.get(productId);
                product = JsonUtils.json2Bean(jsonObj, product,
                        new String[] {"id"}, "yyyy-MM-dd");
            }

            product.setErp2Supplier(entity);
            entity.getErp2Products().add(product);
            erp2ProductManager.save(product);
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
        // 供应商名称
        String name = getStrParam("name", "").trim();

        // 类别
        int type = getIntParam("type", -1);

        // 联系人
        String linkman = getStrParam("linkman", "").trim();

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

        if (!linkman.equals("")) {
            criteria = criteria.add(Restrictions.like("linkman",
                        "%" + linkman + "%"));
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
                "erp2Products", "linkman", "type", "address", "rank",
                "lead", "code", "fax", "hibernateLazyInitializer", "region",
                "descn", "homepage", "email", "zip", "area", "tel",
                "totalPageCount", "pageSize", "currentPageNo",
                "erp2InviteBids"
            }, getDatePattern());
    }

    /**
     * 根据供应商的id活动对应的供应产品列表.
     *
     * @throws Exception json可能抛出异常
     */
    public void getProductsBySupplier() throws Exception {
        Long id = getLongParam("id", -1L);
        Erp2Supplier entity = getEntityDao().get(id);
        JsonUtils.write(entity.getErp2Products(), response.getWriter(),
            new String[] {
                "hibernateLazyInitializer", "erp2Products", "erp2Supplier",
                "region"
            }, "yyyy-MM-dd");
    }

    /**
     * 查看供应商信息.
     */
    public void view() {
        long id = getLongParam("id", -1L);
        Erp2Supplier entity = getEntityDao().get(id);
        mv.addObject("entity", entity);
        mv.setViewName("aerp2/erp2supplier/view");
    }

    /**
     * 获得地区，省市县id的json数组.
     *
     * @throws Exception 生成json可能出现异常
     */
    public void getRegion() throws Exception {
        long id = getLongParam("id", -1L);
        Erp2Supplier entity = getEntityDao().get(id);

        JsonUtils.write(entity.getRegion(), response.getWriter(),
            new String[] {
                "hibernateLazyInitializer", "children", "root", "theSort",
                "draggable", "allowEdit", "parentId", "cls", "descn",
                "leaf", "qtip", "allowDelete", "text", "allowChildren"
            }, "yyyy-MM-dd");
    }

    /** * @return excludes. */
    public String[] getExcludes() {
        return new String[] {
            "hibernateLazyInitializer", "region", "province", "city",
            "town", "erp2Supplier"
        };
    }
}
