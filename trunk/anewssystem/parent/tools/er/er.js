
Point = function(x, y) {
    this.x = x;
    this.y = y;
};

Line = function(x1, y1, x2, y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
};

Line.prototype.getX1 = function() {
    return this.x1;
};
Line.prototype.getX2 = function() {
    return this.x2;
};
Line.prototype.getY1 = function() {
    return this.y1;
};
Line.prototype.getY2 = function() {
    return this.y2;
};

// 斜率
Line.prototype.getK = function() {
    return (this.y2 - this.y1) / (this.x2 - this.x1);
};

// y = kx + d
Line.prototype.getD = function() {
    return this.y1 - this.getK() * this.x1;
};

// 是否平行
Line.prototype.isParallel = function(line) {
    // 因为double精度不够，如果差值小于某个值，就当它是相同
    var x1 = this.x2;
    var x2 = this.x2;

    // 两条线都竖直，绝对是平行的
    if ((Math.abs(x1 - x2) < 0.01) && (Math.abs(line.getX1() - line.getX2()) < 0.01)) {
        return true;
    } else if ((Math.abs(x1 - x2) < 0.01) && (Math.abs(line.getX1() - line.getX2()) > 0.01)) {
        // 一条竖直，另一条不竖直，肯定不是平行的
        return false;
    } else if ((Math.abs(x1 - x2) > 0.01) && (Math.abs(line.getX1() - line.getX2()) < 0.01)) {
        return false;
    } else {
        // 如果两条线都不是竖直的，就计算斜率
        return Math.abs(this.getK() - line.getK()) < 0.01;
    }
};

// 是否是同一条线
Line.prototype.isSameLine = function(line) {
    if (this.isParallel(line)) {
        // 平行，而有一个点在另一条线上，则是同一条线
        var k = line.getK();
        var d = line.getD();
        if (Math.abs(this.x1 * k + d - this.y1) < 0.01) {
            return true;
        } else {
            return false;
        }
    } else {
        // 如果不平行，就不用考虑了
        return false;
    }
};

// 交点在线上，不在延长线上
Line.prototype.contains = function(p) {
    var x1 = this.x1;
    var y1 = this.y1;
    var x2 = this.x2;
    var y2 = this.y2;

    var x = p.x;
    var y = p.y;

    var s = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    var s1 = (x - x1) * (x - x1) + (y - y1) * (y - y1);
    var s2 = (x - x2) * (x - x2) + (y - y2) * (y - y2);

    return s > s1 && s > s2;
};

Line.prototype.getCrossPoint = function(line) {
    // 斜率相同，可能平行，可能重合
    if (this.isParallel(line)) {
        return null;
    }

    var x;
    var y;

    if (Math.abs(this.x1 - this.x2) < 0.01) {
        // 如果当前这条线垂直
        // y = k * x + d
        x = this.x1;
        y = line.getK() * x + line.getD();
    } else if (Math.abs(line.getX1() - line.getX2()) < 0.01) {
        // 如果连接线垂直
        x = line.getX1();
        y = this.getD();
    } else {
        var k1 = this.getK();
        var k2 = line.getK();

        var d1 = this.getD();
        var d2 = line.getD();
        // y = k1 * x + d1
        // y = k2 * x + d2
        x = (d2 - d1) / (k1 - k2);
        y = k1 * x + d1;
    }

    var p = new Point(x, y);
    if (line.contains(p) && this.contains(p)) {
        return p;
    } else {
        return null;
    }
};

Rect = function(x, y, w, h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
};

Rect.prototype.getCrossPoint = function(line) {
    var p = null;

    var top  = new Line(this.x, this.y, this.x + this.w, this.y);
    p = top.getCrossPoint(line);
    if (p != null) {
        return p;
    }
    var bottom = new Line(this.x, this.y + this.h, this.x + this.w, this.y + this.h);
    p = bottom.getCrossPoint(line);
    if (p != null) {
        return p;
    }
    var left = new Line(this.x, this.y, this.x, this.y + this.h);
    p = left.getCrossPoint(line);
    if (p != null) {
        return p;
    }
    var right = new Line(this.x + this.w, this.y, this.x + this.w, this.y + this.h);
    p = right.getCrossPoint(line);

    return p;
};

Table = function(name, x, y, w, h) {
    this.name = name;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
};

Fk = function(name, from, to) {
    this.name = name;
    this.from = tables[from];
    this.to = tables[to];
};

var tables = {};
var fks = {};

// 拖拽需要这两个东西
var target = null;
var dx = 0;
var dy = 0;

