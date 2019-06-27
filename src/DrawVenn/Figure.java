package DrawVenn;

import java.awt.Graphics;
import java.awt.FontMetrics;


abstract class Figure {
	int x, y; // 重心の(x,y)座標
	int getX(){
		return x;
	}
	int getY(){
		return y;
	}
	void setX(int x){
		this.x = x;
	}
	void setY(int y){
		this.y = y;
	}
	abstract void move(int dx, int dy); // これらは派⽣クラスで実装される
	abstract void draw(Graphics g); // 図形毎に処理が違う!
}

class Circle extends Figure {
	int r; // 半径
	Circle(int x, int y, int r) {
		this.x = x; // 重心　親のFigureクラスのフィールド
		this.y = y;
		this.r = r; // 半径　Circleクラスのフィールド
	}
	void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}
	void draw(Graphics g) {
		g.drawOval(x-r, y-r, 2*r, 2*r);
	}
}

abstract class Polygon extends Figure { // Triangle, Rectangleに共通の
	int[] xs, ys; // move, drawを実装
	int n;

	void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		for (int i=0; i<xs.length; i++) {
			this.xs[i] += dx;
			this.ys[i] += dy;
		}
	}
	public int getPointCount() {
		return n;
	}
	public DoublePoint getPoint(int k) {
		if(this.n <= k) {
			return null;
		}
		return new DoublePoint(xs[k], ys[k]);
	}
	void draw(Graphics g) {
		g.drawPolygon(xs, ys, xs.length);
	}
}

class Textbox extends Rectangle {
	String text;
	Textbox(String text, int x, int y) {
		super(x, y, 0, 0);
		this.text = text;
	}
	
	@Override
	void draw(Graphics g) {
		int margin = 2;
		FontMetrics fontmetrics = g.getFontMetrics();
		double height = fontmetrics.getHeight();
		double width = fontmetrics.stringWidth(text);
		//double ascent = fontmetrics.getAscent();
		//double descent = fontmetrics.getDescent();
		super.setWidth((int)width + margin * 2);
		super.setHeight((int)height);
 		super.draw(g);
		g.drawString(text, xs[0] + margin, ys[0] + (int)height - margin);
	}
}
class Line extends Polygon {
	Line(int x1, int y1, int x2, int y2){
		this.n = 2;
		this.xs = new int[2];
		this.ys = new int[2];
		this.xs[0] = x1;
		this.ys[0] = y1;
		this.xs[1] = x2;
		this.ys[1] = y2;
		this.x = (xs[0]+xs[1])/2;
		this.y = (ys[0]+ys[1])/2;
		
	}
}
class Arrow extends Polygon {
	Arrow(int x, int y, int valueX, int valueY){
		this.n = 5;
		this.xs = new int[5];
		this.ys = new int[5];
		this.xs[0] = x + valueX - (int)(10.0 * 0.707 * (valueX - valueY) / Calc.getDistance(0, 0, valueX, valueY));
		this.ys[0] = y + valueY - (int)(10.0 * 0.707 * (valueX + valueY) / Calc.getDistance(0, 0, valueX, valueY));
		this.xs[1] = this.xs[3] = x + valueX;
		this.ys[1] = this.ys[3] = y + valueY;
		this.xs[2] = x;
		this.ys[2] = y;
		this.xs[4] = x + valueX - (int)(10.0 * 0.707 * (valueX + valueY) / Calc.getDistance(0, 0, valueX, valueY));
		this.ys[4] = y + valueY - (int)(10.0 * 0.707 * (-valueX + valueY) / Calc.getDistance(0, 0, valueX, valueY));
		this.x = x;
		this.y = y;
	}
}
class Triangle extends Polygon {
	Triangle(int x1, int y1, int x2, int y2, int x3, int y3){
		this.n = 3;
		this.xs = new int[3];
		this.ys = new int[3];
		this.xs[0] = x1;
		this.ys[0] = y1;
		this.xs[1] = x2;
		this.ys[1] = y2;
		this.xs[2] = x3;
		this.ys[2] = y3;
		this.x = (xs[0]+xs[1]+xs[2])/3;
		this.y = (ys[0]+ys[1]+ys[2])/3;
		
	}
}
class Rectangle extends Polygon {
	int w, h;
	Rectangle(int x, int y, int w, int h){
		this.n = 4;
		this.xs = new int[4];
		this.ys = new int[4];
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.xs[0] = x-w/2;
		this.ys[0] = y-h/2;
		this.xs[1] = x+w/2;
		this.ys[1] = y-h/2;
		this.xs[2] = x+w/2;
		this.ys[2] = y+h/2;
		this.xs[3] = x-w/2;
		this.ys[3] = y+h/2;
	}
	public void setWidth(int w) {
		this.w = w;
		this.xs[0] = x-w/2;
		this.xs[1] = x+w/2;
		this.xs[2] = x+w/2;
		this.xs[3] = x-w/2;
	}
	public void setHeight(int h) {
		this.h = h;
		this.ys[0] = y-h/2;
		this.ys[1] = y-h/2;
		this.ys[2] = y+h/2;
		this.ys[3] = y+h/2;
	}
}

class Band extends Polygon {
	Band(int n, int x, int y, int r){
		this.xs = new int[n];
		this.ys = new int[n];
		this.n = n;
		this.x = x;
		this.y = y;
		for(int i = 0; i < this.n; i++) {
			this.xs[i] = x + (int)(r * Math.cos(2 * Math.PI / this.n * i));
			this.ys[i] = y + (int)(r * Math.sin(2 * Math.PI / this.n * i));
		}
	}
	void setPoint(int n, DoublePoint p) {
		this.xs[n] = (int)p.x;
		this.ys[n] = (int)p.y;
		setCenter();
	}
	void movePoint(int n, int dx, int dy) {
		this.xs[n] += dx;
		this.ys[n] += dy;
		setCenter();
	}
	void setCenter() {
		int sumx = 0, sumy = 0;
		for(int i = 0; i < n; i++) {
			sumx += xs[i];
			sumy += ys[i];
		}
		x = sumx / n;
		y = sumy / n;
	}
	void move(int dx, int dy) {
		super.move(dx, dy);
		setCenter();
	}
}
