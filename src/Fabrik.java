import java.awt.Point;

public class Fabrik {
	private Point source;
	private Point target;
	private Point[] points;
	
	private int lineLength;
	
	public Fabrik(int pointCount, int lineLength) {
		this.lineLength = lineLength;
		
		points = new Point[pointCount];
		
		for (int i = 0; i < pointCount; i++) {
			points[i] = new Point(lineLength*i, 10+10*(i % 2));
		}
		
		this.source = new Point(0, 0);
		this.target = new Point(lineLength*pointCount, 0);
	}
	
	public void update() {
		int iters = 0;
		
		do {
			backwardPass();
			forwardPass();
			iters ++;
		} while (!isOverExtended() && (points[0].distance(source) > 10 || points[0].distance(target) > 10) && iters < 20);
		
		//target.y++;
	}
	
	private void forwardPass() {
		points[0] = (Point) source.clone();
		
		for (int i = 1; i < points.length; i++) {
			points[i] = normalisePoint(points[i-1], points[i], lineLength);
		}
	}
	
	private void backwardPass() {
		points[points.length-1] = (Point) target.clone();
		
		for (int i = points.length-2; i >= 0; i--) {
			points[i] = normalisePoint(points[i+1], points[i], lineLength);
		}
	}
	
	public boolean isOverExtended() {
		return Math.pow(source.x-target.x, 2) + Math.pow(source.y-target.y, 2) > Math.pow(lineLength*(points.length-1), 2);
	}
	
	public Point normalisePoint(Point staticPoint, Point pointToMove, int length) {
		//TODO
		
		double xDif = pointToMove.x-staticPoint.x;
		double yDif = pointToMove.y-staticPoint.y;
		
		double currentLength = Math.sqrt(xDif*xDif + yDif*yDif);
		
		xDif = xDif/currentLength;
		xDif *= length;
		
		yDif = yDif/currentLength;
		yDif *= length;
		
		return new Point((int) Math.round(staticPoint.x+xDif), (int) Math.round(staticPoint.y+yDif));
	}
	
	// Spaghetters
	
	public Point getSource() {
		return source;
	}

	public void setSource(Point source) {
		this.source = source;
	}

	public Point getTarget() {
		return target;
	}

	public void setTarget(Point target) {
		this.target = target;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}
	
}
