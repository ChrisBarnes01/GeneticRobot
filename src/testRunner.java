import javax.swing.JButton;
import javax.swing.JFrame;

public class testRunner extends JFrame{
	
	public static void main(String args[]) {
		
		JFrame f = new JFrame(); 
		Canvas c = new Canvas(1500, 1500);
		c.AddDot(0, 0, 3);
		c.AddDot(40,40, 2);
		c.AddDot(80, 100, 1);
		f.add(c);
		
		f.setSize(1500, 1700);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Moving Ball!");
		
		
		//Add button COntrols 
		//JButton b = new JButton("Submit");
		//b.setBounds(0, 0, 100, 30);
		//add button to the frame.
		//f.add(b);

	}
	
	/*private testRunner() {
		super("Tutorial - Test");
		drawDot dot = new drawDot(); 
		
		setSize(1000, 1000);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}*/
	
}
