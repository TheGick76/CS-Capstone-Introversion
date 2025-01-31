//Importing what we need for creating a window
import java.awt.Color;
import java.io.*;
import java.net.*;
import javax.swing.*;

//Class of Output whichextends the content of JFrame
class Output extends JFrame{

    public static Socket receiveingSocket = null;

    public static DataInputStream in = null;

    public static ServerSocket ss = null;

    public static String outputString = "";
    
    public static String LastInput = "";
    


//Our main function
public static void main(String args[])
    {
        //Creates instance of Output
        Output window = new Output();

        try
        {
            ss = new ServerSocket(1027);
            receiveingSocket = ss.accept();
            System.out.println("Client joined");
            in = new DataInputStream(new BufferedInputStream(receiveingSocket.getInputStream()));
        }
        catch(IOException i)
        {
          System.out.println(i);
        }

        ReadInput(window);

    }
    //Constructor for Output
    public Output()
    {
        //Sets the title of the window
        setTitle("Output");
        //Lets the window be focusable
        setFocusable(true);
        //Closes program when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets sizze of window
        setSize(500,500);
        //sets window visible
        setVisible(true);
    }
    
    //Make multithread later
    public static void ReadInput(JFrame window)
    {
        while(!outputString.equals("p"))
        {
            try
                {
                    outputString = in.readUTF();
                    
                    
                    switch(outputString)
                    {
                    	case "w":
                    		if(LastInput.compareTo("w") !=0) 
                    		{
                    			window.getContentPane().setBackground(Color.BLUE);
                    			System.out.println(outputString);		
                    		}
                        break;
                        
                    	case "a":
                    		if(LastInput.compareTo("a") !=0) 
                    		{
                    			window.getContentPane().setBackground(Color.RED);
                    			System.out.println(outputString);		
                    		}
                        break;
                        
                    	case "s":
                    		if(LastInput.compareTo("s") !=0) 
                    		{
                    			window.getContentPane().setBackground(Color.YELLOW);
                    			System.out.println(outputString);		
                    		}
                        break;
                        
                    	case "d":
                    		if(LastInput.compareTo("d") !=0) 
                    		{
                    			window.getContentPane().setBackground(Color.GREEN);
                    			System.out.println(outputString);		
                    		}
                    	break;
                    	
                    	case "CLEAR":
                			window.getContentPane().setBackground(Color.WHITE);
                			System.out.println(outputString);
                        break;
                    }
                    
 /*                   
                    if(outputString.equals("w") && LastInput.compareTo("w") !=0)
                    {
                        window.getContentPane().setBackground(Color.BLUE);
                        System.out.println(outputString);
                    }
                    else
                    {
                        window.getContentPane().setBackground(Color.WHITE);
                    }
*/
                  LastInput = outputString;
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
        }
    }

}