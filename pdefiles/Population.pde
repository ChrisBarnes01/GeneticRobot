import java.util.Random;

public class Population{
  Dot[] dots;
  double fitnessSum; 
  int gen = 1; 
  int bestDot; 
  
  int minStep = 400; 
  
  public Population(int size){
    dots = new Dot[size];
    for (int i = 0; i < size; i++){
      dots[i] = new Dot(); 
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
    newDots[0].setBest(true); 
    for(int i = 1; i < newDots.length; i ++){
       
      //select parent based on fitness; 
      Dot parent = selectParent(); 
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
    Random randGen = new Random();
    double rand = randGen.nextDouble(fitnessSum);
    double runningSum = 0; 
    for (int i = 0; i < dots.length; i++){
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
