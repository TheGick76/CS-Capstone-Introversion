import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
public class Countdown implements Runnable {

    public int threadNum ;
    public int countdownAmount ;
    public JLabel timerDisplay;

    public Countdown(int i, int countdownAmount, JLabel timerDisplay) {
        this.threadNum = i ;
        this.countdownAmount = countdownAmount ;
        this.timerDisplay = timerDisplay;
    }

    public void run() {
        while (countdownAmount > 0) {
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

        timerDisplay.setText("Countdown has ended. ");
    }
}