package anni.core.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;

import org.dbunit.operation.DatabaseOperation;


/**
 * 仅暴露Dbunit对Excel的支持.
 * 是因为几种数据提供方案中，Excel编辑数据最容易，在实践中被使用的最广泛。 支持每次Setup是否清空数据的选择
 * 来自www.springside.org.cn.
 *
 * @author Anders.Lin
 * @author Lingo
 * @since 2007-06-01
 * @version 1.0
 */
public class DbunitHelper {
    // statis fields
    //
    /** * 默认配置文件名. */
    public static final String DEFAULT_CONFIG_FILE = "dbunit.properties";

    /** * 默认的jdbc驱动. */
    public static final String DEFAULT_DRIVER = "org.hsqldb.jdbcDriver";

    /** * 默认的数据库密码. */
    public static final String DEFAULT_PASS = "";

    /** * 默认的数据库URL. */
    public static final String DEFAULT_URL = "jdbc:hsqldb:mem:.";

    /** * 默认的数据库用户. */
    public static final String DEFAULT_USER = "sa";

    /** * 驱动key. */
    public static final String DRIVER = "connection.driver_class";

    /** * 密码key. */
    public static final String PASS = "connection.password";

    /** * url key. */
    public static final String URL = "connection.url";

    /** * 用户名key. */
    public static final String USER = "connection.username";

    /** * xls路径key. */
    public static final String XLSPATH = "xls.path";

    /** * commons-configuration. */
    private static PropertiesConfiguration config = new PropertiesConfiguration();

    /** * logger. */
    private static Log logger = LogFactory.getLog(DbunitHelper.class);

    // fields
    //
    /** * 是否已经使用dbunit进行过初始化. */
    private boolean alreadySetUp = false;

    /** * 是否在每次测试之前都使用dbunit进行初始化. */
    private boolean cleanInsertEachSetup = false;

    /** * 密码. */
    private String connectionPassword;

    /** * URL. */
    private String connectionUrl;

    /** * 用户名. */
    private String connectionUser;

    /** * 驱动类名. */
    private String driverClazz;

    /** * xls路径. */
    private String xlsPath;

    /**
     * 构造方法中使用默认的配置文件进行初始化.
     */
    public DbunitHelper() {
        doInit(DEFAULT_CONFIG_FILE);
    }

    /**
     * 使用Properties进行初始化.
     *
     * @param properties 使用的Properties
     */
    public void doInit(Properties properties) {
        if ((properties == null) || properties.isEmpty()) {
            return;
        }

        this.driverClazz = properties.getProperty(DRIVER);
        this.connectionUrl = properties.getProperty(URL);
        this.connectionUser = properties.getProperty(USER);
        this.connectionPassword = properties.getProperty(PASS);
    }

    /**
     * 进行初始化.
     * 实际调用doInit(Properties properties)
     *
     * @param path 培植文件所在的路径
     */
    public void doInit(String path) {
        try {
            config.load(path);
        } catch (ConfigurationException e) {
            logger.error("could not load the properties file : " + path, e);
        }

        Properties properties = new Properties();
        properties.setProperty(DRIVER,
            config.getString(DRIVER, DEFAULT_DRIVER));
        properties.setProperty(URL, config.getString(URL, DEFAULT_URL));
        properties.setProperty(USER, config.getString(USER, DEFAULT_USER));
        properties.setProperty(PASS, config.getString(PASS, DEFAULT_PASS));
        doInit(properties);
    }

    /**
     * Close the specified connection.
     * Ovverride this method of you want to keep your connection alive between tests.
     *
     * @param connection 关闭连接
     * @throws Exception 关闭异常
     */
    protected void closeConnection(IDatabaseConnection connection)
        throws Exception {
        connection.close();
    }

    /**
     * 设定是否每次SetUp都清空数据.
     *
     * @param flag 是否每次测试之前都清空数据 true / false
     */
    public void doCleanInsertEachSetup(boolean flag) {
        this.cleanInsertEachSetup = flag;
    }

    /**
     * 执行操作.
     *
     * @param operation 执行的操作
     * @throws Exception 执行操作时发生的异常
     */
    private void executeOperation(DatabaseOperation operation)
        throws Exception {
        if (operation != DatabaseOperation.NONE) {
            IDatabaseConnection connection = getConnection();

            try {
                operation.execute(connection, getDataSet());
            } finally {
                closeConnection(connection);
            }
        }
    }

