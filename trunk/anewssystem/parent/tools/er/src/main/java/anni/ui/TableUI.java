package anni.ui;

import java.io.*;

import anni.model.*;


public class TableUI {
    public void renderScripts(TableModel tableModel, PrintWriter out) {
        String name = tableModel.getName();
        double x = tableModel.getX();
        double y = tableModel.getY();
        double w = tableModel.getW();
        double h = tableModel.getH();
        out.println("    tables['" + name + "'] = new Table('" + name
            + "', " + x + "," + y + "," + w + "," + h + ");");
    }

    public void renderDefs(TableModel tableModel, PrintWriter out) {
        String tableName = tableModel.getName();
        double w = tableModel.getW();
        double h = tableModel.getH();

        out.println("        <g id='" + tableName + "'>");
        out.println("            <rect id='use_" + tableName
            + "_rect' x='0' y='0' width='" + w + "' height='" + h
            + "' style='fill:white; stroke:black;'/>");
        out.println("            <rect x='0' y='0' width='" + w
            + "' height='20' style='fill:yellow;'/>");
        out.println("            <text x='5' y='15'>" + tableName
            + "</text>");
        out.println("            <line x1='0' y1='20' x2='" + w
            + "' y2='20' style='stroke:black;'/>");

        for (CellModel cellModel : tableModel.getLines()) {
            int num = cellModel.getIndex();

            if (cellModel.getType() == CellModel.TYPE_PK) {
                out.println("            <rect x='0' y='"
                    + ((20 * num) + 20) + "' width='" + w
                    + "' height='20' style='fill:red;'/>");
                out.println("            <text x='5' y='"
                    + ((20 * num) + 35) + "' style='fill:white;'>"
                    + cellModel.getName() + "</text>");
            } else if (cellModel.getType() == CellModel.TYPE_FK) {
                out.println("            <rect x='0' y='"
                    + ((20 * num) + 20) + "' width='" + w
                    + "' height='20' style='fill:green;'/>");
                out.println("            <text x='5' y='"
                    + ((20 * num) + 35) + "' style='fill:white;'>"
                    + cellModel.getName() + "</text>");
            } else if (cellModel.getType() == CellModel.TYPE_COLUMN) {
                out.println("            <text x='5' y='"
                    + ((20 * num) + 35) + "' style='fill:black;'>"
                    + cellModel.getName() + "</text>");
            }

            out.println("            <line x1='0' y1='"
                + ((20 * num) + 40) + "' x2='" + w + "' y2='"
                + ((20 * num) + 40) + "' style='stroke:black;'/>");
        }

        out.println("        </g>");
    }

    public void renderBody(TableModel tableModel, PrintWriter out) {
        String tableName = tableModel.getName();
        double x = tableModel.getX();
        double y = tableModel.getY();
        out.println("    <use xlink:href='#" + tableName + "' id='use_"
            + tableName + "' x='" + x + "' y='" + y
            + "' style='cursor:move;' onmousedown='startDrag(evt, \"use_"
            + tableName + "\")' onmouseup='endDrag(\"use_" + tableName
            + "\")' />");
    }
}
