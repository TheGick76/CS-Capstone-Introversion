import javax.swing.*;

public class PopUpManager
{		
		private boolean Active = false;
		private Himothy ref;
		private JFrame CurrentFrame = null;
		
		private int Game = 0;
 		TileMove TMove = new TileMove();
		Platformer platformer = new Platformer();
		DatingSim Crush = new DatingSim();
 		
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
				case 2 -> platformer.kill();
				case 3 -> Crush.kill();
            }
            Game = 0;
			Active = false;
			CurrentFrame = null;
			
		}
	
	
        public void toggleNewFrame(int game) 
        {
        	this.Game = game;
        	Active = true;
           	switch(Game)
         	{
         	case 1 -> CurrentFrame = TMove.Start();
			case 2 -> CurrentFrame = platformer.Start();
			case 3 -> CurrentFrame = Crush.Start();
         	default -> System.out.println("Fail tile");
         	}
        	CurrentFrame.setVisible(true);
        }
        
        public void Input(String input)
        {
        	
         	
             switch(Game)
             {
             case 1-> {Reward(TMove.GetInput(CurrentFrame, input));
			if(input.compareTo("e")==0)
         		KillFrame();
			}
			 case 2-> {Reward(platformer.GetInput(input));
				if(platformer.i == 2)
         		{KillFrame();}
			 }
			 case 3-> 
			 {
				//Not using reward becuase it closes out our window
				//Constatnly updating score so if player quits they get the appropriate score
				ref.UpdateScore(Crush.GetInput(input));
				if(input.compareTo("e")==0)
         		KillFrame();
			 }
             }

			 
        }
        
        private void Reward(int b)
        {        		
	        	if(b==1)
	                switch(Game)
	                {
						case 1 -> ref.UpdateScore(TMove.Win);
	                	case 2-> ref.UpdateScore(platformer.Win);
	                }
	        	
	        	else if(b==-1)
	                switch(Game)
	                {
						case 1 -> ref.UpdateScore(TMove.Lose);
						case 2-> ref.UpdateScore(platformer.Lose);
	                }
	        	if(b!=0)
	        		KillFrame();
        }
}