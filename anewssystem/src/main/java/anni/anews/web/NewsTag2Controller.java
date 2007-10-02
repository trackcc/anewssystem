package anni.anews.web;

import anni.anews.domain.NewsTag;

import anni.anews.manager.NewsTagManager;

import anni.core.grid.LongGridController;

import anni.core.web.prototype.StreamView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lingo.
 * @since 2007年08月16日 下午 23时13分12秒765
 */
public class NewsTag2Controller extends LongGridController<NewsTag, NewsTagManager> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(NewsTagController.class);

    /** * constructor. */
    public NewsTag2Controller() {
        //setEditView("/anews/newstag/editNewsTag");
        //setListView("/anews/newstag/listNewsTag");
    }

    /** * inlineManage. */
    public void inlineManage() {
        mv.setViewName("anews/newstag/inlineManage");
    }

    /** * dialogManage. */
    public void dialogManage() {
        mv.setViewName("anews/newstag/dialogManage");
    }

    /**
     * insertRow.
     *
     * @throws Exception response.getWriter().print()可能出现异常
     */
    public void insertRow() throws Exception {
        String name = getStrParam("name", "");

        if (!"".equals(name)) {
            getEntityDao().createOrGet(name);
        }

        mv.setView(new StreamView("application/json"));
        response.getWriter().print("{success:true,info:'success'}");
    }

    /**
     * updateRow.
     *
     * @throws Exception response.getWriter().print()可能出现异常
     */
    public void updateRow() throws Exception {
        long id = getLongParam("id", -1L);
        logger.info(id);

        NewsTag newsTag = getEntityDao().get(id);

        if (newsTag != null) {
            String name = getStrParam("name", "");
            logger.info(name);
            newsTag.setName(name);
            getEntityDao().save(newsTag);
        }

        logger.info(newsTag);

        mv.setView(new StreamView("application/json"));
        response.getWriter().print("{success:true,info:'success'}");
    }

    /**
     * loadData.
     *
     * @throws Exception response.getWriter().print()可能出现异常
     */
    public void loadData() throws Exception {
        long id = getLongParam("id", -1L);

        if (id != -1L) {
            NewsTag newsTag = getEntityDao().get(id);
            String json = "[{id:" + id + ",name:'" + newsTag.getName()
                + "'}]";
            logger.info(json);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json);
        }

        mv.setView(new StreamView("application/json"));
    }
}
