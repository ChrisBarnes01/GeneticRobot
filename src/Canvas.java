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
	//private ArrayList<Dot> dotsList = new ArrayList<Dot>(); 
	//private Dot[] dotsList;
	private HashSet<Obstacle> obstacleList = new HashSet<Obstacle>(); 
	
	private ArrayList<Obstacle> tempVisitedSquaresList = new ArrayList<Obstacle>(); 

	int acceleraton = 1;
	private int height;
	private int width; 
	
	private int goalX = 900;
	private int goalY = 500;
	
	private int startX = 100;
	private int startY = 500; 
	private Population population;
	
	private int numXintervals;
	private int numYintervals; 
	private int xGap = 1; 
	private int yGap = 1;
	private Image background = new ImageIcon(this.getClass().getResource("treeHacksTest.png")).getImage();
	
	private boolean paused = false;
	
	
	public Canvas(int height, int width, Population test) {
		this.height = height;
		this.width = width; 
		this.population = test; 
		
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
					population.updateObstacleList(obstacleList);
					//System.out.println(obstacleList.size());
					//System.out.println("X pos");
					//System.out.println(newObs.getX() + "||" + newObs.getY());
				};
			}
		});
				
		addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	            //System.out.println(me.getX() + " || " +  me.getY()); 
	        	if (me.getX() < width && me.getY() < height) {
	        		/*Dot newDot = new Dot(height, width);
	            	acceleraton++;
	            	dotsList.add(newDot);*/
	            	//Determine Snap point:
	            	int xSpot = (me.getX()/xGap); 
	            	int ySpot = (me.getY()/yGap);
	            	Obstacle newObs = new Obstacle(xSpot, ySpot);
	            	obstacleList.add(newObs);
					population.updateObstacleList(obstacleList);

	        	}
	        	else {
	        		paused = !paused; 
	        		//System.out.println("Run Distance Calculation");
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
			//System.out.println("WE MADE IT");
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
		setGrid(g2, 40, 40);
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
		//Rectangle drawObst = new Rectangle(50, 1050, 200, 70);
		//g2.fill(drawObst);  
		
		//g2.drawString("Draw Obstacle", 50, 1050);
		
		
		

		//Set up the obstacle
		for (Obstacle myObstacle: obstacleList) {
			g2.setColor(Color.RED);
			Rectangle rect = new Rectangle(myObstacle.getX()*xGap, myObstacle.getY()*yGap, yGap, xGap);
			g2.fill(rect);
		}
		 
		//Draw all of the dots; and set the first one to green (later, change to "Best dot")
		if (population.dots != null) {
			for (int i = 0; i < population.dots.length; i++) {
				if (population.dots[i].isBest()) {
					//System.out.println("THE BEST!!");
					g2.setColor(Color.CYAN);
					Dot myDot = population.dots[i];
					g2.drawString(String.valueOf(i), 200, 200);
					Ellipse2D dot = new Ellipse2D.Double(myDot.getX(), myDot.getY(), 100, 150);
					g2.fill(dot);
				}
				else {				
					Dot myDot = population.dots[i];
					int fillSize = 20;

					if (i < 20) {
						g2.setColor(Color.BLACK);
						fillSize = 60;
					}
					else if (i < 40) {
						g2.setColor(Color.BLUE);
						fillSize = 50;
					}
					else if (i < 60) {
						g2.setColor(Color.GREEN);
						fillSize = 40;
					}
					else if (i < 80) {
						g2.setColor(Color.YELLOW);
						fillSize = 30;

					}
					else if (i < 101) {
						g2.setColor(Color.ORANGE);
						fillSize = 20;
					}
				
					//Dot myDot = dotsList[i];
					Ellipse2D dot = new Ellipse2D.Double(myDot.getX(), myDot.getY(), fillSize, fillSize);
					g2.fill(dot);
					myDot.calculateFitness();
					g2.drawString(String.valueOf(myDot.getFitness()), Math.round(myDot.getX()), Math.round(myDot.getY()));

				}
			}
		}
		t.start();
	}
	
	/*public void actionPerformed(ActionEvent e) {
		if (!paused) {
			if (population.allDotsDead()) {
				population.calculateFitness();

				for (int i = 0; i < dotsList.length; i++) {
					System.out.println(String.valueOf(i) + " || Fitness: " + dotsList[i].getFitness());
				}
				
				population.naturalSelection();
				population.mutateDemBabies();
				dotsList = population.dots;
			}
			else {
				dotsList = population.dots;
				population.update();
			}
		}			
		repaint();

	}*/
	private int stepCount = 0;
	
	public void actionPerformed(ActionEvent e) {
		if (!paused) {
			if (population.allDotsDead()) {
				population.calculateFitness();

				for (int i = 0; i < population.dots.length; i++) {
					System.out.println(String.valueOf(i) + " || Fitness: " + population.dots[i].getFitness());
				}
				
				paused = true;
				population.naturalSelection();
				//population.mutateDemBabies();
				//dotsList = population.dots;
				System.out.println("StepCount: " + stepCount);
				stepCount = 0; 
			}
			else {
				stepCount ++;
				//dotsList = population.dots;
				population.update();
			}		
		}
		if (!paused) {
			repaint();
		}

	}
	
	
	
	
	
}
