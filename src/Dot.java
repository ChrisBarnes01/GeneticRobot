import java.awt.geom.Point2D;
import java.util.HashSet;

public class Dot {
	private int width; 
	private int height;
	private double[] pos;  //originally PVectors
	private int[] goal;
	private double[] vel; 
	private double[] acc; 
	public Brain brain; 
	private double fitness = 0; 
	private boolean reachedGoal = false; 
	private boolean dead = false; 
	private boolean isBest = false; 
	private HashSet<Obstacle> obstacleList; 

	
	public double getX() {
		return pos[0];
	}
	
	public double getY() {
		return pos[1];
	}
	public void mutateBrain() {
		brain.mutate();
	}
	
	
	//*NEW STUFF ______________________________________________________________
	
	  
	  Dot(int canvasWidth, int canvasHeight, HashSet<Obstacle> obstacleList){
	    brain = new Brain(400); 
	    pos = new double[2];
	    //pos[0] = width/2;
	    //pos[1] = height-10;
	    
	    vel = new double[2];
	    acc = new double[2];
	    this.width = canvasWidth;
	    this.height = canvasHeight; 
	    goal = new int[2];
	    pos[0] = width/2;
	    pos[1] = height-10;
	    goal[0] = 900;
	    goal[1] = 500;
		obstacleList = obstacleList; 

	  }
	  
	  
	  //________________________________________________
	  
	  public void show(){
	    if (isBest){
	      //* fill(0, 255, 0);
	      //* ellipse(pos.x, pos.y, 8, 8); 
	    } else{
	      //* fill(0);
	      //* ellipse(pos.x, pos.y, 4, 4); 
	    }
	  }
	  
	  public void setBest(boolean best) {
		  isBest = best;
	  }
	  
	  public boolean isBest() {
		  return isBest;
	  }
	  
	  //______________________________________
	  private void move() {
	   if (brain.getDirections().length > brain.getStep()){
	     acc = brain.getDirections()[brain.getStep()]; 
	     brain.incrementStep(); 
	   }else{
	     dead = true; 
	   }

	   vel[0] += acc[0];
	   vel[1] += acc[1];
	   if (vel[0] > 5) vel[0] = 5;
	   if (vel[1] > 5) vel[1] = 5;
	   pos[0] += vel[0];
	   pos[1] += vel[1];
	  }
	  //---------------------------------------------------------
	  
	  
	  public void update(){
	    if (!dead && !reachedGoal){
	      move();
	      if (pos[0]<2 || pos[1]<2 || pos[0] > width-2 || pos[1] >height -2) {
	        dead = true; 
	      }
	      else if ((pos[0] > goal[0] - 20 && pos[0] < goal[0] + 20) && (pos[1] > goal[1] - 20 && pos[1] < goal[1] + 20)){
	        //if reached goal; 
	        reachedGoal = true; 
	      }
	      else {
	    	  if (obstacleList != null) {
	    		  //System.out.println("obstacle size " + obstacleList.size());
	    		  for (Obstacle obs: obstacleList) {
		    		  //int x = 2;
	    			  System.out.println("CALCULATING OBSTACLES!!");
		    		  //if ((pos[0] < obs.getX() + 38 && pos[0] >= obs.getX()) && (pos[1] > obs.getY() && pos[1] < obs.getY() + 38)) {
	    			  System.out.println("OBST X: " + obs.getX() + " || OBS Y: " + obs.getY());
	    			  if (pos[0] == obs.getX()) {
		    			  dead = true;
		    		  }
		    	  }
	    	  }
	    	  //for (Obstacle obs: obstacleList) {
	    		  //int x = 2;
	    		  //if ((pos[0] < obs.getX() + 38 && pos[0] >= obs.getX()) && (pos[1] > obs.getY() && pos[1] < obs.getY() + 38)) {
	    			  //dead = true;
	    		  //}
	    	  //}
	      }
	      /*else if (pos[0] < 700 && pos[1] < 310 && pos[0] > 100 && pos[1] > 300){
	        dead = true; 
	      }*/
	      
	    }
	  }
	  
	  //--------------------------------------------------------------------
	  
	  public void calculateFitness(){
	    if (reachedGoal){
	      fitness = 1.0/16.0 + 1000.0/(double)(brain.getStep() * brain.getStep()); 
	    }else{
	    	//fitness = 1.0;
	    	//double distanceToGoal = Math.sqrt((goal[1] - pos[1]) * (goal[1] - pos[1]) + (goal[0] - pos[0]) * (goal[0] - pos[0]));
	    	double distanceToGoal = Point2D.distance(goal[0], goal[1], pos[0], pos[1]);
	    	//double distanceToGoal = Math.sqrt(((goal[1] - pos[1]) * (goal[1] - pos[1])));
	    	//fitness = distanceToGoal; 
	    	fitness = 1.0/(distanceToGoal); 
	    	//fitness = 1.0/(Math.random());
	    }
	  }
	  
	  public Dot gimmeBaby(){
	    Dot baby = new Dot(width, height, obstacleList); 
	    baby.brain = brain.clone(); 
	    return baby; 
	  }
	  
	  public void updateObstacleList(HashSet<Obstacle> list) {
		  obstacleList = list;
	  }
	  
	  public void setDead(boolean bool) {
	    dead = bool;
	  }

	  public boolean isDead() {
	    return dead;
	  }

	  public boolean hasReachedGoal() {
	    return reachedGoal;
	  }

	  public double getFitness() {
	    return fitness;
	  }

	
	
	
	
	
	
}
