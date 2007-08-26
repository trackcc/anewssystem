package anni.core.test;

import org.springframework.test.web.AbstractModelAndViewTests;

import org.springframework.web.servlet.ModelAndView;


/**
 * 调用AbstractModelAndViewTests的工具类，提供一些简便测试ModelAndView的方法.
 *
 * @author Lingo
 * @since 2007-06-01
 * @version 1.0
 */
public class ModelAndViewTestHelper extends AbstractModelAndViewTests {
    /**
     * 测试mv中是否存在key.
     *
     * @param mv ModelAndView
     * @param key Object
     */
    public void assertModelAttributeAvailable(ModelAndView mv, Object key) {
        super.assertModelAttributeAvailable(mv, key);
    }

    /**
     * 测试view的名字.
     * 实际上我常常使用的默认约定是不设定viewName的，所以返回是null
     * 实际返回的view会在之后的ViewResolver进行计算
     *
     * @param mv ModelAndView
     * @param name view name
     */
    public void assertViewName(ModelAndView mv, String name) {
        super.assertViewName(mv, name);
    }
}
