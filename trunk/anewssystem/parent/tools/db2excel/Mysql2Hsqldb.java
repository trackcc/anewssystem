
import java.io.*;
import java.sql.*;
import java.util.*;
import jxl.*;
import jxl.write.*;


public class Mysql2Hsqldb {

    public static void main(String[] args) throws Exception {

        //Class.forName("org.hsqldb.jdbcDriver");
        //Connection conn = DriverManager
        //    .getConnection("jdbc:hsqldb:file:db/test;shutdown=true", "sa", "");
        //Class.forName("net.sourceforge.jtds.jdbc.Driver");
        //Connection conn = DriverManager
        //    .getConnection("jdbc:jtds:sqlserver://192.168.0.6:1433/ze;SelectMethod=cursor", "sa", "");
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/data", "root", "");

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
        ResultSet rs = dbMeta.getTables("data", null, null, null);
        //rsInfo(rs);
        List<String> tableNames = new ArrayList<String>();
        while (rs.next()) {
            tableNames.add(rs.getString(3));
        }
        //System.out.println(tableNames + "," + tableNames.size());
        WritableWorkbook workbook = Workbook.createWorkbook(new File("test.xls"));

        for (int i = 0; i < tableNames.size(); i++) {
            String tableName = tableNames.get(i);
            System.out.println(" -- " + tableName + " -- ");
            WritableSheet sheet = workbook.createSheet(tableName, i);
            insertHeader(sheet);
            List<String> primaryKeys = getPrimaryKeys(dbMeta, tableName);

            ResultSet columnRs = dbMeta.getColumns("data", null, tableName, null);
            //rsInfo(columnRs);
            int index = 1;
            while (columnRs.next()) {
                String columnName = columnRs.getString("COLUMN_NAME").toLowerCase();

                sheet.addCell(new Label(0, index, columnName));
                String type = columnRs.getString("TYPE_NAME");
                if (type.equalsIgnoreCase("varchar") || type.equalsIgnoreCase("char")) {
                    type += "(" + columnRs.getInt("COLUMN_SIZE") + ")";
                } else if (type.equalsIgnoreCase("text")) {
                    type = "varchar(2000)";
                } else if (primaryKeys.contains(columnName)) {
                    type = "bigint";
                }
                sheet.addCell(new Label(1, index, type));
                if (columnRs.getInt("NULLABLE") == 0 && !primaryKeys.contains(columnName)) {
                    sheet.addCell(new Label(2, index, "not null"));
                } else {
                    sheet.addCell(new Label(2, index, ""));
                }
                sheet.addCell(new Label(3, index, ""));
                sheet.addCell(new Label(4, index, columnName));
                if (primaryKeys.contains(columnName)) {
                    sheet.addCell(new Label(5, index, "increment"));
                } else {
                    sheet.addCell(new Label(5, index, ""));
                }
                sheet.addCell(new Label(6, index, ""));
                index++;
            }
        }
        workbook.write();
        workbook.close();

        conn.close();
    }

    static void insertHeader(WritableSheet sheet) throws Exception {
        sheet.addCell(new Label(0, 0, "字段名"));
        sheet.addCell(new Label(1, 0, "字段类型"));
        sheet.addCell(new Label(2, 0, "null"));
        sheet.addCell(new Label(3, 0, "default"));
        sheet.addCell(new Label(4, 0, "描述"));
        sheet.addCell(new Label(5, 0, "主键"));
        sheet.addCell(new Label(6, 0, "外键"));
    }

    static List<String> getPrimaryKeys(DatabaseMetaData dbMeta, String tableName) throws Exception {
        ResultSet rs = dbMeta.getPrimaryKeys("data", null, tableName);
        List<String> primaryKeys = new ArrayList<String>();
        //rsInfo(rs);
        while (rs.next()) {
            primaryKeys.add(rs.getString("COLUMN_NAME"));
        }
        return primaryKeys;
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