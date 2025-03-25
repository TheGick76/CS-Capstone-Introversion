import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.plaf.TreeUI;
public class Countdown implements Runnable {

    public int threadNum ;
    public int countdownAmount ;
    public JLabel timerDisplay;
    public boolean[] GameOutcome ;
    public JLabel GameEnd ;

    public Countdown(int i, int countdownAmount, JLabel timerDisplay, boolean[] GameOutcome, JLabel GameEnd) {
        this.threadNum = i ;
        this.countdownAmount = countdownAmount ;
        this.timerDisplay = timerDisplay;
        this.GameOutcome = GameOutcome ;
        this.GameEnd = GameEnd ;
    }

    public void run() {
        // Keeps going until countdown reaches 0 or game is over
        while (countdownAmount > 0 && !GameOutcome[0]) {
            //System.out.println("Thread: " + threadNum + " Time: " + i) ;
            try {
                Thread.sleep(1000) ;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            countdownAmount-- ;
            int seconds = countdownAmount % 60 ;
            int minutes = (countdownAmount % 3600) / 60 ;
            int hours = countdownAmount / 3600 ;
            LocalTime time = LocalTime.of(hours ,minutes, seconds);
            String timeString = time.format(DateTimeFormatter.ofPattern("mm:ss"));
            timerDisplay.setText("Countdown: " + timeString);
        }

        // Runs if countdown reaches 0, meaning game is over and player won
        if (!GameOutcome[0]) {
            GameOutcome[0] = true ;
            GameOutcome[1] = true ;
            timerDisplay.setText("Countdown has ended. ");
            GameEnd.setText("You Win!");
        }
    }
}