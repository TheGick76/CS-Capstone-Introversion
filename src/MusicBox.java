import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
public class MusicBox implements Runnable {

    public int countdownAmount ;
    public int Song;
    public JProgressBar musicDisplay;
    boolean Stop = false;

    public MusicBox(int countdownAmount, JProgressBar musicDisplay, int Song) {
        this.countdownAmount = countdownAmount ;
        this.musicDisplay = musicDisplay;
        this.Song = Song;
    }

    public void refresh(int max)
    {
        countdownAmount = max;
        musicDisplay.setValue(max);
    }

    public void beginMusic()
    { 
        musicTimeRestart();
        Thread musicThread = new Thread(this);
        musicThread.start();
    }

    public void musicTimeRestart()
    {
        Stop = false;
        refresh(60000);
    }

    public void ChangeSong(int newSong)
    {
        Stop = true; 
        Song = newSong;
        try {
            Thread.sleep(10);
            Stop = false;
            Thread musicThread = new Thread(this);
            musicThread.start();
        } catch (Exception e) {
        }
    }

    public void run() {
            
        try {
            //File stuff
            String path;
            File musicPath;
            switch(Song)
            {
                case 1 -> {path = "src\\Assets\\Boogie.wav";
                musicPath = new File(path);
            }
                case 2 -> {path = "src\\Assets\\the-messenger.wav";
                musicPath = new File(path);
            }
                case 3 -> {path = "src\\Assets\\Level2.wav";
                musicPath = new File(path);
            }
            case 4 -> {path = "src\\Assets\\Winter4.wav";
                musicPath = new File(path);
            }
                default -> {path = "src\\Assets\\Boogie.wav";
                musicPath = new File(path);
            }
            }
            

            if(musicPath.exists())
            {
            //Audio stream
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            //Start music
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();

                while (countdownAmount > 0 && !Stop) {

                    try {
                        Thread.sleep(1) ;
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                    countdownAmount = countdownAmount - 1;
                    musicDisplay.setValue(countdownAmount);
                }
        
                if(countdownAmount <= 0 || Stop)
                {
                    clip.stop();
                    clip.close();
                    audioInput.close();
                }
                try {
                    Thread.sleep(10) ;
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                return;
            }
        
        } catch (Exception e) {
            System.out.println("dumb dumb no music here");
            System.out.println("Song: "+ Song);
        }
        
    }
}