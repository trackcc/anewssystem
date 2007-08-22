
package anni.tools;

import java.util.*;

public class Bean {
    public String tableName;
    public String type;
    public List<Row> rowList = new ArrayList<Row>();

    public Bean(String tableName) {
        this.tableName = tableName;
    }

    public List<Row> getRowList() {
        return rowList;
    }

    // transient
    public String getClassName() {
        String[] tmp = tableName.split("_");
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < tmp.length; i++) {
            String name = tmp[i].substring(0, 1).toUpperCase() + tmp[i].substring(1);
            buff.append(name);
        }
        return buff.toString();
    }

    public String getEntityName() {
        String className = getClassName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    public boolean isDisplay() {
		return type == null;
    }
}
