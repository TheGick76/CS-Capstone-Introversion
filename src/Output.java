//Importing what we need for creating a window
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

//Class of Output whichextends the content of JFrame
class Output extends JFrame{

    //Create a socket that will then be connected to server
    private static Socket receiveingSocket = null;

    //Data Stream that will take in the data from a selected source
    private static DataInputStream in = null;

    //The server
    private static ServerSocket ss = null;

    //Will be changed to what key / string was sent from inputs
    private static String outputString = "";

    //if client disconnected
    private static Boolean creationBool = true;

    //Score Display
    private final static JLabel ScoreDisplay = new JLabel("Score: 0");

    //Energy,Score,CurrentTilePosition,Row Position, Coloumn Position
    private final static Himothy player = new Himothy(1,0,0,0,0, ScoreDisplay);
    

    //Stats Labels
    private final static JLabel label1 = new JLabel();
    private final static JLabel label2 = new JLabel();
    private static JLabel label3 = new JLabel();

    //Board Container
    private final static JPanel BoardContainer = new JPanel();

    //Board initializer
    private final static Board board = new Board(6,6,"GAME");

    //Change how these are assigned later
    private static int Rows = Board.Rows;
    private static int Cols = Board.Cols; 
    // Mini windows manager
    private final static PopUpManager popUpManager = new PopUpManager(player);

    // The label that will display the current time, will send to Timer class
    // public static JLabel TimerDisplay = new JLabel("Timer: ");
    private final static JLabel CountdownDisplay = new JLabel("Countdown: 5:00");
    private final static JProgressBar EnergyBar = new JProgressBar();
   
    private static Output window;
    private static boolean FoundInput;

    static boolean MovementLock;
    static boolean PopupLock = false;

    // Booleans to check if the game ended and if the player won
    // GameOutcome[0] is if game ended
    // GameOutcome[1] is if player won
    // GameOutcome[2] is if screen has updated to show game ended

    private static boolean[] GameOutcome = {false, false , false} ;

    private final static JLabel GameEnd = new JLabel() ;


 
    


//Our main function
public static void main(String args[])
    {
        //Creates instance of Output, which is the window
        window = new Output();


        //Try to Connect
        do { 
            //This loop actually wont constantly go while we are connected or searching,
            //it is only allowed to go when Connect(); finishes
            Connect(window, creationBool);
        } while (!FoundInput); 
       
    	for(Tiles T : Board.PersonTiles) {T.StartCountdown(GameOutcome);}
    	
        //Initialize display
        Display(window, 0);
        //Calls the thread to begin the music minigame
        //Change to get what tile is music tile dynamically
    	for(Tiles T : board.BoardTiles)
    	{
    		if(T.tileType.compareTo("MUSIC")==0)
    			T.musicalBox.beginMusic();
    	}
        //Try to Connect
        do { 
            //NEED TO MULTITHREAD
            //Constatnly reads input from the inputs proccess
            ReadInput(window);
        } while (FoundInput); 

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

        // Thread for timer
        // Thread timerThread = new Thread(new Timer(0, TimerDisplay));
        Thread countdownThread = new Thread(new Countdown(1, 300, CountdownDisplay, GameOutcome, GameEnd));
        // timerThread.start();
        countdownThread.start();

        Thread Energy = new Thread(new Energy(player, board, EnergyBar, ScoreDisplay, GameOutcome, GameEnd)) ;

        Energy.start() ;
        
        Thread StopGame = new Thread(new StopGame(GameOutcome, popUpManager, BoardContainer)) ;

        StopGame.start() ;
        
        for(int i = 0; i < (Rows * Cols); i++)
        {
            BoardContainer.add(board.BoardTiles[i],i);
        }

            //Confirming client has connected
            System.out.println("Client joined");

            System.out.println("row " + player.rowPos +" col " + player.colPos);

            //Will direct flow of inputs from the server's spcket
            in = new DataInputStream(new BufferedInputStream(receiveingSocket.getInputStream()));
            FoundInput = true;
        }
        //Error printing
        catch(IOException i)
        {
            System.out.println(i);
        }
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
        BoardContainer.setBackground(Color.BLACK);
        add(BoardContainer);

