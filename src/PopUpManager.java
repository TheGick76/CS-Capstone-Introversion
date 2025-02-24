import javax.swing.*;

public class PopUpManager
{
		 	
        public static void toggleNewFrame() 
        {
	
        	
        	JFrame newFrame = new JFrame("New Frame");
        	newFrame.setSize(300, 300);
        	newFrame.setAutoRequestFocus(false);
        	newFrame.setFocusableWindowState(false);
        	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	newFrame.setVisible(true);

        }
}