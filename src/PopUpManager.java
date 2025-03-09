import java.awt.*;
import javax.swing.*;

public class PopUpManager
{		
		private boolean Active = false;
		private JFrame CurrentFrame;
		
		public boolean GetActive()
		{
			return Active;
		}
		
		public void KillFrame()
		{
			CurrentFrame.setVisible(false);
			Active = false;
			CurrentFrame = null;
		}
	
	
        public void toggleNewFrame() 
        {
        	Active = true;
        	JFrame newFrame = new JFrame("New Frame");
        	newFrame.setSize(300, 300);
        	newFrame.setAutoRequestFocus(false);
        	newFrame.setFocusableWindowState(false);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        	newFrame.setLocation(dim.width/2,dim.height/2);
        	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	CurrentFrame = newFrame;
        	CurrentFrame.setVisible(true);
        }
        
        public void Input(String input)
        {
            switch(input)
            {
            	// Movement Function
            	case "w" -> {CurrentFrame.setLocation(CurrentFrame.getX(), CurrentFrame.getY() - 100);}
            	case "a" -> {CurrentFrame.setLocation(CurrentFrame.getX()-100, CurrentFrame.getY());}
            	case "s" -> {CurrentFrame.setLocation(CurrentFrame.getX(), CurrentFrame.getY() + 100);}
            	case "d" -> {CurrentFrame.setLocation(CurrentFrame.getX()+100, CurrentFrame.getY());}
            	case "e" -> {KillFrame();}

            }
        }
}