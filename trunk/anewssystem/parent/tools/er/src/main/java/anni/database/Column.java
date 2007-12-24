package anni.database;

public class Column {
    private String name;
    private String typeName;
    private int typeSize;
    private boolean nullable;
    private Table table;
    private Pk pk;
    private Fk fk;

    public Column(String name, String typeName, int typeSize,
        boolean nullable) {
        this.name = name;
        this.typeName = typeName;
        this.typeSize = typeSize;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeSize() {
        return typeSize;
    }

    public void setTypeSize(int typeSize) {
        this.typeSize = typeSize;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    //
    public Pk getPk() {
        return pk;
    }

    public void setPk(Pk pk) {
        this.pk = pk;
    }

    public Fk getFk() {
        return fk;
    }

    public void setFk(Fk fk) {
        this.fk = fk;
    }

    //
    public boolean isPk() {
        return pk != null;
    }

    public boolean isFk() {
        return fk != null;
    }

    public String toString() {
        return name + " " + typeName + "(" + typeSize + ")";
    }
}
