package anni.model.geom;

public class Rect {
    private double x;
    private double y;
    private double w;
    private double h;

    public Rect(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public Point getCrossPoint(Line line) {
        Point p = null;

        Line top = new Line(x, y, x + w, y);
        p = top.getCrossPoint(line);

        if (p != null) {
            return p;
        }

        Line bottom = new Line(x, y + h, x + w, y + h);
        p = bottom.getCrossPoint(line);

        if (p != null) {
            return p;
        }

        Line left = new Line(x, y, x, y + h);
        p = left.getCrossPoint(line);

        if (p != null) {
            return p;
        }

        Line right = new Line(x + w, y, x + w, y + h);
        p = right.getCrossPoint(line);

        return p;
    }

    //
    public String toString() {
        return "Rect[" + x + "," + y + "," + w + "," + h + "]";
    }
}
