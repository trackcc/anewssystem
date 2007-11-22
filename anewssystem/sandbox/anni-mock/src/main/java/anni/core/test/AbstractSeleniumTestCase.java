package anni.core.test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.openqa.selenium.server.SeleniumServer;


/**
 * Selenium集成测试基类.
 * <p/>
 * 负责启动和关闭selenium服务。 可以在selenium.properties 设置Explorer类型 和 待测网站的BaseURL
 * 如果没有设置,Explorer默认为"*iexplore", BaseURL默认为"http://localhost:8080"
 * 来自www.springside.org.cn.
 *
 * @author calvin
 * @author Lingo
 * @since 2007-06-01
 * @version 1.0
 */
public abstract class AbstractSeleniumTestCase extends TestCase {
    // statis fields
    //
    /**
     * 默认使用ie作为测试浏览器.
     */
    protected static final String DEFAULT_EXPLORER = "*iexplorer";

    /**
     * 默认的baseurl.
     */
    protected static final String DEFAULT_BASEURL = "http://localhost:8080/";

    /**
     * 等待页面加载的时间，默认是5秒.
     */
    protected static final String DEFAULT_TIME = "5000";

    /**
     * commons-configuration.
     */
    private static PropertiesConfiguration config = new PropertiesConfiguration();

    static {
        try {
            config.load("selenium.properties");
        } catch (ConfigurationException e) {
            // 客户没有自定义selenium.properties属正常情况
            System.out.println("客户没有自定义selenium.properties属正常情况");
        }
    }

    // fields
    //
    /**
     * Selenium变量,命名为user,子类使用时可读性较高.
     */
    protected Selenium user;

    /**
     * 每个测试之前，构造默认的selenium，并启动.
     */
    @Override
    public void setUp() {
        user = new DefaultSelenium("localhost",
                SeleniumServer.DEFAULT_PORT, getExplorer(), getBaseURL());
        user.start();
    }

    /**
     * 测试完成后，停止selenium.
     */
    @Override
    public void tearDown() {
        user.stop();
    }

    /**
     * 返回模拟的浏览器类型. 先从selenium.properties中读取selenium.explorer,如果未设定则使用默认值.
     *
     * @return String 测试用浏览器，*iexplorer / *firefox
     */
    public static String getExplorer() {
        return config.getString("selenium.explorer", DEFAULT_EXPLORER);
    }

    /**
     * 返回网站基本URL. 先从selenium.properties中读取selenium.baseurl,如果未设定则使用默认值.
     *
     * @return String 测试用baseurl
     */
    public static String getBaseURL() {
        return config.getString("selenium.baseurl", DEFAULT_BASEURL);
    }
}
