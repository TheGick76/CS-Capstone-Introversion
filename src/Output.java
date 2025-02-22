//Importing what we need for creating a window
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

//Class of Output whichextends the content of JFrame
class Output extends JFrame{

    //Go through and reprivate all these things, they shouldn't be public
    //Id do it but im so lazy - Josh

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

    //Board Container
    public static JPanel BoardContainer = new JPanel();

    //Board initializer
    public static Board board = new Board(4,4);

    //Change how these are assigned later
    public static int Rows = Board.Rows;
    public static int Cols = Board.Cols;

    //The label that will display the current time, will send to Timer class
    public static JLabel TimerDisplay = new JLabel("Timer: ");
    public static JLabel CountdownDisplay = new JLabel("Countdown: ");
    


//Our main function
public static void main(String args[])
    {
        //Creates instance of Output, which is the window
        Output window = new Output();

        //Initialize display
        Display(window, 0);

        //Thread for timer
        Thread timerThread = new Thread(new Timer(0, TimerDisplay));
        Thread countdownThread = new Thread(new Countdown(0, 300, CountdownDisplay));
        timerThread.start();
        countdownThread.start();

       //Try to Connect
       do { 
        //This loop actually wont constantly go while we are connected or searching,
        //it is only allowed to go when Connect(); finishes
           Connect(window, creationBool);
       } while (true); 

    }

    //Attempt to connect to client
    //This function is called once on start and then again on disconnections due to loop in main
    public static void Connect(JFrame window, Boolean serverCreate)
    {
        //Try to create server
        try
        {
            //If its the first time the server is being created
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
        //Hieght 539 becuase thats the size that does not cut off the board.... for some reason
        setSize(800,539);
        //set layoutmananager
        setLayout(new FlowLayout(FlowLayout.LEADING));
        //setLayout(null);

        //Boardcontainer initializization
        BoardContainer.setPreferredSize(new Dimension(500,500));
        BoardContainer.setLayout(new GridLayout(Rows,Cols));
        add(BoardContainer);

        JPanel statsPanel = new JPanel();
        statsPanel.setPreferredSize(new Dimension(300,500));
        add(statsPanel);

        pack();

        //Timer display
        TimerDisplay.setSize(100,100);
        //TimerDisplay.setBounds(10, 100,100,100);
        statsPanel.add(TimerDisplay);

        CountdownDisplay.setSize(100,100);
        statsPanel.add(CountdownDisplay);

        JProgressBar EnergyBar = new JProgressBar();
        EnergyBar.setMaximum(100);
        EnergyBar.setValue(100);
        EnergyBar.setString("Energy");
        EnergyBar.setStringPainted(true);

        EnergyBar.setSize(200,100);
       // EnergyBar.setBounds(200, 100, 100,100);

       statsPanel.add(new JPanel());
       statsPanel.add(new JPanel());

        statsPanel.add(EnergyBar);

        //Add tile panels
        //Initializes colors of the tiles, make seperate function?
        for(int i = 0; i < (Rows * Cols); i++)
        {
            JPanel temp = new JPanel();
            BoardContainer.add(temp, i);
            switch (board.BoardTiles[i].tileType) {
                case "PERSON" -> temp.setBackground(Color.ORANGE);
                case "CAT" -> temp.setBackground(Color.GREEN);
                default -> temp.setBackground(Color.WHITE);
            }

        }
        //sets window 10px off the top right corner
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation(dim.width - getSize().width - 10, 10);
        //sets window visible
        setVisible(true);
    }
    
    //Make multithread later
    //Read inputs from client
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
                               //Do correct logic
                                MovementLogic(outputString, window);
                                System.out.println(outputString);
                            }
                    }
                        
                    	case "a" -> {
                            //To make sure we don't over take inputs, make sure we only take the input again when released
                            if(LastInput.compareTo("a") !=0)
                            {
                                MovementLogic(outputString, window);
                                System.out.println(outputString);
                            }
                    }
                        
                    	case "s" -> {
                            //To make sure we don't over take inputs, make sure we only take the input again when released
                            if(LastInput.compareTo("s") !=0)
                            {
                                MovementLogic(outputString, window);
                                System.out.println(outputString);
                            }
                    }
                        
                    	case "d" -> {
                            //To make sure we don't over take inputs, make sure we only take the input again when released
                            if(LastInput.compareTo("d") !=0)
                            {
                                MovementLogic(outputString, window);
                                System.out.println(outputString);
                            }
                    }
                    	
                    	case "CLEAR" -> //If a button has been released
                			System.out.println(outputString);
                    }

                 //Store what the last input was to make sure we dont over take
                  LastInput = outputString;
                }
                //If we couldn't read from sockets print error
                catch(IOException i)
                {
                    System.out.println(i);
                    System.out.println("Client Disconnected!");
                    outputString = "QUIT";
                    //Clear our data input stream
                    in = null;
                    //clear our recieving socket
                    receiveingSocket = null;
                }
        }
        outputString = "";
        System.out.println("Client Disconnected!");
    }

    //Function updates visible tiles on movement
    public static void Display(JFrame window, int oldPos)
    {
        //tiles that are NOT the player
        switch (board.BoardTiles[oldPos].tileType) {
            case "PERSON" -> BoardContainer.getComponent(oldPos).setBackground(Color.ORANGE);
            case "CAT" -> {
                BoardContainer.getComponent(oldPos).setBackground(Color.WHITE);
                board.BoardTiles[oldPos].tileType = "EMPTY";
            }
            default -> BoardContainer.getComponent(oldPos).setBackground(Color.WHITE);
        }
       //The Player
       BoardContainer.getComponent(player.currentTilePosition).setBackground(Color.RED);
    }

    //Function for doing what the current tile action is
    //Probably where the most multithreading interactions will happen
    public static void CurrentTileAction()
    {
        if(board.BoardTiles[player.currentTilePosition].tileType.equals("CAT"))
        {
             player.score += 1;
        }  
    }

    //Moves around where the player is on the board
    public static void MovementLogic(String input, JFrame window)
    {
        int oldPos;

        //Take input
        switch(input){
            //Input given was "w", we want our player to move up a row
        case("w") -> {
            //If we are not at the top row
            if(player.rowPos != 0)
            {
                //Top row 0, last row n. move to row 1-n 
                player.rowPos -= 1;
                //Save the tile position for graphical updates and such
                oldPos = player.currentTilePosition;
                //change the offical tile position of the player
                //Current position - how many rows there are + offset of coloumns (In a 4 by 4 square you just subtract 4)
                player.currentTilePosition -= Rows + (Cols - Rows);
                //Print statement for testing, feel free to delete later
                System.out.println("row " + player.rowPos +" col " + player.colPos);
                //Send a refrence to our window so we can visually update the tile we moved to and mvoed away from
                Display(window,oldPos);
                //Do the current tile action
                CurrentTileAction();
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
                 CurrentTileAction();
             }
            }

         case("s") -> {
             if(player.rowPos != Rows - 1)
             {
                 player.rowPos += 1;
                 oldPos = player.currentTilePosition;
                 player.currentTilePosition += Rows + (Cols - Rows);
                 System.out.println("row " + player.rowPos +" col " + player.colPos);
                 System.out.println("Score: " + player.score);
                 Display(window,oldPos);
                 CurrentTileAction();
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
                 CurrentTileAction();
             }
            }


        }
    }
}