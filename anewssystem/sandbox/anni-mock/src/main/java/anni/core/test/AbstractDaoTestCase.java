package anni.core.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * 使用spring测试hibernate3的dao.
 * 默认从spring/*.xml和classpath:test/*.xml读取配置文件，
 * 注意：spring-mock会根据getConfigLocations()的值来缓存ctx
 * 此处与AbstractWebTests相同，会导致AbstractWebTests提取scope="request"类型的bean失效
 * 这个类只适用于单独使用，或者不需要考虑测试scope的时候与其他类合作
 * 如果需要测试scope="request"的类，必须都替换成AbstractWebTests避免出现问题
 *
 * @author Lingo
 */
public class AbstractDaoTestCase
    extends AbstractTransactionalDataSourceSpringContextTests {
    /**
     * spring测试时，获得xml的路径.
     *
     * @return xml的路径数组
     * @see AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);

        return new String[] {
            "classpath*:spring/*.xml", "classpath*:test/*.xml"
        };
    }

    /**
     * 初始化.
     *
     * @throws Exception 可能抛出异常
     */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
    }

    /**
     * 关闭.
     *
     s* @throws Exception 可能抛出异常
     */
    @Override
    public void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
    }
}
