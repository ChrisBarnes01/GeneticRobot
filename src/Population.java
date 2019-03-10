import java.util.HashSet;
import java.util.Random;

public class Population{
  public Dot[] dots;
  double fitnessSum; 
  int gen = 1; 
  int bestDot; 
  private HashSet<Obstacle> obstacleList; 

  
  int minStep = 400; 
  
  protected static Random random = new Random();
  public static double randomInRange(double min, double max) {
    double range = max - min;
    double scaled = random.nextDouble() * range;
    double shifted = scaled + min;
    return shifted; // == (rand.nextDouble() * (max-min)) + min;
  }
  
  public Population(int size, int height, int width, HashSet<Obstacle> obstacleList){
    dots = new Dot[size];
    for (int i = 0; i < size; i++){
      dots[i] = new Dot(height, width, obstacleList); 
    }
  }
  
  public void show() {
   for (int i = 1; i < dots.length; i++){
     dots[i].show(); 
   }
   dots[0].show();
  }
  
  public void updateObstacleList(HashSet<Obstacle> list) {
	  obstacleList = list; 
	  for(int i = 0; i < dots.length; i++) {
		  dots[i].updateObstacleList(list);
	  }
  }

  
  
  //____________________________________________________
  
  public void update(){
   for (int i = 0; i < dots.length; i++){
     if(dots[i].brain.getStep() > minStep){
       dots[i].setDead(true); 
     } else{
       dots[i].update();
     }
   }
 }
   //-------------------------------------------------------------------
   public void calculateFitness(){
    for (int i = 0; i < dots.length; i++){
      dots[i].calculateFitness(); 
    }
   }
  
  //
  
  
  public boolean allDotsDead(){
    for (int i = 0; i < dots.length; i++){
      if(!dots[i].isDead() && !dots[i].hasReachedGoal()){
        return false; 
      }
    }
    return true;
  }
  //-----------------------------------------------
  public void naturalSelection(){
    Dot[] newDots = new Dot[dots.length];
    setBestDot(); 
    calculateFitnessSum(); 

    newDots[0] = dots[bestDot].gimmeBaby(); 
    newDots[0].setBest(true); 
    
    for(int i = 1; i < newDots.length; i ++){
      //select parent based on fitness; 
    	Dot parent = selectParent(); 
    	newDots[i] = parent.gimmeBaby(); 
    	newDots[i].setBest(false);
		newDots[i].mutateBrain();
    }
    dots = newDots; 
    gen++;
    
  }
  
  //_______________________________________________________
  
  private void calculateFitnessSum(){
    fitnessSum = 0; 
    for(int i = 0; i < dots.length; i++){
      fitnessSum += dots[i].getFitness();
    }
  }
  
  private Dot selectParent(){
    double rand = randomInRange(0.0000, fitnessSum);
    System.out.println("FitnessSum: " + fitnessSum);
    System.out.println("Random # Selection: " + rand);
    double runningSum = 0; 
    for (int i = 0; i < dots.length; i++){
    	//System.out.print(dots[i].getFitness());
      runningSum += dots[i].getFitness(); 
      if (runningSum > rand){
    	System.out.println("Selected Dot: " + i);
        return dots[i];
      }
    }
    //should never get here; 
    return null; 
    
  }
  
  //____________________________________________________________________
  
  public void mutateDemBabies(){
	System.out.println("FITNESS FOR 0: " + dots[0].isBest());
	
    //int j = 0; 
    for(int i = 1; i < dots.length; i++){
    	if (!dots[i].isBest()) {
    		//j += 1;
    		//System.out.println(j);
    		//dots[i].mutateBrain();
    	}
    }
  }
  
  //_____________________________________________________
  private void setBestDot(){
    double max = 0; 
    int maxIndex = 0; 
    for (int i = 0; i < dots.length; i++){
      if(dots[i].getFitness() > max){
        max = dots[i].getFitness(); 
        maxIndex = i; 
      }
    }
    
    bestDot = maxIndex;
    dots[maxIndex].setBest(true);
    
    if(dots[bestDot].hasReachedGoal()){
      minStep = dots[bestDot].brain.getStep();
    }
    
    
  }
  
  
  
  
  
  
}