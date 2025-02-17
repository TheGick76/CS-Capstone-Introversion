//Importing what we need for creating a window
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

//Class of Output whichextends the content of JFrame
class Output extends JFrame{

    //Create a socket that will then be connected to server
    public static Socket receiveingSocket = null;

    //Data Stream that will take in the data from a selected source
    public static DataInputStream in = null;

    //The server
    public static ServerSocket ss = null;

    //Will be changed to what key / string was sent from inputs
    public static String outputString = "";
    
    //String that will be the last given input
    public static String LastInput = "";

    //if client disconnected
    public static Boolean creationBool = true;

    //Energy,Score,CurrentTilePosition,Row Position, Coloumn Position
    public static Himothy player = new Himothy(1,0,0,0,0);

    //Board
    public static Board board = new Board(4,4);

    //Change how these are assigned later
    public static int Rows = Board.Rows;
    public static int Cols = Board.Cols;
    


//Our main function
public static void main(String args[])
    {
        //Creates instance of Output, which is the window
        Output window = new Output();

        Display(window, 0);

       //Try to Connect
       do { 
           Connect(window, creationBool);
       } while (true);
       

    }

    //Attempt to connect to client
    public static void Connect(JFrame window, Boolean serverCreate)
    {
        //Try to create server
        try
        {
            if(creationBool)
            {
            //Create server at arbitray port
            ss = new ServerSocket(1027);
            creationBool = false;
            }

            //Wait for a client to connect
            receiveingSocket = ss.accept();

            //Confirming client has connected
            System.out.println("Client joined");

            System.out.println("row " + player.rowPos +" col " + player.colPos);

            //Will direct flow of inputs from the server's spcket
            in = new DataInputStream(new BufferedInputStream(receiveingSocket.getInputStream()));
        }
        //Error printing
        catch(IOException i)
        {
          System.out.println(i);
        }

        //NEED TO MULTITHREAD
        //Constatnly reads input from the inputs proccess
        ReadInput(window);
        //If this gets disconnected redo
        // receiveingSocket = ss.accept(); and associated code
    }

    //Constructor for Output
    //Frame stuff
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
        //set layoutmananager
        setLayout(new GridLayout(Rows,Cols));
        //Add tile panels
        //Initializes placements and colors, make seperate function?
        for(int i = 0; i < (Rows * Cols); i++)
        {
            JPanel temp = new JPanel();
            add(temp, i);
            if(board.BoardTiles[i].tileType.equals("PERSON"))
            {
                temp.setBackground(Color.ORANGE);
            }
            else if(board.BoardTiles[i].tileType.equals("CAT"))
            {
                temp.setBackground(Color.GREEN);
            }
            else
            {
                temp.setBackground(Color.WHITE);
            }

        }
        //sets window 10px off the top right corner
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation(dim.width - getSize().width - 10, 10);
        //sets window visible
        setVisible(true);
    }
    
    //Make multithread later
    public static void ReadInput(JFrame window)
    {
        //While the last input hasn't been an arbitrary close string
        while(!outputString.equals("QUIT"))
        {
            //try to take in inputs from the socket
            try
                {
                    //Store what the input was
                    outputString = in.readUTF();
                    
                    
                    //Basic logic to prove inputs
                    switch(outputString)
                    {
                    	case "w" -> {
                            //To make sure we don't over take inputs, make sure we only take the input again when released
                            if(LastInput.compareTo("w") !=0)
                            {
                                //window.getContentPane().setBackground(Color.BLUE); 
                                MovementLogic(outputString, window);
                                System.out.println(outputString);
                            }
                    }
                        
                    	case "a" -> {
                            //To make sure we don't over take inputs, make sure we only take the input again when released
                            if(LastInput.compareTo("a") !=0)
                            {
                                //window.getContentPane().setBackground(Color.RED);
                                MovementLogic(outputString, window);
                                System.out.println(outputString);
                            }
                    }
                        
                    	case "s" -> {
                            //To make sure we don't over take inputs, make sure we only take the input again when released
                            if(LastInput.compareTo("s") !=0)
                            {
                                //	window.getContentPane().setBackground(Color.YELLOW);
                                MovementLogic(outputString, window);
                                System.out.println(outputString);
                            }
                    }
                        
                    	case "d" -> {
                            //To make sure we don't over take inputs, make sure we only take the input again when released
                            if(LastInput.compareTo("d") !=0)
                            {
                                //window.getContentPane().setBackground(Color.GREEN);
                                MovementLogic(outputString, window);
                                System.out.println(outputString);
                            }
                    }
                    	
                    	case "CLEAR" -> //If a button has been released
                		//	window.getContentPane().setBackground(Color.WHITE);
                			System.out.println(outputString);
                    }
                //If input "w"
                    
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
                 //Store what the last input was to make sure we dont over take
                  LastInput = outputString;
                }
                //If we couldn't read from sockets print error
                catch(IOException i)
                {
                    System.out.println(i);
                    System.out.println("Client Disconnected!");
                    outputString = "QUIT";
                    in = null;
                    receiveingSocket = null;
                    //Connect(window, false);
                   // creationBool = true;
                }
        }
        outputString = "";
        System.out.println("Client Disconnected!");
    }

    public static void Display(JFrame window, int oldPos)
    {
       if(board.BoardTiles[oldPos].tileType.equals("PERSON"))
       {
            window.getContentPane().getComponent(oldPos).setBackground(Color.ORANGE);
       }
       else
       {
        window.getContentPane().getComponent(oldPos).setBackground(Color.WHITE);
       }

       window.getContentPane().getComponent(player.currentTilePosition).setBackground(Color.RED);
    }

    //Moves around where the player is on the board
    public static void MovementLogic(String input, JFrame window)
    {
        int oldPos;

        switch(input){

        case("w") -> {
            if(player.rowPos != 0)
            {
                player.rowPos -= 1;
                //Current pos -= length of rows
                oldPos = player.currentTilePosition;
                player.currentTilePosition -= Rows + (Cols - Rows);
                System.out.println("row " + player.rowPos +" col " + player.colPos);
                Display(window,oldPos);
            }
            }

         case("a") -> {
             //window.getContentPane().setBackground(Color.RED);
             if(player.colPos != 0)
             {
                 player.colPos -= 1;
                 oldPos = player.currentTilePosition;
                 player.currentTilePosition -= 1;
                 System.out.println("row " + player.rowPos +" col " + player.colPos);
                 Display(window, oldPos);
             }
            }

         case("s") -> {
             if(player.rowPos != Rows - 1)
             {
                 player.rowPos += 1;
                 oldPos = player.currentTilePosition;
                 player.currentTilePosition += Rows + (Cols - Rows);
                 System.out.println("row " + player.rowPos +" col " + player.colPos);
                 Display(window,oldPos);
             }
            }

         case("d") -> {
             if(player.colPos != Cols - 1)
             {
                 player.colPos += 1;
                 oldPos = player.currentTilePosition;
                 player.currentTilePosition += 1;
                 System.out.println("row " + player.rowPos +" col " + player.colPos);
                 Display(window,oldPos);
             }
            }


        }
    }
}