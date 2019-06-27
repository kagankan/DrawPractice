package DrawVenn;

public class DoublePoint {
	public double x;
	public double y;
	
	DoublePoint(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	double getDistanceFromOrigin() {
		return Calc.getDistance(0, 0, x, y);
	}
	
	double getVectorX() {
		return Calc.getVectorX(0, 0, x, y);
	}

	double getVectorY() {
		return Calc.getVectorY(0, 0, x, y);
	}
}
