package anni.aerp2.web;

import anni.aerp2.domain.Erp2InviteBid;
import anni.aerp2.domain.Erp2SuccessBid;

import anni.aerp2.manager.Erp2InviteBidManager;
import anni.aerp2.manager.Erp2SuccessBidManager;

import anni.core.grid.LongGridController;

import anni.core.web.prototype.BaseLongController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.validation.BindingResult;


/**
 * @author Lingo.
 * @since 2007年11月08日 上午 01时50分59秒625
 */
public class Erp2SuccessBidController extends LongGridController<Erp2SuccessBid, Erp2SuccessBidManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(Erp2SuccessBidController.class);

    /** * erp2InviteBidManager. */
    private Erp2InviteBidManager erp2InviteBidManager = null;

    /** * constructor. */
    public Erp2SuccessBidController() {
        //setEditView("/aerp2/erp2successbid/editErp2SuccessBid");
        //setListView("/aerp2/erp2successbid/listErp2SuccessBid");
    }

    /** * @param erp2InviteBidManager erp2InviteBidManager. */
    public void setErp2InviteBidManager(
        Erp2InviteBidManager erp2InviteBidManager) {
        this.erp2InviteBidManager = erp2InviteBidManager;
    }

    /** * index. */
    public void index() {
        logger.info("start");
        mv.setViewName("aerp2/erp2successbid/index");
    }

    /**
     * 添加中标结果.
     *
     * @throws Exception 异常
     */
    public void insert() throws Exception {
        logger.info(params());

        long id = getLongParam("id", -1L);

        Erp2SuccessBid entity = getEntityDao().get(id);

        if (entity == null) {
            entity = new Erp2SuccessBid();
        }

        BindingResult bindingResult = bindObject(request, entity);

        if (bindingResult.hasErrors()) {
            logger.info(bindingResult);
            response.getWriter().print("{success:false,info:'数据绑定错误'}");

            return;
        }

        long inviteBidId = getLongParam("supplierName", -1L);
        Erp2InviteBid inviteBid = erp2InviteBidManager.get(inviteBidId);
        entity.setErp2InviteBid(inviteBid);

        //
        entityDao.save(entity);
        logger.info("success");
        response.getWriter().print("{success:true}");
    }

    /** * view. */
    public void view() {
        long id = getLongParam("id", -1L);
        Erp2SuccessBid entity = getEntityDao().get(id);
        mv.addObject("entity", entity);
        mv.setViewName("aerp2/erp2successbid/view");
    }

    /** * @return excludes. */
    @Override
    public String[] getExcludes() {
        return new String[] {
            "hibernateLazyInitializer", "erp2SuccessBid", "region",
            "erp2Products", "erp2Bids", "erp2SuccessBids", "erp2Product",
            "erp2InviteBids"
        };
    }

    /** * @return datePattern. */
    @Override
    public String getDatePattern() {
        return "yyyy-MM-dd";
    }
}
