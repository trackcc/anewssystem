package anni.database;

import java.util.*;


public class Table {
    private int x;
    private int y;
    private int width;
    private int height;
    private String name;
    private Map<String, Column> columns = new HashMap<String, Column>();
    private Map<String, Pk> pks = new HashMap<String, Pk>();
    private Map<String, Fk> fks = new HashMap<String, Fk>();

    public Table(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        if (width == 0) {
            int max = name.length();

            for (Pk pk : pks.values()) {
                int len = pk.getColumn().toString().length();

                if (len > max) {
                    max = len;
                }
            }

            for (Column column : columns.values()) {
                int len = column.toString().length();

                if (len > max) {
                    max = len;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    public Map<String, Pk> getPks() {
        return pks;
    }

    public void setPks(Map<String, Pk> pks) {
        this.pks = pks;
    }

    public Map<String, Fk> getFks() {
        return fks;
    }

    public void setFks(Map<String, Fk> fks) {
        this.fks = fks;
    }

    //
    public String toString() {
        return "Table[" + name + "]";
    }
}
