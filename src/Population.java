import java.util.Random;

public class Population{
  public Dot[] dots;
  double fitnessSum; 
  int gen = 1; 
  int bestDot; 
  
  int minStep = 400; 
  
  protected static Random random = new Random();
  public static double randomInRange(double min, double max) {
    double range = max - min;
    double scaled = random.nextDouble() * range;
    double shifted = scaled + min;
    return shifted; // == (rand.nextDouble() * (max-min)) + min;
  }
  
  public Population(int size, int height, int width){
    dots = new Dot[size];
    for (int i = 0; i < size; i++){
      dots[i] = new Dot(height, width); 
    }
  }
  
  public void show() {
   for (int i = 1; i < dots.length; i++){
     dots[i].show(); 
   }
   dots[0].show();
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
    System.out.println("Best Dot");
    System.out.println(dots[bestDot]);
    newDots[0].setBest(true); 
    for(int i = 1; i < newDots.length; i ++){
       
      //select parent based on fitness; 
      Dot parent = selectParent(); 
      System.out.println("Parent");
      System.out.println(parent);
      //get baby from them
      newDots[i] = parent.gimmeBaby(); 
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
    System.out.println("Random!!!!!");
    System.out.println(rand);
    double runningSum = 0; 
    System.out.println("running Sum");
    for (int i = 0; i < dots.length; i++){
    	System.out.print(dots[i].getFitness());
      runningSum += dots[i].getFitness(); 
      if (runningSum > rand){
        return dots[i];
      }
    }
    //should never get here; 
    return null; 
    
  }
  
  //____________________________________________________________________
  
  public void mutateDemBabies(){
    for(int i = 1; i < dots.length; i++){
      dots[i].brain.mutate(); 
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
    
    if(dots[bestDot].hasReachedGoal()){
      minStep = dots[bestDot].brain.getStep();
    }
    
    
  }
  
  
  
  
  
  
}