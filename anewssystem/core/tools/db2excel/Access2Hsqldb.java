
import java.io.*;
import java.sql.*;
import java.util.*;
import jxl.*;
import jxl.write.*;


public class Access2Hsqldb {
    static String catalog = null;
    static String schema = null;

    public static void main(String[] args) throws Exception {

        //Class.forName("org.hsqldb.jdbcDriver");
        //Connection conn = DriverManager
        //    .getConnection("jdbc:hsqldb:file:db/test;shutdown=true", "sa", "");
        //Class.forName("net.sourceforge.jtds.jdbc.Driver");
        //Connection conn = DriverManager
        //    .getConnection("jdbc:jtds:sqlserver://192.168.0.6:1433/ze;SelectMethod=cursor", "sa", "");
        //Class.forName("com.mysql.jdbc.Driver");
        //Connection conn = DriverManager
        //    .getConnection("jdbc:mysql://localhost:3306/data", "root", "");
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection conn = DriverManager
            .getConnection("jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=smt.mdb");

        // catalog 数据库
        //DatabaseMetaData dbMeta = conn.getMetaData();
        //ResultSet rs = dbMeta.getCatalogs();
        //rsInfo(rs);

        // schema 数据库
        //DatabaseMetaData dbMeta = conn.getMetaData();
        //ResultSet rs = dbMeta.getSchemas();
        //rsInfo(rs);
        //if (true) {
        //    return;
        //}

        // table 数据表
        DatabaseMetaData dbMeta = conn.getMetaData();
        ResultSet rs = dbMeta.getTables(catalog, schema, null, null);
        //rsInfo(rs);
        List<String> tableNames = new ArrayList<String>();
        while (rs.next()) {
            if ("SYSTEM TABLE".equals(rs.getString(4))) {
                continue;
            }
            tableNames.add(rs.getString(3));
        }
        //System.out.println(tableNames + "," + tableNames.size());
        WritableWorkbook workbook = Workbook.createWorkbook(new File("test.xls"));

        for (int i = 0; i < tableNames.size(); i++) {
            String tableName = tableNames.get(i);
            //System.out.println(" -- " + tableName + " -- ");
            WritableSheet sheet = workbook.createSheet(tableName.toLowerCase(), i);
            insertHeader(sheet);
            List<String> primaryKeys = null;
            try {
                primaryKeys =  getPrimaryKeys(dbMeta, tableName);
            } catch (Exception ex) {
                //System.err.println(ex);
                primaryKeys = new ArrayList<String>();
            }
			//System.out.println(primaryKeys);

            ResultSet columnRs = dbMeta.getColumns(catalog, schema, tableName, null);
            //rsInfo(columnRs);
            int index = 1;
			if (primaryKeys.size() == 0) {
				sheet.addCell(new Label(0, index, "id"));
				sheet.addCell(new Label(1, index, "bigint"));
				sheet.addCell(new Label(2, index, ""));
				sheet.addCell(new Label(3, index, ""));
				sheet.addCell(new Label(4, index, "primary key"));
				sheet.addCell(new Label(5, index, "increment"));
				sheet.addCell(new Label(6, index, ""));
			    index = 2;
			}
            while (columnRs.next()) {
                String columnName = columnRs.getString("COLUMN_NAME").toLowerCase();

                sheet.addCell(new Label(0, index, columnName));
                String type = columnRs.getString("TYPE_NAME");
                if (type.equalsIgnoreCase("varchar") || type.equalsIgnoreCase("char")) {
                    type += "(" + columnRs.getInt("COLUMN_SIZE") + ")";
                } else if (type.equalsIgnoreCase("text") || type.equalsIgnoreCase("longchar")) {
                    type = "varchar(2000)";
				} else if (type.equalsIgnoreCase("byte")) {
				    type = "tinyint";
                } else if (type.equalsIgnoreCase("longbinary")) {
				    type = "binary";
                } else if (primaryKeys.contains(columnName)) {
                    type = "bigint";
                sheet.addCell(new Label(0, index, "id"));
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
        //ResultSet rs = dbMeta.getPrimaryKeys(catalog, schema, tableName);
        //List<String> primaryKeys = new ArrayList<String>();
        //rsInfo(rs);
        //while (rs.next()) {
        //    primaryKeys.add(rs.getString("COLUMN_NAME"));
        //}
        //return primaryKeys;

        List<String> primaryKeys = new ArrayList<String>();
        ResultSet rs = dbMeta.getColumns(catalog, schema, tableName, null);
		//rsInfo(rs);
        while (rs.next()) {
			if (rs.getString("TYPE_NAME").equals("COUNTER")) {
			    primaryKeys.add(rs.getString("COLUMN_NAME").toLowerCase());
			}
        }
		//System.out.println(primaryKeys);
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