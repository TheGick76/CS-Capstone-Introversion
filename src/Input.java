//Importing what we need for creating a window

//Importing what we need for listners and windows
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

//Class input extend the contents of JFrame and implements KeyListener
class Input extends JFrame implements KeyListener{

    //Initialize our sockets and viarables
    public String InputString = "";

    public DataInputStream InputReader = new DataInputStream(System.in);

    public static Socket inputSocket = null;

    public static DataOutputStream out = null;
    
    
    private Image Image = new ImageIcon("R.jpg").getImage();
    private Image CenterImage = new ImageIcon("Horse.jpg").getImage();
    
    
    private static float[] Render = {.25f,.25f,.25f,.25f}; 
    private static boolean CanRePaint = true;

    //Our main function
    public static void main(String args[])
    {
        //Creates an instance of Input
        new Input();
        //Create Queue stuff
        try
        {
          inputSocket = new Socket("127.0.0.1", 1027);
          out = new DataOutputStream(inputSocket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    //Input constructor
    public Input()
    {
        //Sets the title of the window
        setTitle("Inputs");
        //Closes the application on the frame closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Frame can be focused
        setFocusable(true);
        //Sets the size of the window
        setSize(500,500);
        //Key inputs are sent to this window when focused
        setFocusTraversalKeysEnabled(false);
        //Adds Keylsitener to this instance
        addKeyListener(this);
        //Sets window visible
        this.setVisible(true);
        this.setResizable(false);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        
        System.out.println("draw");
        
        // Get the size of the window
        int width = getWidth();
        int height = getHeight();



        //Draws image at center position
        int CenterImageWidth = CenterImage.getWidth(this);
        int CenterImageHeight = CenterImage.getHeight(this);
        int CenterImageXpos = width / 2 - CenterImageWidth / 2; // Center horizontally
        int CenterImageYpos = height / 2 - CenterImageHeight / 2; // Center vertically
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[2]));
        g.drawImage(CenterImage, CenterImageXpos, CenterImageYpos, this);

        //Draws an image above the Center image
        int TopImageX = CenterImageXpos + (CenterImageWidth - Image.getWidth(this)) / 2;
        int TopImageY = CenterImageYpos - Image.getHeight(this);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[0]));
        g.drawImage(Image, TopImageX, TopImageY, this);
        
        
        //Draws an image to the Left of Center image
        int LeftImageX = CenterImageXpos - Image.getWidth(this);
        int LeftImageY = CenterImageYpos + (CenterImageHeight - Image.getHeight(this)) / 2;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[1]));
        g.drawImage(Image, LeftImageX, LeftImageY, this);
        
        //Draws an image to the Right of Center image with 100% Composite
        int RightImageX = CenterImageXpos + Image.getWidth(this);
        int RightImageY = CenterImageYpos + (CenterImageHeight - Image.getHeight(this)) / 2;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Render[3]));
        g.drawImage(Image, RightImageX, RightImageY, this);
    }
    
    public void Render(String outputString)
    {

    	// Variable = (condition) ? condition true : condition false;
        Render[0] = outputString.equals("w")? 1.0f : 0.25f;
        Render[1] = outputString.equals("a")? 1.0f : 0.25f;
        Render[2] = outputString.equals("s")? 1.0f : 0.25f;
        Render[3] = outputString.equals("d")? 1.0f : 0.25f;
        
        if((Render[0] == 1.0f || Render[1] == 1.0f || Render[2] == 1.0f || Render[3] == 1.0f) && CanRePaint)
        {
        	CanRePaint = false;
        	repaint();
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
        System.out.println(e.getKeyCode());
       
        try
        {
           // InputString = e.getKeyChar();
            InputString = Character.toString(e.getKeyChar());
            System.out.println(InputString);
            out.writeUTF(InputString);
            
            Render(InputString);
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
            out.writeUTF("CLEAR");
            Render("CLEAR");
            CanRePaint = true;
            repaint();
        }
        catch (IOException i)
        {
            System.out.println(i);
        }
    }

}