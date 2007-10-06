package anni.anews.web;

import anni.anews.domain.News;
import anni.anews.domain.NewsCategory;

import anni.anews.manager.NewsCategoryManager;

import anni.core.test.AbstractWebTests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NewsControllerTest extends AbstractWebTests {
    protected static Log logger = LogFactory.getLog(NewsControllerTest.class);
    private NewsCategoryManager newsCategoryManager;

    public void setNewsCategoryManager(
        NewsCategoryManager newsCategoryManager) {
        this.newsCategoryManager = newsCategoryManager;
    }

    /** * setup */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        controller = (NewsController) applicationContext.getBean(
                "anni.anews.web.NewsController");
    }

    /** * tearDown */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }

    public void testDefault() throws Exception {
        assertNotNull(controller);
        request.setRequestURI("/news/index.htm");
        mv = controller.handleRequest(request, response);
        //mvHelper.assertModelAttributeAvailable(mv, "page");
        mvHelper.assertViewName(mv, "anews/news/index");
    }

    /*
        // 测试按分类搜索的情况
        public void testListByCategory() throws Exception {
            assertNotNull(controller);
            request.setRequestURI("/news/list.htm");
            request.addParameter("category_id", "1");
            mv = controller.handleRequest(request, response);
            mvHelper.assertModelAttributeAvailable(mv, "categoryId");
            mvHelper.assertModelAttributeAvailable(mv, "page");
            mvHelper.assertViewName(mv, "/anews/news/listNews");
        }
        public void testInsert() throws Exception {
            request.setRequestURI("/news/insert.htm");
            mv = controller.handleRequest(request, response);
            mvHelper.assertViewName(mv, "/anews/news/editNews");
        }
    */

    /*
        public void testInsert2() throws Exception {
            request.setRequestURI("/news/insert.htm");
            request.addParameter("name", "name");
            request.addParameter("subtitle", "subtitle");
            request.addParameter("content", "content");
            request.addParameter("tags", "11,22");
            request.addParameter("category_id", "1");
            mv = controller.handleRequest(request, response);
            mvHelper.assertViewName(mv, "redirect:/news/list.htm?status=1");
        }
    public void testUpdate() throws Exception {
        request.setRequestURI("/news/update.htm");
    
        try {
            mv = controller.handleRequest(request, response);
            fail("不可能成功");
        } catch (Throwable ex) {
            assertTrue(true);
        }
    }
    
    */
    public void testChangeStatus() throws Exception {
        request.setRequestURI("/news/changeStatus.htm");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }

    public void testChangeStatus2() throws Exception {
        request.setRequestURI("/news/changeStatus.htm");
        request.addParameter("status", "1");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }

    public void testChangeStatus3() throws Exception {
        request.setRequestURI("/news/changeStatus.htm");
        request.addParameter("status", "1");
        request.addParameter("itemlist", "1");
        mv = controller.handleRequest(request, response);
        mvHelper.assertViewName(mv, null);
    }

    /*
        public void testSearch() throws Exception {
            request.setRequestURI("/news/search.htm");
            mv = controller.handleRequest(request, response);
            mvHelper.assertViewName(mv, "/anews/news/search");
        }
    
        public void testSearch2() throws Exception {
            request.setRequestURI("/news/search.htm");
            request.addParameter("keywords", "test");
            mv = controller.handleRequest(request, response);
            mvHelper.assertModelAttributeAvailable(mv, "page");
            mvHelper.assertViewName(mv, "/anews/news/search");
        }
    */
}
