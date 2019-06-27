package DrawVenn;

public class Calc{
	
	static boolean isInsidePolygon (DoublePoint point, Polygon pol) {
		boolean counterClockwise;
		int windingNumber = 0;
		for(int i = pol.n - 1, j = 0; i >= 0; j = i--) {
			double yi = pol.getPoint(i).y - point.y;
			double xi = pol.getPoint(i).x - point.x;
			double yj = pol.getPoint(j).y - point.y;
			double xj = pol.getPoint(j).x - point.x;
			
			if((yi>0) != (yj>0) && (counterClockwise = (yj > yi)) == (xi * yj > xj * yi)) {
				windingNumber += counterClockwise ? 1 : -1;
			}
		}
		return windingNumber != 0;
	}

	static int clamp (int x, int min, int max) {
		if(x > max) {
			return max;
		}else if (x < min) {
			return min;
		}else {
			return x;
		}
	}
	static double clamp (double x, double min, double max) {
		if(x > max) {
			return max;
		}else if (x < min) {
			return min;
		}else {
			return x;
		}
	}

	static double getPerpendicularFootRate (DoublePoint p, DoublePoint p1, DoublePoint p2) {
		//線分ベクトル
		double ax = p2.x - p1.x;
		double ay = p2.y - p1.y;
		double aLength = Calc.getDistance(0.0, 0.0, ax, ay);
		//点ベクトル
		double bx = p.x - p1.x;
		double by = p.y - p1.y;
		//内積
		double dot = ax * bx + ay * by;

		return dot / aLength / aLength;
		
	}
	
	static DoublePoint getPerpendicular (DoublePoint p, DoublePoint p1, DoublePoint p2) {
		//System.out.println("get:"+p.x+","+p.y+" / "+p1.x+","+p1.y+" / "+p2.x+","+p2.y);
		//線分ベクトル
		double ax = p2.x - p1.x;
		double ay = p2.y - p1.y;
		double aLength = Calc.getDistance(0.0, 0.0, ax, ay);
		//点ベクトル
		double bx = p.x - p1.x;
		double by = p.y - p1.y;
		//内積
		double dot = ax * bx + ay * by;
		//線分上の最寄りの点
		double cx = ax * dot / aLength / aLength;
		double cy = ay * dot / aLength / aLength;
		
		return new DoublePoint(cx - bx, cy - by);
	}

	static double getDistance (double x1, double y1, double x2, double y2) {
		double d = Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1),2));
		return d;
	}
	
	static double getDistance (DoublePoint p1, DoublePoint p2) {
		double d = Math.sqrt(Math.pow((p2.x-p1.x), 2)+Math.pow((p2.y-p1.y),2));
		return d;
	}	
	
	
	static double getVectorX (DoublePoint p1, DoublePoint p2) {
		return getVectorX(p1.x, p1.y, p2.x, p2.y);
	}
	static double getVectorX (double x1, double y1, double x2, double y2) {
		double d = getDistance(x1, y1, x2, y2);
		if(d == 0) {
			return 0;
		}else{
			return (x2-x1)/d;
		}
	}
	static double getVectorY (DoublePoint p1, DoublePoint p2) {
		return getVectorY(p1.x, p1.y, p2.x, p2.y);
	}
	static double getVectorY (double x1, double y1, double x2, double y2) {
		double d = getDistance(x1, y1, x2, y2);
		if(d == 0) {
			return 0;
		}else{
			return (y2-y1)/d;
		}
	}
}