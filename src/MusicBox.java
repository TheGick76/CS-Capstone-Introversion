import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
public class MusicBox implements Runnable {

    public int countdownAmount ;
    public JProgressBar musicDisplay;

    public MusicBox(int countdownAmount, JProgressBar musicDisplay) {
        this.countdownAmount = countdownAmount ;
        this.musicDisplay = musicDisplay;
    }

    public void refresh(int max)
    {
        countdownAmount = max;
        musicDisplay.setValue(max);
    }

    public void run() {
            
        try {
            //File stuff
            String path = "src\\Assets\\Boogie.wav";
            File musicPath = new File(path);

            if(musicPath.exists())
            {
            //Audio stream
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            //Start music
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();

                while (countdownAmount > 0) {

                    try {
                        Thread.sleep(1000) ;
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                    countdownAmount = countdownAmount - 1;
                    musicDisplay.setValue(countdownAmount);
                }
        
                if(countdownAmount <= 0)
                {
                    clip.stop();
                    clip.close();
                }
                return;
            }
        
        } catch (Exception e) {
            System.out.println("dumb dumb no music here");
        }
        
    }
}