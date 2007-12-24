package anni.database;

public class Pk {
    private Column column;

    public Pk(Column column) {
        this.column = column;
    }

    public Column getColumn() {
        return column;
    }

    public String toString() {
        return "Pk[" + column.getName() + "]";
    }
}
