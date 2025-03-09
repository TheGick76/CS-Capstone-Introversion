
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
            if(i == 3)
            {
            BoardTiles[i] = new Tiles(TileCount, "CAT");
            }
            else if(i == 6)
            {BoardTiles[i] = new Tiles(TileCount, "PERSON");}
            else if(i == 12)
            {BoardTiles[i] = new Tiles(TileCount, "POPUP");}
            else if(i == 20)
            {BoardTiles[i] = new Tiles(TileCount, "MUSIC");}
            else
            {BoardTiles[i] = new Tiles(TileCount, "EMPTY");}
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