        JPanel statsPanel = new JPanel(new FlowLayout(1,400,10));
        statsPanel.setPreferredSize(new Dimension(300,500));
        add(statsPanel);

        pack();

        // Timer display
        // TimerDisplay.setSize(100,100);
        //TimerDisplay.setBounds(10, 100,100,100);
        // statsPanel.add(TimerDisplay);

        CountdownDisplay.setSize(100,100);
        JPanel countdownpanel = new JPanel();
        countdownpanel.add(CountdownDisplay);
        statsPanel.add(countdownpanel);

        EnergyBar.setMaximum(100);
        EnergyBar.setValue(100);
        EnergyBar.setString("Energy");
        EnergyBar.setStringPainted(true);

        EnergyBar.setSize(200,100);
        // EnergyBar.setBounds(200, 100, 100,100);

        //Buffers for display
        // statsPanel.add(new JPanel());
        //statsPanel.add(new JPanel());

        JPanel energypanel = new JPanel();
        energypanel.add(EnergyBar);
        statsPanel.add(energypanel);

        JPanel scorepanel = new JPanel();
        scorepanel.add(ScoreDisplay);
        scorepanel.add(GameEnd);
        statsPanel.add(scorepanel);

        statsPanel.add(label3);
        statsPanel.add(label2);
        statsPanel.add(label1);
             
        //sets window 10px off the top right corner
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation(900, 450);
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
            	if(FoundInput && in != null)
                    outputString = in.readUTF();
                    
            	CheckPeopleMove();
            	
                // Stops inputs from affecting the board if the game is over
                if(GameOutcome[0]) { 
                    // Close any minigames in progress
                	// Restarts the game when player presses e
                    if(outputString.compareTo("e") == 0){
                        System.out.println("Restarting Game...");
                        RestartGame() ;
                    }
                }
                
                //When calling popup manager, if the game is complete the popup disappers if a timer is connected
                else if(popUpManager.GetActive() && !PopupLock)
                {                	
                	if(popUpManager.Input(outputString))
                    {
                		board.BoardTiles[player.currentTilePosition].StartCountdown(GameOutcome);
                        PopupLock = true;
                    }
                }

