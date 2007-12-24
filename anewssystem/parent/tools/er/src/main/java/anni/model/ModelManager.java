package anni.model;

import java.util.*;

import anni.database.*;


public class ModelManager {
    private Map<String, TableModel> tableModels = new HashMap<String, TableModel>();
    private List<FkModel> fkModels = new ArrayList<FkModel>();

    //
    public Map<String, TableModel> getTableModels() {
        return tableModels;
    }

    public List<FkModel> getFkModels() {
        return fkModels;
    }

    //
    public void transform(Database database) {
        // tables
        Map<String, Table> tables = database.getTables();

        // 先设置所有的属性
        for (Table table : tables.values()) {
            String tableName = table.getName();
            TableModel tableModel = new TableModel(tableName);
            tableModels.put(tableName, tableModel);

            int index = 0;

            // 主键
            for (Pk pk : table.getPks().values()) {
                String name = pk.getColumn().toString();
                CellModel cellModel = new CellModel(name,
                        CellModel.TYPE_PK, index);
                tableModel.getLines().add(cellModel);
                index++;
            }

            // 普通
            for (Column column : table.getColumns().values()) {
                if (column.isPk() || column.isFk()) {
                    continue;
                }

                String name = column.toString();
                CellModel cellModel = new CellModel(name,
                        CellModel.TYPE_COLUMN, index);
                tableModel.getLines().add(cellModel);
                index++;
            }

            // 外键
            for (Fk fk : table.getFks().values()) {
                String name = fk.getFkColumn().toString();
                CellModel cellModel = new CellModel(name,
                        CellModel.TYPE_FK, index);
                tableModel.getLines().add(cellModel);
                index++;
            }
        }

        // 再计算x,y,w,h
        int x = 10;
        int y = 10;

        for (TableModel tableModel : tableModels.values()) {
            tableModel.setX(x);
            tableModel.setY(y);

            x += (tableModel.getW() + 10);

            if (x > 800) {
                x = 10;
                y += 280;
            }
        }

        // fks
        Map<String, Fk> fks = database.getFks();

        for (Fk fk : fks.values()) {
            String fkTableName = fk.getFkColumn().getTable().getName();
            String pkTableName = fk.getPkColumn().getTable().getName();
            TableModel fkTable = tableModels.get(fkTableName);
            TableModel pkTable = tableModels.get(pkTableName);

            FkModel fkModel = new FkModel(fk.getName(), fkTable, pkTable);
            fkModels.add(fkModel);
        }
    }

    public static ModelManager createModelManager(Database database) {
        ModelManager modelManager = new ModelManager();
        modelManager.transform(database);

        return modelManager;
    }
}
