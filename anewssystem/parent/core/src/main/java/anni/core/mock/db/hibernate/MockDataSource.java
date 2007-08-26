package anni.core.mock.db.hibernate;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


public class MockDataSource implements DataSource {
    public Connection getConnection() throws SQLException {
        return null;
    }

    public Connection getConnection(String arg0, String arg1)
        throws SQLException {
        return null;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter arg0) throws SQLException {
    }

    public void setLoginTimeout(int arg0) throws SQLException {
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }
}
