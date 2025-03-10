
public class Board {

    public static int Rows;
    public static int Cols;
    public static Tiles[] BoardTiles = new Tiles[Rows * Cols];

    Board(int Rows, int Cols , String BoardName)
    {
        Board.Rows = Rows;
        Board.Cols = Cols;
        BoardTiles = new Tiles[Rows * Cols];
        switch (BoardName) 
        {
        case "GAME" -> GameBoard();
        case "TEST" -> TestBoard();
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
         	case 3 -> BoardTiles[i] = new Tiles(TileCount, "CAT");
         	case 6 -> BoardTiles[i] = new Tiles(TileCount, "PERSON");
         	case 12 -> {BoardTiles[i] = new Tiles(TileCount, "POPUP");BoardTiles[i].PopupGame = 1;}
         	case 16 -> BoardTiles[i] = new Tiles(TileCount, "POPUP");
         	case 20 -> {BoardTiles[i] = new Tiles(TileCount, "MUSIC"); BoardTiles[i].PopupGame = 2;}
         	default ->  BoardTiles[i] = new Tiles(TileCount, "EMPTY");
         	}
                TileCount++;
       }
    }
    
    void TestBoard()
    {
        int TileCount = 0;
        //Creats an array of tile objects
        //Initalizes what tiles are which
        for(int i = 0; i < (Rows * Cols) ; i++) 
        {
            if(i == 1)
            {
            BoardTiles[2] = new Tiles(TileCount, "CAT");
            }

                TileCount++;
       }
    }
}

