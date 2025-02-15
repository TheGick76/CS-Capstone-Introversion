public class Board {

    public static int Rows = 4;
    public static int Cols = 4;
    

    public static void main(String[] args) {

        int TileCount = 1;

        Tiles[][] BoardTiles = new Tiles[2][2];
        //Creats an array of tile objects
        for(int i = 0; i < 2 ; i++) {
            for(int j = 0; j< 2; j++){
                if(i != 1 && j != 1)
                BoardTiles[i][j] = new Tiles(TileCount, "EMPTY");
                else
                BoardTiles[i][j] = new Tiles(TileCount, "CAT");

                TileCount++;
        }
    }
    System.out.println(BoardTiles[0][0].tileType);
    System.out.println(BoardTiles[0][0].tileNumber);
    System.out.println(BoardTiles[1][1].tileType);
    System.out.println(BoardTiles[1][1].tileNumber);

    }
}

