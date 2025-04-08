import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;
 
 public class SimonSays {
 	int Wx;
 	int Wy;
 	int Level = 3;
 	Countdown Count = new Countdown(0,0);
 	ArrayList<JFrame> AllFrame = new ArrayList<>();
 	Random R = new Random();
 	
 	
 	ArrayList<Character> Input = new ArrayList<>(List.of('w','a','s','d'));
    private static int[][] Colors = new int[4][3]; 
    
    ArrayList<Integer> ButtonPress = new ArrayList<>();
    ArrayList<Integer> Flash = new ArrayList<>();
 	int Cycle;
 	boolean Flashing;
 	boolean Stop;
    
    
 	
 	JFrame Start()
 	{
 		
 	 	Cycle = 0;
 	 	Flashing = true;
 	 	Stop = true;
 		
 		for (int[] c : Colors) 
 			Arrays.fill(c,0);
 	
 		if(Level == 3)
 		{ 			
 		for(int i = 0; i < Level;i++) 
 			Flash.add(R.nextInt(4));
 			Flash.add(-1);
 		}
 		
 		Colors[0][0] = 255;
 		Colors[1][1] = 255; 
 		Colors[2][0] = 255;Colors[2][1] = 255;
 		Colors[3][2] = 255;

 		
     	JFrame newFrame = new JFrame();
     	newFrame.setSize(318, 320);
     	newFrame.setUndecorated(true);
     	newFrame.setAutoRequestFocus(false);
     	newFrame.setFocusableWindowState(false);
     	newFrame.getContentPane().setBackground(Color.magenta);
         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        newFrame.setLocation(dim.width - newFrame.getSize().width - 10, 10);
     	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     	Wx = newFrame.getX();
     	Wy = newFrame.getY();

     	CreateTile((100*(1%3) + 8)+newFrame.getX(),(100*(1/3) + 30)+newFrame.getY(),0,true);
     	CreateTile((100*(3%3) + 8)+newFrame.getX(),(100*(3/3) + 30)+newFrame.getY(),1,true);
     	CreateTile((100*(4%3) + 8)+newFrame.getX(),(100*(4/3) + 30)+newFrame.getY(),2,true);
     	CreateTile((100*(5%3) + 8)+newFrame.getX(),(100*(5/3) + 30)+newFrame.getY(),3,true);
     	CreateTile((100*(1%3) + 8)+newFrame.getX(),(100*(1/3) + 30)+newFrame.getY(),0,false);
     	CreateTile((100*(3%3) + 8)+newFrame.getX(),(100*(3/3) + 30)+newFrame.getY(),1,false);
     	CreateTile((100*(4%3) + 8)+newFrame.getX(),(100*(4/3) + 30)+newFrame.getY(),2,false);
     	CreateTile((100*(5%3) + 8)+newFrame.getX(),(100*(5/3) + 30)+newFrame.getY(),3,false);
     	
     	return newFrame;
 	}
 	
 	void CreateTile(int x , int y , int c , boolean visible)
 	{
     	JFrame newFrame = new JFrame();
     	newFrame.setSize(100, 100);
     	newFrame.setUndecorated(true);
     	newFrame.setAutoRequestFocus(false);
     	newFrame.setFocusableWindowState(false);
     	newFrame.setAlwaysOnTop(true);
     	newFrame.getContentPane().setBackground(new Color(Colors[c][0],Colors[c][1],Colors[c][2],visible?64:255)); 
     	newFrame.setLocation(x,y);
     	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     	newFrame.setVisible(visible);
     	AllFrame.add(newFrame);
   
 	}
 	

 	
 	
 	int GetInput(JFrame CurrentFrame,String input)
 	{  
 		if(Flashing)
 		CheckFlash();
 		
 		char in = input.charAt(0);
 		for(int i = 0 ; i< Input.size() ; i ++)
 		{
 			if(!Flashing)
 				SwapVisible(i,in == Input.get(i));			
 		}
 		if(Input.contains(in))		
 		{
 			Flashing = false;
 			return CheckInput(in);
 		}
         return 0;	
 	}
 	
 	int CheckInput(char in)
 	{
 		int i = Input.indexOf(in);
 		ButtonPress.add(i);
 		
 		System.out.println("You added "+in+" with a value "+i);
 		System.out.println("Flash: "+Flash);
 		System.out.println("Inputs: "+ButtonPress);
 		
 		
 		ArrayList<Integer> Check = new ArrayList<>(Flash.subList(0, ButtonPress.size()));
 		if(Check.equals(ButtonPress))
 		{
 			System.out.println("You added the Correct Button");
 			System.out.println("You: " +ButtonPress.size() +" Overall: "+Flash.size());
 			if(ButtonPress.equals(Flash.subList(0, Flash.size()-1)))
 			{
 				System.out.println("Level Up");
 				ButtonPress.clear();
 				Flash.add(Flash.size()-1, R.nextInt(4));
 				Flashing = true;
 				Level++;
 				Cycle = 0;
 				return Level -1;
 			}

 		}
 		else
 		{
 			System.out.println("You added the Wrong Button");
 			Cycle = 0;
 			return 0-Level;
 		}
 		
 		return 0;

 		
 	}
 	
 	void CheckFlash()
 	{
 		if(!Count.Play)
 		{
 			StartCountdown();
 		}
 	}
 	
    void StartCountdown()
    {
    	Stop = !Stop;
    	
 		for(int i = 0 ; i< Input.size() ; i ++)
				SwapVisible(i,i==Flash.get(Cycle) && Stop);
 		
    	Count = new Countdown(3,0);
    	Thread C = new Thread(Count);
    	C.start();
    	if(Stop)
    	Cycle = Cycle < Level ? Cycle+1 :0;
    }
 	
 	void SwapVisible(int i ,  boolean on)
 	{		
 		AllFrame.get(i+4).setVisible(on);
 	}
 		
 	void kill()
 	{
 		for(JFrame J : AllFrame)
 		{			
 			J.setVisible(false);
 			J.dispose();
 		}
 		Flashing = true;
 		ButtonPress.clear();
 		AllFrame.clear();
 		for (int[] c : Colors) 
 			Arrays.fill(c,0);
 	}
 	


 }