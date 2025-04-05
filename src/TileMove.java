import java.awt.*;
 import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
 
 public class TileMove {
 	int posX;
 	int posY;
 	int Win = 10;
 	int Lose = -5;
 	
 	int BombCount = 30;
 	int gridsize = 50; //ppx for square
 	int gridCount = 13; // x by x grid [please keep odd]
 	
 	private int[][] maze;
 	
 	ArrayList<JFrame> AllFrame = new ArrayList<>();
 	Random R = new Random();

	boolean MovementLock = false;
 	
 	JFrame Start()
 	{
     	JFrame newFrame = new JFrame();
     	newFrame.setSize(gridsize, gridsize);
     	newFrame.setUndecorated(true);
     	newFrame.setAutoRequestFocus(false);
     	newFrame.setFocusableWindowState(false);
     	newFrame.getContentPane().setBackground(Color.blue);
         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
     	newFrame.setLocation(0,0);
     	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     	CreateBack(gridCount,gridCount);
     	
        maze = new int[gridCount][gridCount];
        for (int i = 0; i < gridCount; i++) {
            for (int j = 0; j < gridCount; j++) {
                maze[i][j] = 0; // Initialize with walls
            }
        }
        
        CreateMaze(0,0);
                
        for (int i = 0; i < gridCount; i++)
            for (int j = 0; j < gridCount; j++)            	
                if(maze[i][j]==0)
                	CreateBombs(i,j);



        CreateGoal();     	
     	return newFrame;
 	}
 	
 	void CreateMaze(int x , int y)
 	{
 		maze[x][y] = 1; 
 		List<Integer> direction = new ArrayList<>(List.of(0,1,2,3));
 		Collections.shuffle(direction); 
 		//int[] direction = {0,1,2,3};
 		
 		for(int dir : direction)
 		{
 			int newX = x;
 			int newY = y;
 			switch(dir)
 			{
            case 0-> newX += 2; // Up
            case 1-> newX -= 2; // Down
            case 2-> newY -= 2; // Left
            case 3-> newY += 2; // Right
 			}
 			
 			if(newX > -1 && newX < gridCount && newY > -1 && newY < gridCount && maze[newX][newY]==0)
 			{
 				maze[((newX + x)/2)][((newY + y)/2)] = 1;
 				CreateMaze(newX,newY);
 			}
 		}
 	}
 	
 	
 	int GetInput(JFrame CurrentFrame,String input)
 	{
		if(!MovementLock)
        {
         switch(input)
         {
      	case "w" -> {if(CurrentFrame.getY()-50 > -25)CurrentFrame.setLocation(CurrentFrame.getX(), CurrentFrame.getY() - 50); MovementLock = true; break;}
      	case "a" -> {if(CurrentFrame.getX()-50 > -25)CurrentFrame.setLocation(CurrentFrame.getX()-50, CurrentFrame.getY());MovementLock = true;break;}
      	case "s" -> {if(CurrentFrame.getY()+50 < gridCount*gridsize)CurrentFrame.setLocation(CurrentFrame.getX(), CurrentFrame.getY() + 50);MovementLock = true;break;}
      	case "d" -> {if(CurrentFrame.getX()+50 < gridCount*gridsize)CurrentFrame.setLocation(CurrentFrame.getX()+50, CurrentFrame.getY());MovementLock = true;break;}
 		 }
		}
		else
		switch(input)
		{
			case "WUP" -> MovementLock = false;
			case "AUP" -> MovementLock = false;
			case "SUP" -> MovementLock = false;
			case "DUP" -> MovementLock = false;
		}
         posX = CurrentFrame.getX()+(int)(CurrentFrame.getWidth()*0.5f);
         posY = CurrentFrame.getY()+(int)(CurrentFrame.getHeight()*0.5f);
         
         return CheckWin();	
 	}
 	
 	void CreateGoal()
 	{
 		int x = -1 , y = -1;
 		do 
 		{
 			x = R.nextInt(gridCount);
 			y = R.nextInt(gridCount);
 		} while(maze[x][y]==0 && (x+y)>0);
 		
     	JFrame Goal = new JFrame();
     	Goal.setName("Goal");
     	Goal.setSize(gridsize, gridsize);
     	Goal.setUndecorated(true);
     	Goal.setAutoRequestFocus(false);
     	Goal.setFocusableWindowState(false);
     	Goal.getContentPane().setBackground(Color.green);
         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
     	Goal.setLocation((int)(x*gridsize),(int)(y*gridsize));
     	Goal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     	Goal.setVisible(true);
     	AllFrame.add(Goal);
 	}
 	
 	void CreateBombs(int x , int y)
 	{
     	JFrame Bomb = new JFrame();
     	Bomb.setName("Bomb");
     	Bomb.setSize(gridsize, gridsize);
     	Bomb.setUndecorated(true);
     	Bomb.setAutoRequestFocus(false);
     	Bomb.setFocusableWindowState(false);
     	Bomb.getContentPane().setBackground(Color.red);
         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
         Bomb.setLocation((int)(x*gridsize),(int)(y*gridsize));
         Bomb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         Bomb.setVisible(true);
     	AllFrame.add(Bomb);
 	}
 	
 	
 	void CreateBack(int x , int y)
 	{
     	JFrame Bomb = new JFrame();
     	Bomb.setName("Back");
     	Bomb.setLocation(0, 0);
     	Bomb.setUndecorated(true);
     	Bomb.setAutoRequestFocus(false);
     	Bomb.setFocusableWindowState(false);
     	Bomb.getContentPane().setBackground(Color.cyan);
         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
         Bomb.setSize((int)(x*gridsize),(int)(y*gridsize));
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
 		AllFrame.clear();
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