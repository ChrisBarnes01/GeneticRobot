import javax.swing.JButton;
import javax.swing.JFrame;

public class testRunner extends JFrame{
	
	public static void main(String args[]) {
		
		
		
		JFrame f = new JFrame(); 
		Population test = new Population(100, 1500, 1500);
		Canvas c = new Canvas(1500, 1500, test);	
		
		

		//c.AddDot();
		//c.AddDot();
		//c.AddDot();
		f.add(c);
		f.setSize(1500, 1700);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Moving Ball!");

	}
	
	
}
