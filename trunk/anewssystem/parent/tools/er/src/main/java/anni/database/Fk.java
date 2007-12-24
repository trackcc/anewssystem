package anni.database;

public class Fk {
    private Column pkColumn;
    private Column fkColumn;
    private String name;

    public Column getPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(Column pkColumn) {
        this.pkColumn = pkColumn;
    }

    public Column getFkColumn() {
        return fkColumn;
    }

    public void setFkColumn(Column fkColumn) {
        this.fkColumn = fkColumn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
