//Importing what we need for creating a window

//Importing what we need for listners and frames
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import javax.swing.*;

//Class input extend the contents of JFrame and implements KeyListener
class Input extends JFrame implements KeyListener{

    //Initialize our sockets and viarables
    public String InputString = "";


    //Creates our socket which will connect to the server
    public static Socket inputSocket = null;

    //How we will be sending our data
    public static DataOutputStream out = null;

    //A string that will contain text that will have the status of the server connection
     private static String ServerStatus = "Server Status: Unconnected";
     private static JButton ServerConnectButton = new JButton("Connect");
     private static JFrame Frame;
     private boolean Connected = false;
    
    //Images 
    private static Image Image = new ImageIcon("R.jpg").getImage();
    private static Image CenterImage = new ImageIcon("Horse.jpg").getImage();
    private static Image SpaceImage = new ImageIcon("Squib.jpg").getImage();

    
    //For rendering the images and their offsets
    private static Image[] Pictures;
    private static float[] Render; 
    private static int[][] PicPos;
    private static int key;
    
    //Locking inputs
    private static boolean CanReInput= true;
    private static char keyPressed = '&';
    private static boolean CanRePaint = true;
    
    private static Character[] Inputs = {'q', 'w', 'e', 'a', 's','d',' '};


    //Our main function
    public static void main(String args[])
    {
    	key = Inputs.length;
    	
    	Pictures = new Image[key];
    	Arrays.fill(Pictures,Image); // Temp fill for pictures
    	Pictures[4] = CenterImage;  // How pictures should be implemented
    	Pictures[6] = SpaceImage;
    	
    	
    	Render = new float[key];
    	Arrays.fill(Render,0.25f);
    	PicPos = new int[key][2];
    	
    	
        Frame = new Input();
    }
    
    //Input constructor
    public Input()
    {
        //Set layout manager
    	getContentPane().setBackground(Color.gray);
        setLayout(new BorderLayout());
        //Sets the title of the window
        setTitle("Inputs");
        //Closes the application on the frame closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Frame can be focused
        setFocusable(true);
        setAlwaysOnTop(true);
        setAutoRequestFocus(true);
        //Sets the size of the window
        setSize(318,320);
        //sets window 10px off the top left corner
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation(dim.width - getSize().width - 10, 10);
        //Key inputs are sent to this window when focused
        setFocusTraversalKeysEnabled(false);
        //Adds Keylsitener to this instance
        addKeyListener(this);
        //Add button
        
        //Creates and adds a new action listener
        ServerConnectButton.addActionListener((ActionEvent e) -> {
            //When clicked try to connect
            //Possible Connect(User Typed IP); For cross machine IPC
            Connect();
        } //Using this method as to not overload
        //Key listner with if statements and bog it down anymore
        );
        //Stops the action lsitener from taking priority over the key listener
        ServerConnectButton.setFocusable(false);
        //Adding the button with layout
        add(ServerConnectButton, BorderLayout.PAGE_END);
        //Sets window visible
        setVisible(true);
        setResizable(false);
    }

    //Function for trying to connect to server
    public void Connect()
    {
        //Try to connect
        try
        {
            //If succesful
            //Does work with LAN IPs!!!!
         inputSocket = new Socket("127.0.0.1", 1027);
         out = new DataOutputStream(inputSocket.getOutputStream());
         	Connected = true;
         	Frame.remove(ServerConnectButton);
            repaint();
         }
         catch (IOException e) 
         {
           ServerStatus = "Server Status: Failed to Connect!";
           repaint();
         }
    }   
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        

        if(!Connected)
        	g.drawString(ServerStatus, 10, 100);
        else
        {        
	        for (int i = 0; i < 7 ; i ++)
	        {
	        	PicPos[i][0] = (100*(i%3) + 8);
	        	PicPos[i][1] = (100*(i/3) + 30);
	        }
	       
	        for(int i = 0;i < Inputs.length; i++)
	        {
	            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[i]));
	            g.drawImage(Pictures[i], PicPos[i][0], PicPos[i][1], this);
	        }
        
      }
    }
    
    public void Render(Character in)
    {
    	
    	for(int i = 0 ; i < Inputs.length ; i++)
    	{
    		// Variable = (condition) ? condition true : condition false;
    		Render[i] = in == Inputs[i]? 1.0f : 0.25f; 
    		if(Render[i] == 1 && CanRePaint)
    		{
    			CanRePaint = false;
        		key = i;
        		repaint(PicPos[i][0],PicPos[i][1],Pictures[i].getWidth(null),Pictures[i].getHeight(null));
    		}
    	}
    }
 

    //Key listener actions
    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
    public void keyPressed(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet.");
        try
        {
        	//Only reads desired inputs, turns all other inputs to '&'
          if(Arrays.asList(Inputs).contains(e.getKeyChar()) && CanReInput)
          	{
        		//  CanReInput = false; 
        		  keyPressed = e.getKeyChar();
                  if(!(e.getKeyChar() == ' '))
        		  InputString = Character.toString(e.getKeyChar());
                  else
                  InputString = " ";
        		  out.writeUTF(InputString);  
        		  Render(e.getKeyChar());
              //    CanReInput = false;
        	}
          
          else if(CanReInput)
        	  keyPressed = '&';
        }
        catch (IOException i)
        {
            System.out.println(i);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet.");
      try
        {  
            //This is jsut for the platformer mini game, sorry its messy
            if(e.getKeyChar() == 'd')
            {
                out.writeUTF("DUP");
            }
            if(e.getKeyChar() == 's')
            {
                out.writeUTF("SUP");
            }
            if(e.getKeyChar() == 'a')
            {
                out.writeUTF("AUP");
            }
            if(e.getKeyChar() == 'w')
            {
                out.writeUTF("WUP");
            }
                
    	  //Only acts if a desired key was pressed then released
    	  if(e.getKeyChar()==keyPressed && keyPressed != '&')
    	  {
            out.writeUTF("CLEAR");
            Render(null);
            CanRePaint = true;
            CanReInput = true;
            repaint(PicPos[key][0],PicPos[key][1],Pictures[key].getWidth(null),Pictures[key].getHeight(null));
            keyPressed = '&';
    	  }
          if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
          {
           int result = JOptionPane.showConfirmDialog(this, "Quit?",
               "Quit", JOptionPane.WARNING_MESSAGE);
         if (result  == JOptionPane.YES_OPTION)
           {
            out.writeUTF("QUIT");
            this.dispose();
           }
          }
        }
        catch (IOException i)
        {
            System.out.println(i);
        }
    }

}