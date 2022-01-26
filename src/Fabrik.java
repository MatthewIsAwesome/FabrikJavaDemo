import java.awt.geom.Point2D;

public class Fabrik {
	private Point2D.Double source;
	private Point2D.Double target;
	private Point2D.Double[] points;
	
	private int lineLength;
	
	public Fabrik(int pointCount, int lineLength) {
		this.lineLength = lineLength;
		
		points = new Point2D.Double[pointCount];
		
		for (int i = 0; i < pointCount; i++) {
			points[i] = new Point2D.Double(lineLength*i, 10+10*(i % 2));
		}
		
		this.source = new Point2D.Double(0, 0);
		this.target = new Point2D.Double(lineLength*pointCount, 0);
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
		points[0] = (Point2D.Double) source.clone();
		
		for (int i = 1; i < points.length; i++) {
			points[i] = normalisePoint(points[i-1], points[i], lineLength);
		}
	}
	
	private void backwardPass() {
		points[points.length-1] = (Point2D.Double) target.clone();
		
		for (int i = points.length-2; i >= 0; i--) {
			points[i] = normalisePoint(points[i+1], points[i], lineLength);
		}
	}
	
	public boolean isOverExtended() {
		return Math.pow(source.x-target.x, 2) + Math.pow(source.y-target.y, 2) > Math.pow(lineLength*(points.length-1), 2);
	}
	
	public Point2D.Double normalisePoint(Point2D.Double staticPoint, Point2D.Double pointToMove, int length) {
		
		double xDif = pointToMove.x-staticPoint.x;
		double yDif = pointToMove.y-staticPoint.y;
		
		double currentLength = Math.sqrt(xDif*xDif + yDif*yDif);
		
		xDif = xDif/currentLength;
		xDif *= length;
		
		yDif = yDif/currentLength;
		yDif *= length;
		
		return new Point2D.Double(staticPoint.x+xDif, staticPoint.y+yDif);
	}
	
	// Spaghetters
	
	public Point2D.Double getSource() {
		return source;
	}

	public void setSource(Point2D.Double source) {
		this.source = source;
	}

	public Point2D.Double getTarget() {
		return target;
	}

	public void setTarget(Point2D.Double target) {
		this.target = target;
	}

	public Point2D.Double[] getPoints() {
		return points;
	}

	public void setPoiubt(Point2D.Double[] points) {
		this.points = points;
	}
	
}
