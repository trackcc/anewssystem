package anni.core.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.Server;


/**
 * 启动关闭hsqldb的工具类.
 *
 * @author Lingo
 * @since 2007-06-01
 * @version 1.0
 */
public final class HsqldbHelper {
    /**
     * private construactor.
     */
    private HsqldbHelper() {
    }

    /**
     * 启动服务器.
     */
    private void startServer() {
        try {
            Server server = new Server();
            server.setDatabaseName(0, "test");
            server.setDatabasePath(0, "target/hsqldb/test");
            server.setPort(9003);
            server.setSilent(true);
            server.start();
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 关闭服务器.
     */
    private void stopServer() {
        Connection conn = null;
        Statement state = null;

        try {
            Class.forName("org.hsqldb.jdbcDriver");

            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9003/test",
                    "sa", "");
            state = conn.createStatement();
            state.executeUpdate("SHUTDOWN;");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (state != null) {
                try {
                    state.close();
                    state = null;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 入口.
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            HsqldbHelper hsqldb = new HsqldbHelper();

            if ("startServer".equals(args[0])) {
                hsqldb.startServer();
            } else if ("stopServer".equals(args[0])) {
                hsqldb.stopServer();
            }
        }
    }
}