    /**
     * Returns the test database connection.
     *
     * @return IDatabaseConnection 返回一个数据库连接
     * @throws Exception 执行操作时发生的异常
     */
    public IDatabaseConnection getConnection() throws Exception {
        Class.forName(this.driverClazz);

        Connection jdbcConnection = DriverManager.getConnection(this.connectionUrl,
                this.connectionUser, this.connectionPassword);

        return new DatabaseConnection(jdbcConnection);
    }

    /**
     * Returns the test dataset.
     * 从xls文件中生成DataSet并返回
     *
     * @return IDataSet dataSet
     * @throws Exception 读取数据时可能发生的异常
     */
    protected IDataSet getDataSet() throws Exception {
        /*
         * IDatabaseConnection con = getConnection();
         * IDataSet dataset = con.createDataSet();
         * XlsDataSet.write(dataset, new FileOutputStream("export.xls"));
         */
        return new XlsDataSet(new FileInputStream(xlsPath));
    }

    /**
     * 返回jdbc连接.
     *
     * @return Connection
     * @throws Exception 连接错误
     */
    public Connection getJdbcConnection() throws Exception {
        return this.getConnection().getConnection();
    }

    /**
     * Returns the database operation executed in test setup.
     *
     * @return DatabaseOperation 每次测试之前需要进行的操作
     *  CLEAN_INSERT清空，并重新插入xls的数据
     *  NONE什么也不做
     * @throws Exception 执行初始化可能发生的错误
     */
    protected DatabaseOperation getSetUpOperation() throws Exception {
        if (!this.alreadySetUp) {
            alreadySetUp = true;
            preCleanAndInsert();

            return DatabaseOperation.CLEAN_INSERT;
        }

        if (this.cleanInsertEachSetup) {
            preCleanAndInsert();

            return DatabaseOperation.CLEAN_INSERT;
        }

        return DatabaseOperation.NONE;
    }

    /**
     * 这个是我自定义的方法，用来解决dbunit不能清空复杂关系数据的情况.
     * 具体情况下需要自己重写这个方法
     * FIXME: 我也许应该把这个方法写成abstract的？
     *
     * @throws Exception 执行初始化可能发生的错误
     */
    protected void preCleanAndInsert() throws Exception {
        Connection conn = null;
        Statement state = null;

        try {
            conn = getJdbcConnection();
            state = conn.createStatement();

            BufferedReader in = null;

            try {
                in = new BufferedReader(new InputStreamReader(
                            DbunitHelper.class.getClassLoader()
                                              .getResourceAsStream("dbunit-clean.sql")));

                String line = null;

                while ((line = in.readLine()) != null) {
                    state.executeUpdate(line);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                    in = null;
                }
            }

            //state.executeUpdate("delete from comment");
            //state.executeUpdate("delete from article");
            //state.executeUpdate("delete from category");
            //state.executeUpdate("delete from role_menu");
            //state.executeUpdate(
            //    "delete from menu where parent_id is not null");
            //state.executeUpdate("delete from menu");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        }
    }

    /**
     * 设置xls文件的路径.
     *
     * @param path xls文件的路径
     */
    public void setXlsPath(String path) {
        xlsPath = path;
    }

    /**
     * Returns the database operation executed in test cleanup.
     *
     * @return DatabaseOperation 获得测试结束后的操作，NONE什么也不做
     * @throws Exception 应该不会发生错误
     */
    protected DatabaseOperation getTearDownOperation()
        throws Exception {
        return DatabaseOperation.NONE;
    }

    /**
     * 使用dbunit对数据库进行初始化.
     */
    public void setUp() {
        try {
            executeOperation(getSetUpOperation());
        } catch (Exception e) {
            //throw new RuntimeException("error when doing dbunit setUp", e);
            e.printStackTrace();
        }
    }

    /**
     * 执行测试结束时的操作.
     */
    public void tearDown() {
        try {
            executeOperation(getTearDownOperation());
        } catch (Exception e) {
            throw new RuntimeException("error when doing dbunit tearDown",
                e);
        }
    }
}