                else
                    //Basic logic to prove inputs
                    switch(outputString)
                    {
                    	// Movement Function
                    	case "w" -> {MovementLogic(outputString, window);}
                    	case "a" -> {MovementLogic(outputString, window);}
                    	case "s" -> {MovementLogic(outputString, window);}
                    	case "d" -> {MovementLogic(outputString, window);}
                    	
                    	case "e" -> {SelectTile();
                            PopupLock = true;
                            
                        }

                        case " " -> {CurrentTileAction(outputString);}
                        case "q" -> {CurrentTileAction(outputString);}
                        case "WUP" -> MovementLock = false;
                        case "AUP" -> MovementLock = false;
                        case "SUP" -> MovementLock = false;
                        case "DUP" -> MovementLock = false;
                        case "EUP" -> PopupLock = false;
                    	

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
                    System.exit(0);
                }
        }
        outputString = "";
        System.out.println("Client Disconnected!");
    }

    //Function updates visible tiles on movement
    public static void Display(JFrame window, int oldPos)
    {
    	board.PlayerMove(oldPos, player.currentTilePosition);
        //Add tile panels
        //Initializes colors of the tiles, make seperate function?
    	
    	for(Tiles T : board.BoardTiles)
    	{
            //tiles that are NOT the player
            switch (T.tileType) {
                case "PERSON" -> board.BoardTiles[T.tileNumber].backgroundSet("PERSON");
                case "MUSIC" -> board.BoardTiles[T.tileNumber].backgroundSet("MUSIC");
                case "EMPTY" -> board.BoardTiles[T.tileNumber].backgroundSet("EMPTY");
                case "POPUP" ->board.BoardTiles[T.tileNumber].backgroundSet(!board.BoardTiles[T.tileNumber].Count.Play ? "POPUP" : "EMPTY");
                case "CAT" -> {
                    //Tiles that change when player moves over it
                	if(oldPos == T.tileNumber)
                	{               		
                    board.BoardTiles[T.tileNumber].backgroundSet("EMPTY");
                    board.BoardTiles[T.tileNumber].tileType = "EMPTY";
                	}
                }
                default -> board.BoardTiles[T.tileNumber].backgroundSet("");
            }
            board.BoardTiles[player.currentTilePosition].backgroundSet("PLAYER");
    	}
    
    }

    //Function for doing what the current tile action(s) is
    public static void CurrentTileAction(String input)
    {
        Tiles curTile =board.BoardTiles[player.currentTilePosition];
        switch (curTile.tileType) {
            case "MUSIC" -> {
                try {
                    Thread.sleep(5);
                    curTile.MusicInput(input);
                }
                catch(Exception e) {
                }
                label3.setText(curTile.musicalBox.Controls);
                label2.setText(curTile.musicalBox.curSong);
                label1.setText(curTile.musicalBox.credits);
            }
            case "PERSON" -> System.out.println(curTile.Count.Play);
            case "EMPTY" -> {
                label3.setText("");
                label2.setText("");
                label1.setText(""); 
            }
        }
    }

    //Moves around where the player is on the board
    public static void MovementLogic(String input, JFrame window)
    {
        if(!MovementLock)
        {
        Display(window,player.MovementLogic(input, board));
        CurrentTileAction(input);
        MovementLock = true;
        }
    }
    
    
    public static void SelectTile()
    {
        Tiles curTile = board.BoardTiles[player.currentTilePosition];
    	if(curTile.tileType.equals("POPUP") && !curTile.Count.Play && !PopupLock)
    		{popUpManager.toggleNewFrame(curTile.PopupGame);
                MovementLock = true;
                PopupLock = true;
            }
    }
    
    public static void CheckPeopleMove()
    {
    	for (int i = 0; i < board.PersonTiles.size();i++) {
    		Tiles T = board.PersonTiles.get(i);
            if(!T.Count.Play)
            {
            	board.SwapTiles(board.PersonTiles.indexOf(T) , player.currentTilePosition , GameOutcome);
            	Display(window,player.currentTilePosition);
            	T.StartCountdown(GameOutcome);
            }
        }
    }
    
    public static void RestartGame()
    {

        // Reset GameOutcome
        GameOutcome[0] = false ;
        GameOutcome[1] = false ;
        GameOutcome[2] = false ;
        GameEnd.setText("");

        // Reset himothy, score, and energy

        int oldPos = player.currentTilePosition ;

        player.rowPos = 0 ;
        player.colPos = 0 ;
        player.currentTilePosition = 0 ;
        player.score = 0 ;
        player.UpdateScore(0);
        EnergyBar.setValue(100);

        // Reset the countdown and energy threads
        Thread countdownThread = new Thread(new Countdown(1, 300, CountdownDisplay, GameOutcome, GameEnd));
        countdownThread.start();

        Thread Energy = new Thread(new Energy(player, board, EnergyBar, ScoreDisplay, GameOutcome, GameEnd)) ;
        Energy.start() ;

        // Reset board and displays everything

        BoardContainer.removeAll();
        board.PersonTiles.clear();
        board.EmptyTiles.clear();

        board.BoardTiles = new Tiles[Rows * Cols] ;
        board.GameBoard();

        for(int i = 0; i < (Rows * Cols); i++)
        {
            BoardContainer.add(board.BoardTiles[i],i);
        }

        BoardContainer.revalidate();
        BoardContainer.repaint();
        board.BoardTiles[20].musicalBox.beginMusic();

        for(Tiles T : Board.PersonTiles) {T.StartCountdown(GameOutcome);}
        
        Display(window, oldPos) ;

    }

}