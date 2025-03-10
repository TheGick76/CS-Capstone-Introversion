
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.*;

public class MusicPopup{
       JFrame newFrame = new JFrame("Stereo");
       JLabel credits = new JLabel("");
       JLabel curSong= new JLabel("Boogie");
       JLabel quitStr = new JLabel("Quit: E");
       JLabel refreshStr = new JLabel("Refresh: Space");
       MusicBox Stereo;
       //Board board;
       int songNum = 4;

    JFrame Start()
 	{
        newFrame.setLayout(new FlowLayout());
        newFrame.setSize(500, 500);
        newFrame.setAutoRequestFocus(false);
        newFrame.setFocusableWindowState(false);
        newFrame.add(quitStr);
        newFrame.add(refreshStr);

        String creditString = getMyStrings();

        credits.setText(creditString);
        newFrame.add(credits);
        newFrame.add(curSong);

        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
     //   newFrame.setLocation((int)(dim.width),(int)(dim.height));
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     	return newFrame;
 	}

     void kill()
 	{		
        //newFrame.setVisible(false);
        newFrame.dispose();	
 	}

    void GetInput(JFrame CurrentFrame,String input)
 	{
         switch(input)
         {
            case "q" -> {
                System.out.println("AM I EVEN HERE");
                songNum++;
                if(songNum > 4)
                {
                    songNum = 1;
                }
                Stereo.ChangeSong(songNum);
                getMyStrings();
                
            }
            case " " -> {
                if(Stereo.countdownAmount <= 0)
                {
                    board.BoardTiles[20].beginMusic();
                }
                else
                {
                    board.BoardTiles[20].musicTimeRestart();
                }
            }
            case "CLEAR" -> {CurrentFrame.getContentPane().setBackground(Color.WHITE);}
         }	
 	}

    String getMyStrings()
    {
        String creditString;
        switch(Stereo.Song)
        {
            case 1 -> {creditString = "Song: Zachz, Фрози, Joyful - Boogie\r\n" + //
                                "Music provided by NoCopyrightSounds\r\n" + //
                                "Free Download/Stream: http://ncs.io/Boogie\r\n" + //
                                "Watch: http://ncs.lnk.to/BoogieAT/youtube";
                        credits.setText(creditString);
                        curSong.setText("Boogie");}
            case 2 -> {creditString = "Song: Silent Partner - The Messenger";
            credits.setText(creditString);
            curSong.setText("The Messenger");}
            case 3 -> {creditString = "Song: Juhani Junkala - Chiptune Level 2";
            credits.setText(creditString);
            curSong.setText("Chiptune");}
            case 4 -> {creditString = "Song Antonio Vivaldi - The Four Seasons (Winter)";
            credits.setText(creditString);
            curSong.setText("Winter");}
            default -> creditString = "What";
        }
        return creditString;
    }
}
