package DrawVenn;

public class DrawDemo {
	public static void main(String[] args) {
		final int width = 800;
		final int height = 600;
		//new DrawGraph(new TrainGraph(), width, height);
		new DrawVenn(new NumberGraph(), width, height);
		DoublePoint p = new DoublePoint(100.1, 100.1);
		DoublePoint p1 = new DoublePoint(105, 90);
		DoublePoint p2 = new DoublePoint(105, 110);
		DoublePoint c = Calc.getPerpendicular(p, p1, p2);
		System.out.println(c.x+", "+c.y);
	}
}
