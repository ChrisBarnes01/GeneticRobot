public class Dot{
   
   double[] pos;  //originally PVectors
   double[] vel; 
   double[] acc; 
   Brain brain; 
   double fitness = 0; 
   boolean reachedGoal = false; 
   boolean dead = false; 
   boolean isBest = false; 
  
  public Dot(int width, int height){
    brain = new Brain(400); 
    pos = new double[2];
    pos[0] = width/2;
    pos[1] = height-10;
    vel = new double[2];
    acc = new double[2];
  }
  
  
  //________________________________________________
  
  public void show(){
    if (isBest){
      * fill(0, 255, 0);
      * ellipse(pos.x, pos.y, 8, 8); 
    } else{
      * fill(0);
      * ellipse(pos.x, pos.y, 4, 4); 
    }
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
      else if (Math.sqrt((goal[1] - pos[1]) * (goal[1] - pos[1]) + (goal[0] - pos[0]) * (goal[0] - pos[0])) < 5){
        //if reached goal; 
        reachedGoal = true; 
      }
      else if (pos[0] < 700 && pos[1] < 310 && pos[0] > 100 && pos[1] > 300){
        dead = true; 
      }
      
    }
  }
  
  //--------------------------------------------------------------------
  
  public void calculateFitness(){
    if (reachedGoal){
      fitness = 1.0/16.0 + 1000.0/(double)(brain.getStep() * brain.getStep()); 
    }else{
      double distanceToGoal = Math.sqrt((goal[1] - pos[1]) * (goal[1] - pos[1]) + (goal[0] - pos[0]) * (goal[0] - pos[0]));
      fitness = 1.0/(distanceToGoal * distanceToGoal); 
    }
  }
  
  public Dot gimmeBaby(){
    Dot baby = new Dot(width, height); 
    baby.brain = brain.clone(); 
    return baby; 
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
