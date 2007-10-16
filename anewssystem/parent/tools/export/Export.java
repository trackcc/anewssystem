
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class Export {
    public static void main(String[] args) throws Exception{
        Class.forName("org.hsqldb.jdbcDriver");
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:db/test;shutdown=true","sa","");

        // catalog 数据库
        //DatabaseMetaData dbMeta = conn.getMetaData();
        //ResultSet rs = dbMeta.getCatalogs();
        //rsInfo(rs);

        // schema 数据库
        //DatabaseMetaData dbMeta = conn.getMetaData();
        //ResultSet rs = dbMeta.getSchemas();
        //rsInfo(rs);

        // table 数据表
        DatabaseMetaData dbMeta = conn.getMetaData();
        ResultSet rs = dbMeta.getTables(null, "PUBLIC", null, null);
        //rsInfo(rs);
        List<String> tableNames = new ArrayList<String>();
        while (rs.next()) {
            tableNames.add(rs.getString(3));
        }

        // export
        PrintWriter out = new PrintWriter(new FileOutputStream("import.sql"));
        for (Iterator iter = tableNames.iterator(); iter.hasNext(); )
        {
            String tableName =(String) iter.next();
            Statement state = conn.createStatement();
            ResultSet resultSet = state.executeQuery("select * from " + tableName);

            ResultSet columnRs = dbMeta.getColumns(null, "PUBLIC", tableName, null);
            StringBuffer columns = new StringBuffer();
            List<String> types = new ArrayList<String>();
            while (columnRs.next()) {
                String columnName = columnRs.getString("COLUMN_NAME").toLowerCase();
                columns.append(columnName).append(",");
                types.add(columnRs.getString("TYPE_NAME"));
            }
            columns.deleteCharAt(columns.length() - 1);

            while (resultSet.next()) {
                StringBuffer buff = new StringBuffer();
                buff.append("insert into ").append(tableName).append("(").append(columns).append(") values(");

                for (int i = 0; i < types.size(); i++) {
					String type = types.get(i);
                    if ("BIGINT".equals(type) || "INTEGER".equals(type)) {
                        buff.append(resultSet.getString(i + 1)).append(",");
                    } else {
                        buff.append("'").append(resultSet.getString(i + 1)).append("',");
                    }
                }
                buff.deleteCharAt(buff.length() - 1);
                out.print(buff);
                out.println(");");
            }
            out.println("");
        }
        out.flush();
        out.close();
        conn.close();
    }

    static void rsInfo(ResultSet rs) throws Exception {
        ResultSetMetaData rsMeta = rs.getMetaData();
        int cols = rsMeta.getColumnCount();
        while (rs.next()) {
            for (int i = 0; i < cols; i++) {
                System.out.print(rs.getString(i + 1) + ",");
            }
            System.out.println("");
        }
    }
}
