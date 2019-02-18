import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

public class drawDot extends JPanel implements ActionListener {
	Timer t = new Timer(5, this);
	double x = 0, y = 0, velX = 2, velY = 2;
	
	drawDot(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; 
		Ellipse2D circle = new Ellipse2D.Double(x, y, 40, 40);
		Ellipse2D circle2 = new Ellipse2D.Double(x + y, y + x, 20, 20);
		g2.fill(circle);
		g2.fill(circle2);
		t.start();
	}
	public void actionPerformed(ActionEvent e) {
		x += velX;
		y += velY;
		repaint();
	}
	
	
}
