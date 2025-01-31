//Importing what we need for creating a window

//Importing what we need for listners and windows
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
       
        try
        {
           // InputString = e.getKeyChar();
            InputString = Character.toString(e.getKeyChar());
            System.out.println(InputString);
            out.writeUTF(InputString);
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
        }
        catch (IOException i)
        {
            System.out.println(i);
        }
    }

}