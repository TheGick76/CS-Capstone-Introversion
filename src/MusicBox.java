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
    public String credits;
    public String curSong;
    public String Controls = "Refresh: 'Space', Next Song: 'Q'";

    public MusicBox(int countdownAmount, JProgressBar musicDisplay, int Song, String Credits, String Cursong) {
        this.countdownAmount = countdownAmount ;
        this.musicDisplay = musicDisplay;
        this.Song = Song;
        this.credits = Credits;
        this.curSong = Cursong;
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
        getMyStrings();
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
            Thread.sleep(5);
            Stop = false;
            Thread musicThread = new Thread(this);
            musicThread.start();
        } catch (Exception e) {
        }
    }

    void getMyStrings()
    {
        String creditString;
        switch(Song)
        {
            case 1 -> {creditString = "<HTML>Song: Zachz, Фрози, Joyful - Boogie <br>" + //
                                "Music provided by NoCopyrightSounds<br>" + //
                                "Free Download/Stream: http://ncs.io/Boogie<br>" + //
                                "Watch: http://ncs.lnk.to/BoogieAT/youtube<HTML/>";
                        credits = creditString;
                        curSong="Boogie";}
            case 2 -> {creditString = "Song: Silent Partner - The Messenger";
            credits=creditString;
            curSong="The Messenger";}
            case 3 -> {creditString = "Song: Juhani Junkala - Chiptune Level 2";
            credits=creditString;
            curSong="Chiptune";}
            case 4 -> {creditString = "Song Antonio Vivaldi - The Four Seasons (Winter)";
            credits=creditString;
            curSong="Winter";}
            default -> creditString = "What";
        }
    }

    void GetInput(String input)
    {
        switch(input)
        {
           case "q" -> {
               Song++;
               if(Song > 4)
               {
                   Song = 1;
               }
               ChangeSong(Song);
               getMyStrings();
               
           }
           case " " -> {
               if(countdownAmount <= 0)
               {
                   beginMusic();
               }
               else
               {
                   musicTimeRestart();
               }
           }
           case "CLEAR" -> {}
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