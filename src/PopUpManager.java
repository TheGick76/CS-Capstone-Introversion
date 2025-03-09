import java.awt.*;
import javax.swing.*;

public class PopUpManager
{		
		private boolean Active = false;
		private Himothy ref;
		private JFrame CurrentFrame;
		private int Game;
		TileMove TMove = new TileMove();
		
		PopUpManager(Himothy playerRef)
		{
			this.ref = playerRef;
		}
		
		public boolean GetActive()
		{
			return Active;
		}
		
		public void KillFrame()
		{
			CurrentFrame.setVisible(false);
            switch(Game)
            {
            	case 1-> TMove.kill();


            }
            Game = -1;
			Active = false;
			CurrentFrame = null;
		}
	
	
        public void toggleNewFrame(int Game) 
        {
        	this.Game = Game;
        	Active = true;
        	switch(Game)
        	{
        	case 1 -> CurrentFrame = TMove.Start();
        	case 2 -> System.out.println("Fail tile");
        	}
        	CurrentFrame.setVisible(true);
        }
        
        public void Input(String input)
        {
        	if(input.compareTo("e")==0)
        		KillFrame();
        	
            switch(Game)
            {
            	case 1-> Reward(TMove.GetInput(CurrentFrame, input));


            }
        }
        
        private void Reward(int b)
        {        		
	        	if(b==1)
	                switch(Game)
	                {
	                	case 1-> ref.score += TMove.Win;
	                	
	                }
	        	
	        	else if(b==-1)
	                switch(Game)
	                {
	                	case 1-> ref.score += TMove.Lose;
	                }
	        	if(b!=0)
	        		KillFrame();
        }
}