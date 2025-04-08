import javax.swing.*;

public class PopUpManager
{		
		private boolean Active = false;
		private Himothy ref;
		private JFrame CurrentFrame = null;
		private boolean Finished = false;
		private boolean MovementLock = false;
		private int Game = 0;
 		TileMove TMove = new TileMove();
		Platformer platformer = new Platformer();
		DatingSim Crush = new DatingSim();
		SimonSays Simon = new SimonSays();
 		
 		PopUpManager(Himothy playerRef)
 		{
 			this.ref = playerRef;
 		}
 		
		public boolean GetActive()
		{
			return Active;
		}
		
		public boolean GameFinished()
		{
			return Finished;
		}
		
		public void KillFrame()
		{
			CurrentFrame.setVisible(false);
            switch(Game)
            {
            	case 1 -> TMove.kill();
				case 2 -> platformer.kill();
				case 3 -> Crush.kill();
				case 4 -> Simon.kill();
            }
            Game = 0;
			Active = false;
			CurrentFrame = null;
			
		}
	
	
        public void toggleNewFrame(int game) 
        {
        	this.Game = game;
        	Active = true;
        	Finished = false;
           	switch(Game)
         	{
         	case 1 -> CurrentFrame = TMove.Start();
			case 2 -> CurrentFrame = platformer.Start();
			case 3 -> CurrentFrame = Crush.Start();
			case 4 -> CurrentFrame = Simon.Start();
         	default -> System.out.println("Fail tile");
         	}
        	CurrentFrame.setVisible(true);
        }
        
        public boolean Input(String input)
        {
        	
             switch(Game)
             {
             case 1-> {
				if(!MovementLock && input.matches("w|a|s|d")){
				Reward(TMove.GetInput(CurrentFrame, input));
					MovementLock = true;
				}
				else if(input.matches("WUP|SUP|AUP|DUP"))
				{
					MovementLock = false;
				}
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
				{
					// get status of Crush and make 'Finished' equal to 
					//if the game is done or not so tile goes on cool down
					KillFrame();
				}
			 }
			 
             case 4-> {
				if(!MovementLock && input.matches("w|a|s|d|v")){
				Reward(Simon.GetInput(CurrentFrame, input));
					if(input.matches("w|a|s|d"))
					MovementLock = true;
				}
				else if(input.matches("WUP|SUP|AUP|DUP"))
				{
					MovementLock = false;
				}
			if(input.compareTo("e")==0)
         		KillFrame();
			}
             }
         return Finished;

			 
        }
        
        private void Reward(int b)
        {        		
	        	if(b>0)
	                switch(Game)
	                {
						case 1 -> ref.UpdateScore(TMove.Win);
	                	case 2-> ref.UpdateScore(platformer.Win);
	                	case 4-> ref.UpdateScore(b);
	                }
	        	
	        	else if(b<0)
	                switch(Game)
	                {
						case 1 -> ref.UpdateScore(TMove.Lose);
						case 2-> ref.UpdateScore(platformer.Lose);
						case 4-> ref.UpdateScore(b);
	                }
	        	if(b!=0 && !(Game==4 && b>0))
	        	{
	        		MovementLock = false;
	        		Finished = true;
	        		KillFrame();
	        	}
        }
}