import javax.swing.*;
public class Timer implements Runnable {

    public int threadNum ;
    public JLabel timerDisplay;

    public Timer(int i, JLabel timerDisplay) {
        this.threadNum = i ;
        this.timerDisplay = timerDisplay;
    }

    public void run() {
        int i = 0 ;
        while (true) {
            System.out.println("Thread: " + threadNum + " Time: " + i) ;
            try {
                Thread.sleep(1000) ;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            i++ ;
            timerDisplay.setText("Timer: " + i);
        }
    }
}