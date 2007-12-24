package anni.model;

import java.util.*;

import anni.database.*;


public class TableModel {
    private String name;
    private double x;
    private double y;
    private double w;
    private double h;
    private List<CellModel> lines = new ArrayList<CellModel>();

    public TableModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getW() {
        if (w == 0) {
            int max = name.length();

            for (CellModel cellModel : lines) {
                int len = cellModel.getName().length();

                if (len > max) {
                    max = len;
                }
            }

            w = max * 10;
        }

        return w;
    }

    public double getH() {
        if (h == 0) {
            h = 20 * (1 + lines.size());
        }

        return h;
    }

    public List<CellModel> getLines() {
        return lines;
    }

    public void setLines(List<CellModel> lines) {
        this.lines = lines;
    }

    //
    public String toString() {
        return "TableModel[" + name + "," + x + "," + y + "," + w + ","
        + h + "]";
    }
}
