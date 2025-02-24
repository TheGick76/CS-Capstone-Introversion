//Importing what we need for creating a window

//Importing what we need for listners and frames
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.time.*;

//Class input extend the contents of JFrame and implements KeyListener
class Input extends JFrame implements KeyListener{

    //Initialize our sockets and viarables
    public String InputString = "";

    //Temp
    //Creates our socket which will connect to the server
    public static Socket inputSocket = null;

    //How we will be sending our data
    public static DataOutputStream out = null;

    //A string that will contain text that will have the status of the server connection
     private static String ServerStatus = "Server Status: Unconnected";
    
    //Images 
    private final Image Image = new ImageIcon("R.jpg").getImage();
    private final Image CenterImage = new ImageIcon("Horse.jpg").getImage();
    
    //For rendering the images and their offsets
    private final static float[] Render = {.25f,.25f,.25f,.25f,.25f}; 
    private final static int[][] PicPos = new int[5][2];	// X cord , Y cord
    private static int key = 5;
    
    //Locking inputs
    private static boolean CanReInput= true;
    private static char keyPressed = '&';
    private static boolean CanRePaint = true;


    //Our main function
    public static void main(String args[])
    {
        //Creates an instance of Input
        //Class of Input has already implements key listners and JFrame stuff
        JFrame Frame = new Input();
        System.out.println("Test 1");
        System.out.println(Frame.getTitle());
        System.out.println("Test 1");
        //PopUpManager.ConnectPop(Frame);
        
    }
    
