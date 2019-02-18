import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Canvas extends JPanel implements ActionListener{
	Timer t = new Timer(5, this);
	//double x = 0, y = 0, velX = 2, velY = 2;
	private ArrayList<Dot> dotsList = new ArrayList<Dot>(); 
	private HashSet<Obstacle> obstacleList = new HashSet<Obstacle>(); 
	
	private ArrayList<Obstacle> tempVisitedSquaresList = new ArrayList<Obstacle>(); 

	
	public void AddDot(double x, double y, int acc) {
		Dot newDot = new Dot(x, y, acc);
		dotsList.add(newDot);
	}
	
	int acceleraton = 1;
	private int height;
	private int width; 
	
	private int goalX = 900;
	private int goalY = 500;
	
	private int startX = 100;
	private int startY = 500; 
	
	
	private int numXintervals;
	private int numYintervals; 
	private int xGap = 1; 
	private int yGap = 1;
	private Image background = new ImageIcon(this.getClass().getResource("treeHacksTest.png")).getImage();
	
	public Canvas(int height, int width) {
		this.height = height;
		this.width = width; 
		
		//Motion Adapter
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				//Here, do a check to make sure that you don't ever add
				//an obstacle to the same spot more than once
				if (me.getX() < width && me.getY() < height) {
					int xSpot = (me.getX()/xGap); 
					int ySpot = (me.getY()/yGap);
					Obstacle newObs = new Obstacle(xSpot, ySpot);

					obstacleList.add(newObs);
					System.out.println(obstacleList.size());
					System.out.println("X pos");
					System.out.println(newObs.getX() + "||" + newObs.getY());
				};
			}
		});
		
		
		addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	            //System.out.println(me.getX() + " || " +  me.getY()); 
	        	if (me.getX() < width && me.getY() < height) {
	        		Dot newDot = new Dot(me.getX(), me.getY(), acceleraton);
	            	acceleraton++;
	            	dotsList.add(newDot);
	            	//Determine Snap point:
	            	int xSpot = (me.getX()/xGap); 
	            	int ySpot = (me.getY()/yGap);
	            	Obstacle newObs = new Obstacle(xSpot, ySpot);
	            	obstacleList.add(newObs);
	        	}
	        	else {
	        		System.out.println("Run Distance Calculation");
	        	}
	          } 
	        }); 
	}
	
	
	
	
	private class DistanceValues{
		
	}
	
	
	
	//DFS - get Distance:
	private ArrayList<Obstacle> pathToGoal(int startX, int startY, int endX, int endY){
		HashSet<Obstacle> visited = new HashSet<Obstacle>();
		ArrayList<Obstacle> path = new ArrayList<Obstacle>(); 
		
		
	
		return path; 
	}
	
	private boolean depthFirstHelper(HashSet<Obstacle> visited, ArrayList<Obstacle> path, Obstacle start, Obstacle end) {
		if (start.equals(end)) {
			System.out.println("WE MADE IT");
			return true; 
		}
		//Mark the input node as visited, then add to path
		visited.add(start);
		path.add(start);
		//here, set COLOR TO GREEN
		
		//Now, explore all of the potential directions you can go; 
		
		return true;
	}
	
	
	
	//This function draws a grid on the canvas!! 
	private void setGrid(Graphics2D g2, int xDimension, int yDimension) {
		numXintervals = xDimension; 
		numYintervals = yDimension;
		xGap = width/xDimension;
		int xLoc = xGap; 
		for (int i = 0; i < xDimension; i++) {
		    g2.drawLine(xLoc, 0, xLoc, width);
		    xLoc += xGap;
		}
		yGap = height/yDimension;
		int yLoc = yGap; 
		for (int i = 0; i < yDimension; i++) {
		    g2.drawLine(0, yLoc, height, yLoc);
		    yLoc += yGap;
		}	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; 
		
		//Here, I should probably create a boolean to ensure that heavy computation 
		//doesn't occur beyond the starting setup
		//Choose background image
		g.drawImage(background, 0, 0, height, width, this);
		setGrid(g2, 80, 80);
		//Now, draw goal; 
		g2.setColor(Color.YELLOW);
		Ellipse2D goal = new Ellipse2D.Double(goalX, goalY, 40, 40);
		g2.fill(goal);
		g2.setColor(Color.BLUE);
		
		//Now, draw startPoint; 
		Ellipse2D start = new Ellipse2D.Double(startX, startY, 20, 20);
		g2.fill(start);
		g2.setColor(Color.BLACK);
		
		
		
		
		//Here, add the buttons
		Rectangle drawObst = new Rectangle(50, 1050, 200, 70);
		g2.fill(drawObst);  
		
		g2.drawString("Draw Obstacle", 50, 1050);
		
		
		

		//Set up the obstacle
		for (Obstacle myObstacle: obstacleList) {
			g2.setColor(Color.RED);
			Rectangle rect = new Rectangle(myObstacle.getX()*xGap, myObstacle.getY()*yGap, yGap, xGap);
			g2.fill(rect);
		}
		 
		//Draw all of the dots; and set the first one to green (later, change to "Best dot")
		for (int i = 0; i < dotsList.size(); i++) {
			if (i == 0) {
				g2.setColor(Color.GREEN);
			}
			else {
				g2.setColor(Color.BLACK);
			}
			Dot myDot = dotsList.get(i);
			Ellipse2D dot = new Ellipse2D.Double(myDot.getX(), myDot.getY(), 20, 20);
			g2.fill(dot);
		}
		t.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < dotsList.size(); i++) {
			double curX = dotsList.get(i).getX();
			int acc = dotsList.get(i).getAcc();
			dotsList.get(i).setX(curX + acc);
		}
		repaint();
	}
	
}
