import java.util.Random;

public class Brain{

  private int[][] directions; 
  private int step = 0; 
  
  public Brain(int size){
    directions = new int[size][2];
    randomize(); 
  }
  
  private void randomize(){
    Random rand = new Random();
    for (int i = 0; i < directions.length; i++) {
      double x = rand.nextDouble(-1, 1);
      double y = rand.nextDouble(-1, 1);
      directions[i][0] = x;
      directions[i][1] = y;
    }
  }
  
  public Brain clone(){
    Brain clone = new Brain(directions.length); 
    for (int i = 0; i < directions.length; i++){
      clone.directions[i] = directions[i]; 
    }
    return clone; 
  }

  public void mutate(){
    Random rand = new Random();
    double mutationRate = 0.01; 
    for (int i = 0; i < directions.length; i++){
      double random = rand.nextDouble(0, 1);
      if (random < mutationRate){
        double x = rand.nextDouble(-1, 1);
        double y = rand.nextDouble(-1, 1);
        directions[i][0] = x;
        directions[i][1] = y;
      }
    }
  }
  

  //getter method for directions
  public int[][] getDirections() {
    return directions;
  }
  
  //getter method for step
  public int getStep() {
    return step;
  }

  //setter for step
  public void incrementStep() {
    step++;
  }
  
}