    //Input constructor
    public Input()
    {
        //Set layout manager
        setLayout(new BorderLayout());
        //Sets the title of the window
        setTitle("Inputs");
        //Closes the application on the frame closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Frame can be focused
        setFocusable(true);
        setAlwaysOnTop(true);
        //Sets the size of the window
        setSize(500,500);
        //sets window 10px off the top left corner
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation(10, 10);
        //Key inputs are sent to this window when focused
        setFocusTraversalKeysEnabled(false);
        //Adds Keylsitener to this instance
        addKeyListener(this);
        //Add button
        JButton ServerConnectButton = new JButton("Connect");
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
         inputSocket = new Socket("127.0.0.1", 1027);
         out = new DataOutputStream(inputSocket.getOutputStream());
      //   ServerStatus.setText("Connected!");
            ServerStatus = "Server Status: Connected!";
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

        g.drawString(ServerStatus, 10, 100);
        
        // Get the size of the window
        int width = getWidth();
        int height = getHeight();



        //Draws image at center position [s]
        int CenterImageWidth = CenterImage.getWidth(this);
        int CenterImageHeight = CenterImage.getHeight(this);
        int CenterImageXpos = width / 2 - CenterImageWidth / 2; // Center horizontally
        int CenterImageYpos = height / 2 - CenterImageHeight / 2; // Center vertically
        
        PicPos[2][0] = CenterImageXpos;
        PicPos[2][1] = CenterImageXpos;
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[2]));
        g.drawImage(CenterImage, CenterImageXpos, CenterImageYpos, this);

        //Draws an image above the Center image [w]
        int TopImageX = CenterImageXpos + (CenterImageWidth - Image.getWidth(this)) / 2;
        int TopImageY = CenterImageYpos - Image.getHeight(this);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[0]));
        g.drawImage(Image, TopImageX, TopImageY, this);
        
        PicPos[0][0] = TopImageX;
        PicPos[0][1] = TopImageY;
        
        //Draws an image to the Left of Center image [a]
        int LeftImageX = CenterImageXpos - Image.getWidth(this);
        int LeftImageY = CenterImageYpos + (CenterImageHeight - Image.getHeight(this)) / 2;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[1]));
        g.drawImage(Image, LeftImageX, LeftImageY, this);
        
        PicPos[1][0] = LeftImageX;
        PicPos[1][1] = LeftImageY;
        
        //Draws an image to the Right of Center image [d]
        int RightImageX = CenterImageXpos + Image.getWidth(this);
        int RightImageY = CenterImageYpos + (CenterImageHeight - Image.getHeight(this)) / 2;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[3]));
        g.drawImage(Image, RightImageX, RightImageY, this);
        
        PicPos[3][0] = RightImageX;
        PicPos[3][1] = RightImageY;
        
        //Draws an image to the Right of Center image [d]
        int TopRightImageX = CenterImageXpos + Image.getWidth(this);
        int TopRightImageY = CenterImageYpos - Image.getHeight(this) + (CenterImageHeight - Image.getHeight(this)) / 2;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[4]));
        g.drawImage(Image, TopRightImageX, TopRightImageY, this);
        
        PicPos[4][0] = RightImageX;
        PicPos[4][1] = TopRightImageY;
    }
    
    public void Render(String outputString)
    {

    	// Variable = (condition) ? condition true : condition false;
        Render[0] = outputString.equals("w")? 1.0f : 0.25f;
        Render[1] = outputString.equals("a")? 1.0f : 0.25f;
        Render[2] = outputString.equals("s")? 1.0f : 0.25f;
        Render[3] = outputString.equals("d")? 1.0f : 0.25f;
        Render[4] = outputString.equals("e")? 1.0f : 0.25f;

        
        //Will only repaint a certain area based off of what key got pressed
        if((Render[0] == 1.0f || Render[1] == 1.0f || Render[2] == 1.0f || Render[3] == 1.0f || Render[4] == 1.0f) && CanRePaint)
        {
        	CanRePaint = false;
        	if (Render[0] == 1.0f)
        	{
        		key = 0;
        		repaint(PicPos[0][0],PicPos[0][1],100,100);
        	}
        	else if (Render[1] == 1.0f)
        	{
        		key = 1;
        		repaint(PicPos[1][0],PicPos[1][1],100,100);
        	}
        	else if (Render[2] == 1.0f)
        	{
        		key = 2;
        		repaint(PicPos[2][0],PicPos[2][1],100,100);
        	}
        	else if (Render[3] == 1.0f)
        	{
        		key = 3;
        		repaint(PicPos[3][0],PicPos[3][1],100,100);
        	}
        	else if (Render[4] == 1.0f)
        	{
        		key = 4;
        		repaint(PicPos[4][0],PicPos[4][1],100,100);
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
       //When key is pressed print the code of the key
       // System.out.println(e.getKeyCode());
       
        try
        {
        	//Only reads desired inputs, turns all other inputs to '&'
          if(e.getKeyChar()=='w'||e.getKeyChar()=='a'||e.getKeyChar()=='s'||e.getKeyChar()=='d'||e.getKeyChar()=='e')
          {
        	  if(CanReInput)
        	  {
        		  CanReInput = false; 
        		  keyPressed = e.getKeyChar();
        		  InputString = Character.toString(e.getKeyChar());
        		  out.writeUTF(InputString);  
        		  Render(InputString);
        		  if(e.getKeyChar() == 'e')
        		  {
        			  try {
						Thread.sleep(50);
						requestFocus();
						Thread.sleep(50);
			            out.writeUTF("CLEAR");
			            Render("CLEAR");
			            CanRePaint = true;
			            CanReInput = true;
			            repaint(PicPos[key][0],PicPos[key][1],100,100);
			            keyPressed = '&';
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		  }
        	  }
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
           // InputString = e.getKeyChar();
          //  InputString = Character.toString(e.getKeyChar());
          //  System.out.println(InputString);
    	  
    	  
    	  //Only acts if a desired key was pressed then released
    	  if(e.getKeyChar()==keyPressed && keyPressed != '&')
    	  {
            out.writeUTF("CLEAR");
            Render("CLEAR");
            CanRePaint = true;
            CanReInput = true;
            repaint(PicPos[key][0],PicPos[key][1],100,100);
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