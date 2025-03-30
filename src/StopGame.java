import java.util.Arrays;

import javax.swing.JPanel;

public class StopGame implements Runnable{

    public boolean[] GameOutcome ;
    public PopUpManager popUpManager ;
    public JPanel BoardContainer ;

    StopGame(boolean[] GameOutcome, PopUpManager popUpManager, JPanel BoardContainer) {
        this.GameOutcome = GameOutcome ;
        this.popUpManager = popUpManager ;
        this.BoardContainer = BoardContainer ;
    }


    public void run() {

        System.out.println("StopGame is running");

        while(true) {
            try {
                Thread.sleep(100) ;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            if (GameOutcome[0] && !GameOutcome[2]) {
                // Close any minigames in progress
                if(popUpManager.GetActive()) {
                    popUpManager.KillFrame();
                }

                BoardContainer.removeAll() ;
                BoardContainer.revalidate() ;
                BoardContainer.repaint();

                GameOutcome[2] = true ;

                System.out.println("Stopped Game");
            } 
        }
    }

}
