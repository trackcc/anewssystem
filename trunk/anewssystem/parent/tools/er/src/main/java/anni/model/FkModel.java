package anni.model;

import anni.model.geom.*;


public class FkModel {
    public static final double SIN0 = Math.sin(0.5);
    public static final double COS0 = Math.cos(0.5);
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private String name;
    private TableModel from;
    private TableModel to;
    private double angle;
    private double sin;
    private double cos;
    private double dx1;
    private double dy1;
    private double dx2;
    private double dy2;
    private double textX;
    private double textY;

    public FkModel(String name, TableModel from, TableModel to) {
        this.name = name;
        this.from = from;
        this.to = to;

        initBeginAndEnd();
        initArrow();
        initText();
    }

    private void initBeginAndEnd() {
        double fromX = from.getX();
        double fromY = from.getY();
        double fromW = from.getW();
        double fromH = from.getH();
        double toX = to.getX();
        double toY = to.getY();
        double toW = to.getW();
        double toH = to.getH();

        double xx1 = fromX + (fromW / 2);
        double yy1 = fromY + (fromH / 2);
        double xx2 = toX + (toW / 2);
        double yy2 = toY + (toH / 2);

        if (from != to) {
            Line fromLine = new Line(xx1, yy1, xx2, yy2);
            Rect fromRect = new Rect(fromX, fromY, fromW, fromH);
            Point fromCrossPoint = fromRect.getCrossPoint(fromLine);

            if (fromCrossPoint != null) {
                this.x1 = fromCrossPoint.getX();
                this.y1 = fromCrossPoint.getY();
            } else {
                this.x1 = xx1;
                this.y1 = yy1;
            }

            Line toLine = new Line(xx1, yy1, xx2, yy2);
            Rect toRect = new Rect(toX, toY, toW, toH);
            Point toCrossPoint = toRect.getCrossPoint(toLine);

            if (toCrossPoint != null) {
                this.x2 = toCrossPoint.getX();
                this.y2 = toCrossPoint.getY();
            } else {
                this.x2 = xx2;
                this.y2 = yy2;
            }
        } else {
            this.x1 = xx1;
            this.y1 = yy1;
            this.x2 = xx2;
            this.y2 = yy2;
        }
    }

    private void initArrow() {
        double a = x1 - x2;
        double b = y1 - y2;
        double c = Math.sqrt((a * a) + (b * b));
        sin = b / c;
        cos = a / c;

        double sin1 = (sin * COS0) + (cos * SIN0);
        double cos1 = (cos * COS0) - (sin * SIN0);
        double sin2 = (sin * COS0) - (cos * SIN0);
        double cos2 = (cos * COS0) + (sin * SIN0);
        dy1 = y2 + (sin1 * 20);
        dx1 = x2 + (cos1 * 20);
        dy2 = y2 + (sin2 * 20);
        dx2 = x2 + (cos2 * 20);
    }

    private void initText() {
        textX = ((x1 + x2) / 2) - (name.length() * 5);
        textY = (y1 + y2) / 2;
    }

    public String getName() {
        return name;
    }

    public TableModel getFrom() {
        return from;
    }

    public TableModel getTo() {
        return to;
    }

    //
    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    //
    public double getSin() {
        return sin;
    }

    public double getCos() {
        return cos;
    }

    //
    public double getDx1() {
        return dx1;
    }

    public double getDy1() {
        return dy1;
    }

    public double getDx2() {
        return dx2;
    }

    public double getDy2() {
        return dy2;
    }

    //
    public double getTextX() {
        return textX;
    }

    public double getTextY() {
        return textY;
    }
}
