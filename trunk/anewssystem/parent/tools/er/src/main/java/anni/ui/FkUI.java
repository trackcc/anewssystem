package anni.ui;

import java.io.*;

import anni.model.*;


public class FkUI {
    public void renderScripts(FkModel fkModel, PrintWriter out) {
        out.println("    fks['" + fkModel.getName() + "'] = new Fk('"
            + fkModel.getName() + "', '" + fkModel.getFrom().getName()
            + "', '" + fkModel.getTo().getName() + "');");
    }

    public void renderDefs(FkModel fkModel, PrintWriter out) {
    }

    public void renderBody(FkModel fkModel, PrintWriter out) {
        String name = fkModel.getName();
        TableModel from = fkModel.getFrom();
        TableModel to = fkModel.getTo();

        double x1 = fkModel.getX1();
        double y1 = fkModel.getY1();
        double x2 = fkModel.getX2();
        double y2 = fkModel.getY2();

        double dx1 = fkModel.getDx1();
        double dy1 = fkModel.getDy1();
        double dx2 = fkModel.getDx2();
        double dy2 = fkModel.getDy2();

        double textX = fkModel.getTextX();
        double textY = fkModel.getTextY();

        double w = from.getW();
        double h = from.getH();

        out.println("    <g id='" + name + "'>");

        // 如果是自关联
        if (from == to) {
            out.println("        <polyline id='" + name
                + "_line' points='" + (x1 + (w / 2)) + " " + y1 + ","
                + (x1 + w) + " " + y1 + "," + (x1 + w) + " " + (y1 - h)
                + "," + x1 + "," + (y1 - h) + "," + x1 + " "
                + (y1 - (h / 2))
                + "' style='fill:none;stroke:blue;stroke-width:2;'/>");
            //out.println("        <rect id='" + name + "_rect' x='" + x1
            //    + "' y='" + (y1 - from.getH()) + "' width='" + from.getW()
            //    + "' height='" + from.getH()
            //    + "' style='fill:none;stroke:blue;stroke-width:2;'/>");
            out.println("        <polyline id='" + name
                + "_polyline' points='" + x2 + " " + (y2 - (h / 2)) + ","
                + (x2 - 10) + " " + (y2 - 20 - (h / 2)) + "," + (x2 + 10)
                + " " + (y2 - 20 - (h / 2)) + "' style='fill:blue;'/>");
            out.println("        <text id='" + name + "_text' x='" + x1
                + "' y='" + (y1 - (from.getH() / 2)) + "'>" + name
                + "</text>");
        } else {
            out.println("        <line id='" + name + "_line' x1='" + x1
                + "' y1='" + y1 + "' x2='" + x2 + "' y2='" + y2
                + "' style='stroke:blue; stroke-width:2;'/>");
            out.println("        <polyline id='" + name
                + "_polyline' points='" + x2 + " " + y2 + "," + dx1 + " "
                + dy1 + "," + dx2 + " " + dy2 + "' style='fill:blue;'/>");
            out.println("        <text id='" + name + "_text' x='" + textX
                + "' y='" + textY + "'>" + name + "</text>");
        }

        out.println("    </g>");
    }
}
