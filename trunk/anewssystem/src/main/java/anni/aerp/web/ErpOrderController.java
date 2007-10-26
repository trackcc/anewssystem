package anni.aerp.web;

import java.text.SimpleDateFormat;

import anni.aerp.domain.ErpOrder;
import anni.aerp.domain.ErpOrderInfo;

import anni.aerp.manager.ErpOrderInfoManager;
import anni.aerp.manager.ErpOrderManager;

import anni.core.grid.LongGridController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年10月25日 上午 02时55分02秒125
 */
public class ErpOrderController extends LongGridController<ErpOrder, ErpOrderManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(ErpOrderController.class);

    /** * sdf. */
    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd");

    /** * ErpOrderInfoManager. */
    private ErpOrderInfoManager erpOrderInfoManager = null;

    /** * constructor. */
    public ErpOrderController() {
    }

    /** * @param erpOrderInfoManager ErpOrderInfoManager. */
    public void setErpOrderInfoManager(
        ErpOrderInfoManager erpOrderInfoManager) {
        this.erpOrderInfoManager = erpOrderInfoManager;
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp/erporder/index");
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {"hibernateLazyInitializer", "erpOrder"};
    }

    /**
     * 保存订单信息.
     *
     * @throws Exception 异常
     */
    public void insert() throws Exception {
        String data = getStrParam("data", "");
        JSONObject jsonObject = JSONObject.fromObject(data);
        String orderDateStr = jsonObject.getString("orderDate");
        String customer = jsonObject.getString("customer");
        String linkMan = jsonObject.getString("linkMan");
        String tel = jsonObject.getString("tel");
        String amount = jsonObject.getString("amount");

        ErpOrder entity = new ErpOrder();
        entity.setOrderDate(sdf.parse(orderDateStr));
        entity.setCustomer(customer);
        entity.setLinkMan(linkMan);
        entity.setTel(tel);
        entity.setAmount(amount);
        getEntityDao().save(entity);

        JSONArray jsonArray = jsonObject.getJSONArray("infos");

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            ErpOrderInfo info = new ErpOrderInfo();
            info.setProduct(item.getString("product"));
            info.setType(item.getString("type"));
            info.setUnit(item.getString("unit"));
            info.setHeight(item.getString("height"));
            info.setWidth(item.getString("width"));
            info.setArea(item.getString("area"));
            info.setPrice(item.getString("price"));
            info.setAmount(item.getString("amount"));
            info.setStatus(item.getString("status"));
            info.setNum(item.getString("num"));
            info.setUnitType(item.getString("unitType"));

            erpOrderInfoManager.save(info);
            entity.getErpOrderInfos().add(info);
        }

        getEntityDao().save(entity);
    }
}
