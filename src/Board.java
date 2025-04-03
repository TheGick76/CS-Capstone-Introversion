
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;


public class Board {

    public static int Rows;
    public static int Cols;
    public static Tiles[] BoardTiles = new Tiles[Rows * Cols];
    public static ArrayList<Tiles> EmptyTiles = new ArrayList<>();
    public static ArrayList<Tiles> PersonTiles = new ArrayList<>();

    public static JLabel label1;
    public static JLabel label2;

    Board(int Rows, int Cols , String BoardName)
    {
        Board.Rows = Rows;
        Board.Cols = Cols;
        Board.label1 = label1;
        Board.label2 = label2;

        BoardTiles = new Tiles[Rows * Cols];
        switch (BoardName) 
        {
        case "GAME" -> GameBoard();
        }

    }
    
    void GameBoard()
    {
        int TileCount = 0;
        //Creats an array of tile objects
        //Initalizes what tiles are which
        for(int i = 0; i < (Rows * Cols) ; i++) 
        {
        	switch(i)
         	{
         	case 0 -> BoardTiles[i] = new Tiles(TileCount, "PLAYER");
         	case 3 -> BoardTiles[i] = new Tiles(TileCount, "CAT");
         	case 6 ->BoardTiles[i] = new Tiles(TileCount, "PERSON" , 5);
         	case 12 -> {BoardTiles[i] = new Tiles(TileCount, "POPUP" , 4);
         				BoardTiles[i].PopupGame = 1;}
         	case 16 -> {BoardTiles[i] = new Tiles(TileCount, "POPUP");
         				BoardTiles[i].PopupGame = 2;}
         /*	case 20 -> {BoardTiles[i] = new Tiles(TileCount, "MUSIC");
				            BoardTiles[i].Music();
				            BoardTiles[i].credits = "";
				            BoardTiles[i].curSong = "";}
		*/
         	case 22 -> BoardTiles[i] = new Tiles(TileCount, "PERSON" , 3);
            case 25 ->{BoardTiles[i] = new Tiles(TileCount, "POPUP");
                	   BoardTiles[i].PopupGame = 3;}
         	default ->  {
         		BoardTiles[i] = new Tiles(TileCount, "EMPTY");
         		EmptyTiles.add(BoardTiles[i]);
         				}
         	}
        	if(Board.BoardTiles[i].tileType.equals("PERSON") && Board.BoardTiles[i].Timer > 0)
        		PersonTiles.add(BoardTiles[i]);
                TileCount++;
       }
    }    
    
    void PlayerMove(int oldPos,int newPos)
    {
      if(oldPos != newPos)
      {
    	  if(BoardTiles[newPos].tileType.equals("EMPTY"))
    	  {
    		  EmptyTiles.remove(BoardTiles[newPos]);
    		  BoardTiles[newPos].tileType = "PLAYER";
    	  }
    	  if(BoardTiles[oldPos].tileType.equals("PLAYER"))
    	  {
    		  EmptyTiles.add(BoardTiles[oldPos]);
    		  BoardTiles[oldPos].tileType = "EMPTY";
    	  } 	  
      }
    }
    
    // Swapping Person Tile and Empty Tiles
    void SwapTiles(int PersonPos , int playerPos , boolean[] GameOutcome)
    {
    	// Removes Tile From Person List
    	int PersonTileNum = PersonTiles.get(PersonPos).tileNumber;
    	PersonTiles.remove(PersonTiles.get(PersonPos));
    	   	
    	// Removes Tile From Empty List
    	Tiles NewPlace = EmptyTiles.get(new Random().nextInt(EmptyTiles.size()));
    	int NewPlaceNum = NewPlace.tileNumber;
    	EmptyTiles.remove(NewPlace); 
    	
    	//setting Paramters for new tiles
    	BoardTiles[NewPlaceNum].tileType = "PERSON";
    	BoardTiles[NewPlaceNum].Timer = BoardTiles[PersonTileNum].Timer;
    	BoardTiles[NewPlaceNum].StartCountdown(GameOutcome);
    	PersonTiles.add(BoardTiles[NewPlaceNum]);
    	
    	//Setting Paramater for the old tile
    	BoardTiles[PersonTileNum].tileType = PersonTileNum == playerPos ? "PLAYER" : "EMPTY";
    	BoardTiles[PersonTileNum].Timer = 0;

    	// if Empty add to the Empty List
    	if(BoardTiles[PersonTileNum].tileType.compareTo("EMPTY") == 0)
    	EmptyTiles.add(BoardTiles[PersonTileNum]);
    }
}

