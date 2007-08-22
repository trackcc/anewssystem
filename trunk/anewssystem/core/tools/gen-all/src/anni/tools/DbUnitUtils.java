
package anni.tools;

import java.io.*;
import java.sql.*;
import java.util.*;
import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.excel.*;

public class DbUnitUtils {
    public static void genDbUnitExcel(String dest) throws Exception {
        // hsqldb
        Class.forName("org.hsqldb.jdbcDriver");
        Connection jdbcConnection = DriverManager
            .getConnection("jdbc:hsqldb:file:target/hsqldb/test;shutdown=true", "sa", "");
        IDatabaseConnection con = new DatabaseConnection(jdbcConnection);
        //
        IDataSet dataset = con.createDataSet();
        FileOutputStream out = new FileOutputStream(dest + "/export.xls");
        XlsDataSet.write(dataset, out);
        out.close();
        con.close();
    }
}
