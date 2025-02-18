public class Board {

    public static int Rows = 4;
    public static int Cols = 4;
    public static Tiles[] BoardTiles = new Tiles[Rows * Cols];

    Board(int Rows, int Cols)
    {
        Board.Rows = Rows;
        Board.Cols = Cols;

        BoardTiles = new Tiles[Rows * Cols];

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
            else
            {BoardTiles[i] = new Tiles(TileCount, "EMPTY");}
                TileCount++;
       }
    System.out.println(BoardTiles[3].tileType);
    System.out.println(BoardTiles[3].tileNumber);
    System.out.println(BoardTiles[6].tileType);
    System.out.println(BoardTiles[6].tileNumber);
    }

}

