
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class EntityRelationship {
    // sql
    String driverName = "org.hsqldb.jdbcDriver";
    String username = "sa";
    String password = "";
    //String url = "jdbc:hsqldb:hsql://192.168.1.127:9103/shop";
    String url = "jdbc:hsqldb:file:db/test";

    //
    Connection conn;
    DatabaseMetaData dbMeta;

    public List<Table> getTables() throws Exception {
        ResultSet rs = dbMeta.getTables(null, "PUBLIC", null, null);
        //List<String> tableNames = new ArrayList<String>();
        List<Table> tables = new ArrayList<Table>();

        while (rs.next()) {
            String tableName = rs.getString(3);
            //tableNames.add(tableName);

            Table table = new Table(tableName);
            tables.add(table);

            List<String> primaryKeys = getPrimaryKeys(dbMeta, tableName);
            List<ForeignKey> foreignKeys = getForeignKeys(dbMeta, tableName);

            ResultSet columnRs = dbMeta.getColumns(null, "PUBLIC", tableName, null);
            while (columnRs.next()) {

                String columnName = columnRs.getString("COLUMN_NAME").toLowerCase();

                String type = columnRs.getString("TYPE_NAME");
                if (type.equalsIgnoreCase("varchar") || type.equalsIgnoreCase("char")) {
                    type += "(" + columnRs.getInt("COLUMN_SIZE") + ")";
                } else if (type.equalsIgnoreCase("text")) {
                    type = "varchar(2000)";
                } else if (primaryKeys.contains(columnName)) {
                    type = "bigint";
                }

                String nullable;
                if (columnRs.getInt("NULLABLE") == 0 && !primaryKeys.contains(columnName)) {
                    nullable = "not null";
                } else {
                    nullable = "";
                }

                if (primaryKeys.contains(columnName.toUpperCase())) {
                    //pk = "increment";
                    table.pks.add(columnName + " " + type);
                } else {
                    boolean flag = true;
                    for (ForeignKey fk : foreignKeys) {
                        if (fk.fkColumnName.equals(columnName.toUpperCase())) {
                            fk.type = type;
                            table.fks.add(fk);
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        table.columns.add(columnName + " " + type);
                    }
                }
            }

        }
        return tables;
    }

    public String drawTable(Table table, int index) throws Exception {
        //int w = tableName.length() * 10;
        String tableName = table.name;
        int w = table.getWidth();
        int h = 20 * (table.pks.size() + table.columns.size() + 1);

        String str = "        <g id='" + tableName + "'>\r\n" +
            "            <rect x='0' y='0' width='" + w + "' height='" + h + "' style='fill:white; stroke:black;'/>\r\n" +
            "            <rect x='0' y='0' width='" + w + "' height='20' style='fill:yellow;'/>\r\n" +
            "            <text x='5' y='15'>" + tableName + "</text>\r\n" +
            "            <line x1='0' y1='20' x2='" + w + "' y2='20' style='stroke:black;'/>\r\n";

        int num = 0;
        for (int i = 0; i < table.pks.size(); i++) {
            String column = table.pks.get(i);
            str += "            <rect x='0' y='" + (20 * i + 20) + "' width='" + w + "' height='20' style='fill:red;'/>\r\n";
            str += "            <text x='5' y='" + (20 * i + 35) + "' style='fill:white;'>" + column + "</text>\r\n";
            str += "            <line x1='0' y1='" + (20 * i + 40) + "' x2='" + w + "' y2='" + (20 * i + 40) + "' style='stroke:black;'/>\r\n";

            num = i;
        }

        int num2 = 0;
        for (int i = 0; i < table.columns.size(); i++) {
            String column = table.columns.get(i);

            int k = i + num + 1;
            str += "            <text x='5' y='" + (20 * k + 35) + "'>" + column + "</text>\r\n";
            str += "            <line x1='0' y1='" + (20 * k + 40) + "' x2='" + w + "' y2='" + (20 * k + 40) + "' style='stroke:black;'/>\r\n";

            num2 = k;
        }

        for (int i = 0; i < table.fks.size(); i++) {
            ForeignKey fk = table.fks.get(i);

            int k = i + num2 + 1;
            str += "            <rect x='0' y='" + (20 * k + 20) + "' width='" + w + "' height='20' style='fill:green;'/>\r\n";
            str += "            <text x='5' y='" + (20 * k + 35) + "' style='fill:white;'>" + fk.fkColumnName + " " + fk.type + "</text>\r\n";
            str += "            <line x1='0' y1='" + (20 * k + 40) + "' x2='" + w + "' y2='" + (20 * k + 40) + "' style='stroke:black;'/>\r\n";
        }

        str += "        </g>\r\n";
        return str;
    }

    public void drawTables(List<Table> tables) throws Exception {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("shop.svg"), "utf-8"));
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.0//EN' 'http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd'>");
        out.println("<svg xmlns:xlink='http://www.w3.org/1999/xlink' xmlns='http://www.w3.org/2000/svg' width='1000' height='2500' viewBox='0 0 1000 2500'>");
        out.println("    <script type='text/ecmascript'>");
        out.println("    <![CDATA[");
        out.println();
        out.println("    var target = null;");
        out.println();
        out.println("    function startDrag(id) {");
        out.println("        target = document.getElementById(id);");
        out.println("    }");
        out.println();
        out.println("    function endDrag(id) {");
        out.println("        target = null;");
        out.println("    }");
        out.println();
        out.println("    function doDrag(evt, id) {");
        out.println("        if (target != null) {");
        out.println("            var x = evt.clientX;");
        out.println("            var y = evt.clientY;");
        out.println("            target.setAttribute('x', x - 50);");
        out.println("            target.setAttribute('y', y - 50);");
        out.println("        }");
        out.println("    }");
        out.println("    ]]>");
        out.println("    </script>");
        out.println("    <defs>");

        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            System.out.println(" -- " + table.name + " -- ");

            String tableSvg = drawTable(table, i);
            out.println(tableSvg);
        }

        out.println("    </defs>");

        int x = 10;
        int y = 10;
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            String tableName = table.name;
            table.x = x;
            table.y = y;

            out.println("    <use xlink:href='#" + table.name + "' id='use_" + tableName + "' x='" + x + "' y='" + y + "'  onmousedown='startDrag(\"use_" + tableName + "\")' onmouseup='endDrag(\"use_" + tableName + "\")' onmousemove='doDrag(evt, \"use_" + tableName + "\")'/>");
            x += table.getWidth() + 10;
            if (x > 800) {
                x = 10;
                y += 280;
            }
        }

        out.println();
        for (Table table : tables) {
            //System.out.println(" - " + table.name);

            for (ForeignKey fk : table.fks) {
                //System.out.println(fk.fkName + " - " + fk.pkTableName);
                int x1 = table.x;
                int y1 = table.y;
                int w1 = table.getWidth();
                int h1 = table.getHeight();
                Table fkTable = findTable(tables, fk.pkTableName);
                int x2 = fkTable.x;
                int y2 = fkTable.y;
                int w2 = fkTable.getWidth();
                int h2 = fkTable.getHeight();

                int xx1 = x1 + w1 / 2;
                int yy1 = y1 + h1 / 2;
                int xx2 = x2 + w2 / 2;
                int yy2 = y2 + h2 / 2;
                out.println("    <line x1='" + xx1 + "' y1='" + yy1 + "' x2='" + xx2 + "' y2='" + yy2 + "' style='stroke:blue; stroke-width:2;'/>");
                out.println("    <circle cx='" + xx2 + "' cy='" + yy2 + "' r='5' style='fill:blue;'/>");
                out.println("    <text x='" + ((xx1 + xx2) / 2) + "' y='" + ((yy1 + yy2) / 2) + "'>" + fk.fkName + "</text>");
            }
        }
        out.println();

        out.println("</svg>");
        out.flush();
        out.close();
    }

    Table findTable(List<Table> tables, String tableName) {
        for (Table table : tables) {
            if (table.name.equals(tableName)) {
                return table;
            }
        }
        return null;
    }

    // ====================================================
    public void execute() throws Exception {
        //
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        dbMeta = conn.getMetaData();

        //
        List<Table> tables = getTables();
        drawTables(tables);

        //
        conn.close();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("start");
        EntityRelationship er = new EntityRelationship();
        er.execute();
        System.out.println("end");
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

    static List<String> getPrimaryKeys(DatabaseMetaData dbMeta, String tableName) throws Exception {
        ResultSet rs = dbMeta.getPrimaryKeys(null, "PUBLIC", tableName);
        List<String> primaryKeys = new ArrayList<String>();
        //rsInfo(rs);
        while (rs.next()) {
            primaryKeys.add(rs.getString("COLUMN_NAME"));
        }
        return primaryKeys;
    }

    static List<ForeignKey> getForeignKeys(DatabaseMetaData dbMeta, String tableName) throws Exception {
        ResultSet rs = dbMeta.getImportedKeys(null, "PUBLIC", tableName);
        List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
        //rsInfo(rs);
        while (rs.next()) {
            String pkColumnName = rs.getString("PKCOLUMN_NAME");
            String pkTableName = rs.getString("PKTABLE_NAME");
            String fkColumnName = rs.getString("FKCOLUMN_NAME");
            String fkTableName = rs.getString("FKTABLE_NAME");
            String fkName = rs.getString("FK_NAME");
            foreignKeys.add(new ForeignKey(pkColumnName, pkTableName, fkColumnName, fkTableName, fkName));
        }
        //System.out.println(foreignKeys);
        return foreignKeys;
    }
}

class Table {
    int x;
    int y;
    int width;
    int height;

    String name;
    List<String> columns = new ArrayList<String>();
    List<String> pks = new ArrayList<String>();
    List<ForeignKey> fks = new ArrayList<ForeignKey>();

    public Table(String name) {
        this.name = name;
    }

    public int getWidth() {
        if (width == 0) {
            int max = name.length();
            for (String pk : pks) {
                if (pk.length() > max) {
                    max = pk.length();
                }
            }
            for (String column : columns) {
                if (column.length() > max) {
                    max = column.length();
                }
            }
            width = max * 10;
        }
        return width;
    }

    public int getHeight() {
        if (height == 0) {
            height = 20 * (1 + pks.size() + columns.size());
        }
        return height;
    }
}

class ForeignKey{
    String pkColumnName;
    String pkTableName;
    String fkColumnName;
    String fkTableName;
    String fkName;
    String type;

    public ForeignKey(String pkColumnName,
                    String pkTableName,
                    String fkColumnName,
                    String fkTableName,
                    String fkName) {
        this.pkColumnName = pkColumnName;
        this.pkTableName = pkTableName;
        this.fkColumnName = fkColumnName;
        this.fkTableName = fkTableName;
        this.fkName = fkName;
    }
}

