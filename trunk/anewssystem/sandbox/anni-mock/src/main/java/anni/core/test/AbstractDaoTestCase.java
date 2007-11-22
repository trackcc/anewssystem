package anni.core.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * 使用DbUnit和spring测试hibernate3的dao.
 *
 * @since 2007-03-19
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
