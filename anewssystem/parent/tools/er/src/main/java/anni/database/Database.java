package anni.database;

import java.sql.*;

import java.util.*;


/**
 * 1.读取所有外键，外键值放到Database里
 * 2.获得所有表，表放到Database里
 * 3.获得表里的列，根据主键和外键修改列，所有列放到表里
 *
 */
public class Database {
    // sql
    private String driverName = "org.hsqldb.jdbcDriver";
    private String username = "sa";
    private String password = "";
    private String url = "jdbc:hsqldb:file:db/er";

    //
    private Map<String, Table> tables = new HashMap<String, Table>();
    private Map<String, Fk> fks = new HashMap<String, Fk>();

    public Database(String driverName, String username, String password,
        String url) {
        this.driverName = driverName;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public Map<String, Fk> getFks() {
        return fks;
    }

    // ==========================================
    public static Database createDatabase(String username,
        String password, String driverName, String url)
        throws Exception {
        Database database = new Database(driverName, username, password,
                url);

        database.fetchTables();

        return database;
    }

    // ==========================================
    public void fetchTables() throws Exception {
        Class.forName(driverName);

        Connection conn = DriverManager.getConnection(url, username,
                password);
        DatabaseMetaData dbMeta = conn.getMetaData();
        ResultSet rs = dbMeta.getTables(null, "PUBLIC", null, null);

        // 拿到所有表
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            Table table = new Table(tableName);
            tables.put(tableName, table);
        }

        // 给这些表添上列
        for (Table table : tables.values()) {
            String tableName = table.getName();

            ResultSet columnRs = dbMeta.getColumns(null, "PUBLIC",
                    tableName, null);

            while (columnRs.next()) {
                String columnName = columnRs.getString("COLUMN_NAME")
                                            .toLowerCase();
                columnName = columnName.toUpperCase();

                String typeName = columnRs.getString("TYPE_NAME");
                int typeSize = columnRs.getInt("COLUMN_SIZE");
                boolean nullable = columnRs.getInt("NULLABLE") == 0;

                Column column = new Column(columnName, typeName, typeSize,
                        nullable);
                column.setTable(table);
                table.getColumns().put(columnName, column);
            }
        }

        // 给表加上主键
        for (Table table : tables.values()) {
            String tableName = table.getName();

            ResultSet pkrs = dbMeta.getPrimaryKeys(null, "PUBLIC",
                    tableName);

            while (pkrs.next()) {
                String pkName = pkrs.getString("COLUMN_NAME");
                Column column = table.getColumns().get(pkName);
                Pk pk = new Pk(column);
                column.setPk(pk);
                table.getPks().put(column.getName(), pk);
            }
        }

        // 给表加上外键
        for (Table table : tables.values()) {
            String tableName = table.getName();

            ResultSet fkrs = dbMeta.getImportedKeys(null, "PUBLIC",
                    tableName);

            while (fkrs.next()) {
                String pkColumnName = fkrs.getString("PKCOLUMN_NAME");
                String pkTableName = fkrs.getString("PKTABLE_NAME");
                String fkColumnName = fkrs.getString("FKCOLUMN_NAME");
                String fkTableName = fkrs.getString("FKTABLE_NAME");
                String fkName = fkrs.getString("FK_NAME");

                Fk fk = new Fk();
                fk.setName(fkName);
                fk.setFkColumn(table.getColumns().get(fkColumnName));
                fk.setPkColumn(tables.get(pkTableName).getColumns()
                                     .get(pkColumnName));
                table.getFks().put(fkName, fk);

                table.getColumns().get(fkColumnName).setFk(fk);
                fks.put(fk.getName(), fk);
            }
        }

        conn.close();
    }

    // ==========================================
    private static void rsInfo(ResultSet rs) throws Exception {
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
