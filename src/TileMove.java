import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class TileMove {
	int posX;
	int posY;
	int Win = 10;
	int Lose = -5;
	ArrayList<JFrame> AllFrame = new ArrayList<>();
	JFrame Start()
	{
    	JFrame newFrame = new JFrame();
    	newFrame.setSize(100, 100);
    	newFrame.setUndecorated(true);
    	newFrame.setAutoRequestFocus(false);
    	newFrame.setFocusableWindowState(false);
    	newFrame.getContentPane().setBackground(Color.blue);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
    	newFrame.setLocation((int)(dim.width *.2),(int)(dim.height*.8));
    	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	CreateGoal();
    	CreateBombs();
    	return newFrame;
	}
	
	int GetInput(JFrame CurrentFrame,String input)
	{
        switch(input)
        {
        	// Movement Function
        	case "w" -> {CurrentFrame.setLocation(CurrentFrame.getX(), CurrentFrame.getY() - 50);}
        	case "a" -> {CurrentFrame.setLocation(CurrentFrame.getX()-50, CurrentFrame.getY());}
        	case "s" -> {CurrentFrame.setLocation(CurrentFrame.getX(), CurrentFrame.getY() + 50);}
        	case "d" -> {CurrentFrame.setLocation(CurrentFrame.getX()+50, CurrentFrame.getY());}
        }
        posX = CurrentFrame.getX()+(int)(CurrentFrame.getWidth()*0.5f);
        posY = CurrentFrame.getY()+(int)(CurrentFrame.getHeight()*0.5f);
        
        return CheckWin();	
	}
	
	void CreateGoal()
	{
    	JFrame Goal = new JFrame();
    	Goal.setName("Goal");
    	Goal.setSize(100, 100);
    	Goal.setUndecorated(true);
    	Goal.setAutoRequestFocus(false);
    	Goal.setFocusableWindowState(false);
    	Goal.getContentPane().setBackground(Color.green);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
    	Goal.setLocation((int)(dim.width *.5),(int)(dim.height*.8));
    	Goal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	Goal.setVisible(true);
    	AllFrame.add(Goal);
	}
	
	void CreateBombs()
	{
    	JFrame Bomb = new JFrame();
    	Bomb.setName("Bomb");
    	Bomb.setSize(100, 100);
    	Bomb.setUndecorated(true);
    	Bomb.setAutoRequestFocus(false);
    	Bomb.setFocusableWindowState(false);
    	Bomb.getContentPane().setBackground(Color.red);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        Bomb.setLocation((int)(dim.width *.5),(int)(dim.height*.7));
        Bomb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Bomb.setVisible(true);
    	AllFrame.add(Bomb);
	}
	
	void kill()
	{
		for(JFrame J : AllFrame)
		{			
			J.setVisible(false);
			J.dispose();
		}
	}
	
	int CheckWin()
	{
		
		for(JFrame J : AllFrame)
		{
			
			if(J.getName().compareTo("Goal") == 0)
			{
				if(J.getX()< posX && posX < (J.getX()+J.getWidth()))
					if(J.getY()< posY && posY < (J.getY()+ J.getWidth()))
						return 1;
			}
			if(J.getName().compareTo("Bomb") == 0)
			{
				if(J.getX()< posX && posX < (J.getX()+J.getWidth()))
					if(J.getY()< posY && posY < (J.getY()+ J.getWidth()))
						return -1;
			}
		}
		return 0;
	}

}
