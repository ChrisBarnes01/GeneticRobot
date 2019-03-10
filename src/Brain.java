import java.util.Random;

public class Brain{

  private double[][] directions; 
  private int step = 0; 
  
  
  protected static Random random = new Random();
  public static double randomInRange(double min, double max) {
    double range = max - min;
    double scaled = random.nextDouble() * range;
    double shifted = scaled + min;
    return shifted; // == (rand.nextDouble() * (max-min)) + min;
  }
  
  public Brain(int size){
    directions = new double[size][2];
    randomize(); 
  }
  
  private void randomize(){
    for (int i = 0; i < directions.length; i++) {
      double x = randomInRange(-1.0, 1.0);
      double y = randomInRange(-1, 1);
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
    double mutationRate = 0.01; 
    for (int i = 0; i < directions.length; i++){
      double random = randomInRange(0, 1);
      

      if (random < mutationRate){
    	  System.out.println("RANDOM RATE: " + random);
          System.out.println("Mutation Rate: " + mutationRate);
    	  //double angle = Math.toRadians(Math.random() * 360);
    	//System.out.println("I AM BEING MUTATED!!!!");
        //directions[i][0] = Math.cos(angle);
    	directions[i][0] = randomInRange(-1.0, 1.0);
    	directions[i][1] = randomInRange(-1.0, 1.0);
        //directions[i][1] = Math.sin(angle);
      }
      else {
    	  //System.out.println("NO MUTATION!!");
      }
    }
  }
  

  //getter method for directions
  public double[][] getDirections() {
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