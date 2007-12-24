package anni.model;

public class CellModel {
    public static final int TYPE_COLUMN = 0;
    public static final int TYPE_PK = 1;
    public static final int TYPE_FK = 2;
    private String name; // 名字
    private int type; // 类型
    private int index; // 行号

    public CellModel(String name, int type, int index) {
        this.name = name;
        this.type = type;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    //
    public String toString() {
        return "CellModel[" + name + "]";
    }
}
