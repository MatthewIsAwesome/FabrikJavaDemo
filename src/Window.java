import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Window extends JPanel implements ActionListener, MouseListener {
	private Fabrik fab;
	private Timer t;
	
	private JFrame frame;
	
	private boolean mouseDown;
	private int mouseButton;
	
	public static void main(String[] args) {
		new Window();
	}
	
	public Window() {
		frame = new JFrame();
		fab = new Fabrik(80, 8);
		
		t = new Timer(14, this);
		
		// frame config
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600, 600));
		frame.setTitle("Fabrik Demonstration");
		this.setFocusable(true);
		this.addMouseListener(this);
		
		// run
		frame.setVisible(true);
		t.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// Antialiasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Source and target
		g2.setColor(new Color(0x3A96BA));
		drawPoint(g2, fab.getSource());
		g2.setColor(new Color(0x3AB54C));
		drawPoint(g2, fab.getTarget());
		
		// Draw lines
		Color lineCol = fab.isOverExtended() ? Color.RED : Color.BLACK;
		
		Point[] points = fab.getPoints();
		Point a, b = new Point(0, 0);
		for (int i = 1; i < points.length; i++) {
			a = points[i-1];
			b = points[i];
			
			g2.setColor(lineCol);
			//drawPoint(g2, a, lineCol);
			g2.drawLine(a.x, a.y, b.x, b.y);
		}
		drawPoint(g2, b);
	}
	
	private void drawPoint(Graphics2D g2, Point p) {
		g2.fillOval(p.x-5, p.y-5, 10, 10);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == t)  {
			fab.update();
			this.repaint();
			
			if (mouseDown) {
				//Point loc = MouseInfo.getPointerInfo().getLocation()
				Point loc = getMousePosition();
				
				if (loc == null) return;
					
				if (mouseButton == MouseEvent.BUTTON1) {
					fab.setTarget(loc);
					
				} else if (mouseButton == MouseEvent.BUTTON3) {
					fab.setSource(loc);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
