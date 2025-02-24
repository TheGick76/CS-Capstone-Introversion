
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class PopUpManager
{
		 	
        public static void toggleNewFrame() 
        {
        /*	
        	Board board = new Board(2,2);
        	int Rows = Board.Rows;
        	int Cols = Board.Cols;
        	
            for(int i = 0; i < (Rows * Cols); i++)
            {
                JPanel temp = new JPanel();
                BoardContainer.add(temp, i);
                switch (board.BoardTiles[i].tileType) {
                    case "PERSON" -> temp.setBackground(Color.ORANGE);
                    default -> temp.setBackground(Color.WHITE);
                }

            }
        */	
        	
        	JFrame newFrame = new JFrame("New Frame");
        	newFrame.setSize(300, 300);
        	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	newFrame.setVisible(true);

        }
}