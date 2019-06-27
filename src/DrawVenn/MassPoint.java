package DrawVenn;

public class MassPoint extends DoublePoint{
	public double m;
	public double vx;
	public double vy;
	public double fx;
	public double fy;
	public boolean isGroup;
	public String groupName;
	public int groupNumber;
	
	MassPoint (double m, double x, double y){
		super(x, y);
		this.m = m;
		vx = vy = 0;
		fx = fy = 0;
		isGroup = false;
	}
	
	public void move(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	public double getVNorm() {
		return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
	}
	
	public double getFNorm() {
		return Math.sqrt(Math.pow(fx, 2) + Math.pow(fy, 2));
	}
}
