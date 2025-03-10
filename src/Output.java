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

    //if client disconnected
    public static Boolean creationBool = true;

    //Score Display
    public static JLabel ScoreDisplay = new JLabel("Score: 0");

    //Energy,Score,CurrentTilePosition,Row Position, Coloumn Position
    public static Himothy player = new Himothy(1,0,0,0,0, ScoreDisplay);
    
    // Mini windows manager
    public static PopUpManager popUpManager = new PopUpManager(player);
    
    //Board Container
    public static JPanel BoardContainer = new JPanel();

    //Board initializer
    public static Board board = new Board(6,6,"GAME");

    //Change how these are assigned later
    public static int Rows = Board.Rows;
    public static int Cols = Board.Cols;

    //The label that will display the current time, will send to Timer class
   // public static JLabel TimerDisplay = new JLabel("Timer: ");
    public static JLabel CountdownDisplay = new JLabel("Countdown: 5:00");
    public static JProgressBar EnergyBar = new JProgressBar();
    


//Our main function
public static void main(String args[])
    {
        //Creates instance of Output, which is the window
        Output window = new Output();

        //Initialize display
        Display(window, 0);

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

        //Thread for timer
     //   Thread timerThread = new Thread(new Timer(0, TimerDisplay));
        Thread countdownThread = new Thread(new Countdown(0, 300, CountdownDisplay));
       // timerThread.start();
        countdownThread.start();

        Thread Energy = new Thread(new Energy(player, board, EnergyBar, ScoreDisplay)) ;

        //Calls the thread to begin the music minigame
        board.BoardTiles[20].beginMusic();

        Energy.start() ;

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
      //  TimerDisplay.setSize(100,100);
        //TimerDisplay.setBounds(10, 100,100,100);
       // statsPanel.add(TimerDisplay);

        CountdownDisplay.setSize(100,100);
        statsPanel.add(CountdownDisplay);

        EnergyBar.setMaximum(100);
        EnergyBar.setValue(100);
        EnergyBar.setString("Energy");
        EnergyBar.setStringPainted(true);

        EnergyBar.setSize(200,100);
       // EnergyBar.setBounds(200, 100, 100,100);

       //Buffers for display
       statsPanel.add(new JPanel());
       statsPanel.add(new JPanel());

        statsPanel.add(EnergyBar);

        statsPanel.add(ScoreDisplay);



        //Add tile panels
        //Initializes colors of the tiles, make seperate function?
        for(int i = 0; i < (Rows * Cols); i++)
        {
            /*
            JPanel temp = new JPanel();
            BoardContainer.add(temp, i);
            switch (board.BoardTiles[i].tileType) {
                case "PERSON" -> temp.setBackground(Color.ORANGE);
                case "CAT" -> temp.setBackground(Color.GREEN);
                case "POPUP" -> temp.setBackground(Color.cyan);
                default -> temp.setBackground(Color.WHITE);


            }
                */
            BoardContainer.add(board.BoardTiles[i],i);


        }
        //sets window 10px off the top right corner
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation(10, 10);
        //sets window visible
        setVisible(true);
    }
    
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
                    
                 if(popUpManager.GetActive())
                	 popUpManager.Input(outputString);
                 else
                    //Basic logic to prove inputs
                    switch(outputString)
                    {
                    	// Movement Function
                    	case "w" -> {MovementLogic(outputString, window);}
                    	case "a" -> {MovementLogic(outputString, window);}
                    	case "s" -> {MovementLogic(outputString, window);}
                    	case "d" -> {MovementLogic(outputString, window);}
                    	
                    	case "e" -> {SelectTile();}
                    	
                    	
                    	case "CLEAR" -> //If a button has been released
                			System.out.println(outputString);
                    }
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
            case "PERSON" -> board.BoardTiles[oldPos].backgroundSet("PERSON");
            case "POPUP" -> board.BoardTiles[oldPos].backgroundSet("POPUP");
            case "CAT" -> {
                //Tiles that change when player moves over it
                board.BoardTiles[oldPos].backgroundSet("EMPTY");
                board.BoardTiles[oldPos].tileType = "EMPTY";
            }
            case "MUSIC" -> board.BoardTiles[oldPos].backgroundSet("MUSIC");
            case "EMPTY" -> board.BoardTiles[oldPos].backgroundSet("EMPTY");
            default -> board.BoardTiles[oldPos].backgroundSet("");
        }
       //The Player
       board.BoardTiles[player.currentTilePosition].backgroundSet("PLAYER");
    }

    //Function for doing what the current tile action is
    //Probably where the most multithreading interactions will happen
    public static void CurrentTileAction()
    {
        if(board.BoardTiles[player.currentTilePosition].tileType.equals("CAT"))
        {
            // player.score += 1;
        }  
    }

    //Moves around where the player is on the board
    public static void MovementLogic(String input, JFrame window)
    {
        Display(window,player.MovementLogic(input, board));
        CurrentTileAction();
    }
    
    
    public static void SelectTile()
    {
        Tiles curTile = board.BoardTiles[player.currentTilePosition];
    	if(curTile.tileType.equals("POPUP"))
    		popUpManager.toggleNewFrame(curTile.PopupGame);
        else if(curTile.tileType.equals("MUSIC"))
        {
            if(curTile.musicalBox.countdownAmount <= 0)
            {
            curTile.beginMusic();
            }
            else
            curTile.musicTimeRestart();
        }
    }
}