function startDrag(evt, id) {
    target = document.getElementById(id);
    dx = target.getAttribute('x') - evt.clientX;
    dy = target.getAttribute('y') - evt.clientY;
}

function endDrag(id) {
    // 更新拖拽后的坐标
    tables[id.substring(4)].x = parseFloat(target.getAttribute('x'));
    tables[id.substring(4)].y = parseFloat(target.getAttribute('y'));

    // 计算哪些外键连接线受到影响
    var items = [];
    for (var i in fks) {
        item = fks[i];
        if ("use_" + item.from.name == id || "use_" + item.to.name == id) {
            items.push(item);
        }
    }

    // 重画受到影响的连接线
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        var from = item.from;
        var to = item.to;

        var line = document.getElementById(item.name + '_line');
        var polyline = document.getElementById(item.name + '_polyline');
        var text = document.getElementById(item.name + '_text');

        if (from.name == to.name) {

            var x1 = from.x + from.w / 2;
            var y1 = from.y + from.h / 2;
            var x2 = to.x + to.w / 2;
            var y2 = to.y + to.h / 2;

            var w = from.w;
            var h = from.h;

            // line
            line.setAttribute('points', (x1 + w / 2) + " " + y1 + "," +
                                        (x1 + w) + " " + y1 + "," +
                                        (x1 + w) + " " + (y1 - h) + "," +
                                        x1 + "," + (y1 - h) + "," +
                                        x1 + " " + (y1 - h / 2));

            // text
            text.setAttribute('x', x1);
            text.setAttribute('y', (y1 - h / 2));

            // polyline
            polyline.setAttribute('points', x2 + ' ' + (y2 - h / 2) + ',' +
                                            (x2 - 10) + ' ' + (y2 - 20 - h / 2) + ',' +
                                            (x2 + 10) + ' ' + (y2 - 20 - h / 2));

        } else {
            var fromX = from.x;
            var fromY = from.y;
            var fromW = from.w;
            var fromH = from.h;
            var toX = to.x;
            var toY = to.y;
            var toW = to.w;
            var toH = to.h;

            var xx1 = fromX + fromW / 2;
            var yy1 = fromY + fromH / 2;
            var xx2 = toX + toW / 2;
            var yy2 = toY + toH / 2;

            var x1;
            var y1;
            var x2;
            var y2;

            if (from != to) {
                var fromLine = new Line(xx1, yy1, xx2, yy2);
                var fromRect = new Rect(fromX, fromY, fromW, fromH);
                var fromCrossPoint = fromRect.getCrossPoint(fromLine);
                if (fromCrossPoint != null) {
                    x1 = fromCrossPoint.x;
                    y1 = fromCrossPoint.y;
                } else {
                    x1 = xx1;
                    y1 = yy1;
                }

                var toLine = new Line(xx2, yy2, xx1, yy1);
                var toRect = new Rect(toX, toY, toW, toH);
                var toCrossPoint = toRect.getCrossPoint(toLine);
                if (toCrossPoint != null) {
                    x2 = toCrossPoint.x;
                    y2 = toCrossPoint.y;
                } else {
                    x2 = xx2;
                    y2 = yy2;
                }
            } else {
                x1 = xx1;
                y1 = yy1;
                x2 = xx2;
                y2 = yy2;
            }

            // line
            line.setAttribute('x1', x1);
            line.setAttribute('y1', y1);
            line.setAttribute('x2', x2);
            line.setAttribute('y2', y2);

            // text
            text.setAttribute('x', (x1 + x2) / 2 - item.name.length * 5);
            text.setAttribute('y', (y1 + y2) / 2);

            // polyline
            var a = x1 - x2;
            var b = y1 - y2;
            var c = Math.sqrt(a * a + b * b);
            var sin = b / c;
            var cos = a / c;

            var sin1 = sin * Math.cos(0.5) + cos * Math.sin(0.5);
            var cos1 = cos * Math.cos(0.5) - sin * Math.sin(0.5);
            var sin2 = sin * Math.cos(0.5) - cos * Math.sin(0.5);
            var cos2 = cos * Math.cos(0.5) + sin * Math.sin(0.5);

            var dy1 = y2 + sin1 * 20;
            var dx1 = x2 + cos1 * 20;
            var dy2 = y2 + sin2 * 20;
            var dx2 = x2 + cos2 * 20;
            polyline.setAttribute('points', x2 + ' ' + y2 + ',' + dx1 + ' ' + dy1 + ',' + dx2 + ' ' + dy2);
        }

    }

    target = null;
}

function doDrag(evt, id) {
    if (target != null) {
        target.setAttribute('x', evt.clientX + dx);
        target.setAttribute('y', evt.clientY + dy);
    }
}


