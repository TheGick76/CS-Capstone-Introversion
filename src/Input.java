//Importing what we need for creating a window

//Importing what we need for listners and windows
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

//Class input extend the contents of JFrame and implements KeyListener
class Input extends JFrame implements KeyListener{

    //Initialize our string that will be used for storing which key was pressed
    public String InputString = "";

    //Create a data stream for terminal 
  //  public DataInputStream InputReader = new DataInputStream(System.in);

    //Create a socket for communication
    public static Socket inputSocket = null;

    //Create output stream for our inputs
    public static DataOutputStream out = null;

    //Our main function
    public static void main(String args[])
    {
        //Creates an instance of Input
        new Input();

        //Try and create the input socket
        try
        {
            //Creating socket with local loop back IP and an arbitrary port
          inputSocket = new Socket("127.0.0.1", 1027);
          //Will take data and and send it out that is sent from the socket
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
        setVisible(true);
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
       
        //Try and take the pressed key and send it out
        try
        {
           // InputString = e.getKeyChar();
            InputString = Character.toString(e.getKeyChar());
            System.out.println(InputString);
            out.writeUTF(InputString);
        }
        //If there was an error
        catch (IOException i)
        {
            System.out.println(i);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet.");
      //Try and send when the key has been released
      try
        {
            //Send out string "CLEAR" whihch will be interpreted as key has been released
            out.writeUTF("CLEAR");
        }
        //If there was an error sending that out
        catch (IOException i)
        {
            System.out.println(i);
        }
    }

}