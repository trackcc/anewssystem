package anni.model.geom;

public class Line {
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    // 斜率
    public double getK() {
        return (y2 - y1) / (x2 - x1);
    }

    // y = kx + d
    public double getD() {
        return y1 - (getK() * x1);
    }

    // 是否平行
    public boolean isParallel(Line line) {
        // 因为double精度不够，如果差值小于某个值，就当它是相同

        // 两条线都竖直，绝对是平行的
        if ((Math.abs(x1 - x2) < 0.01)
                && (Math.abs(line.getX1() - line.getX2()) < 0.01)) {
            return true;
        } else if ((Math.abs(x1 - x2) < 0.01)
                && (Math.abs(line.getX1() - line.getX2()) > 0.01)) {
            // 一条竖直，另一条不竖直，肯定不是平行的
            return false;
        } else if ((Math.abs(x1 - x2) > 0.01)
                && (Math.abs(line.getX1() - line.getX2()) < 0.01)) {
            return false;
        } else {
            // 如果两条线都不是竖直的，就计算斜率
            return Math.abs(this.getK() - line.getK()) < 0.01;
        }
    }

    // 是否是同一条线
    public boolean isSameLine(Line line) {
        if (isParallel(line)) {
            // 平行，而有一个点在另一条线上，则是同一条线
            double k = line.getK();
            double d = line.getD();

            if (Math.abs(((x1 * k) + d) - y1) < 0.01) {
                return true;
            } else {
                return false;
            }
        } else {
            // 如果不平行，就不用考虑了
            return false;
        }
    }

    // 交点在线上，不在延长线上
    public boolean contains(Point p) {
        double x = p.getX();
        double y = p.getY();

        double s = ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2));
        double s1 = ((x - x1) * (x - x1)) + ((y - y1) * (y - y1));
        double s2 = ((x - x2) * (x - x2)) + ((y - y2) * (y - y2));

        return (s > s1) && (s > s2);
    }

    // 求交点
    public Point getCrossPoint(Line line) {
        // 斜率相同，可能平行，可能重合
        if (isParallel(line)) {
            return null;
        }

        double x;
        double y;

        if (Math.abs(x1 - x2) < 0.01) {
            // 如果当前这条线垂直
            // y = k * x + d
            x = x1;
            y = (line.getK() * x) + line.getD();
        } else if (Math.abs(line.getX1() - line.getX2()) < 0.01) {
            // 如果连接线垂直
            x = line.getX1();
            y = this.getD();
        } else {
            double k1 = this.getK();
            double k2 = line.getK();

            double d1 = this.getD();
            double d2 = line.getD();
            // y = k1 * x + d1
            // y = k2 * x + d2
            x = (d2 - d1) / (k1 - k2);
            y = (k1 * x) + d1;
        }

        Point p = new Point(x, y);

        if (line.contains(p) && this.contains(p)) {
            return p;
        } else {
            return null;
        }
    }

    //
    public String toString() {
        return "Line[" + x1 + "," + y1 + "," + x2 + "," + y2 + "]";
    }
}
