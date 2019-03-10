import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;

public class testRunner extends JFrame{
	
	public static void main(String args[]) {
		
		
		HashSet<Obstacle> obstacleList = new HashSet<Obstacle>(); 
		JFrame f = new JFrame(); 		
		f.setResizable(true);
		//f.getHeight();
		Population test = new Population(100, 1500, 1500, obstacleList);
		Canvas c = new Canvas(1500, 1500, test);	
		
		
		
		
		

		//c.AddDot();
		//c.AddDot();
		//c.AddDot();
		f.add(c);
		f.setSize(1900, 1700);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Moving Ball!");

	}
	
	
}
