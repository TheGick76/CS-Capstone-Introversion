import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Tiles extends JPanel{

    //What tile number is it
    int tileNumber;
    JProgressBar musicDuration = new JProgressBar();
    int musicTime = 60000;
    int musicSong = 4;
    int PopupGame;

    //"Empty" "People" "Cat" "Ect."
    String tileType;

    //Constructor
    Tiles(int tileNum, String tileType)
    {
        this.tileNumber = tileNum;
        this.tileType = tileType;
        specificTileGoodies(tileType);
        backgroundSet(tileType);
        assignGame(tileType);
    }

    void specificTileGoodies(String s)
    {
        switch(s)
        {
            case("MUSIC") ->
            {
                setLayout(new BoxLayout(this,2));
                musicDuration.setMaximum(musicTime);
                musicDuration.setValue(musicTime);
                add(musicDuration);
            }
            default ->
            {

            }
        }
    }

    void backgroundSet(String s)
    {
        switch(s){
            case("EMPTY") ->
            {
             setBackground(Color.WHITE);
            }
            case("PERSON") ->
            {
             setBackground(Color.ORANGE);
            }
            case("CAT")->
            {
             setBackground(Color.GREEN);
            }
            case("POPUP")->
            {
             setBackground(Color.cyan);
            }
            case("PLAYER")->
            {
             setBackground(Color.RED);
            }
            case("MUSIC") ->
            {
                setBackground(Color.YELLOW);
            }
            default->
            {
             setBackground(Color.MAGENTA);
            }
        }   
    }

    void assignGame(String type)
    {


    }

    void SimonSays()
    {

    }

    void Mash()
    {

    }

    void Platoformer()
    {

    }

    void SpinTheBottle()
    {

    }

    MusicBox musicalBox = new MusicBox(musicTime, musicDuration, musicSong);

    public void beginMusic()
    { 
        musicTimeRestart();
        Thread musicThread = new Thread(musicalBox);
        musicThread.start();
    }

    public void musicTimeRestart()
    {
        musicalBox.Stop = false;
        musicalBox.refresh(musicTime);
    }

    public void ChangeSong(int Song)
    {
        musicalBox.Stop = true;
        musicalBox.Song = Song;  
        try {
            Thread.sleep(10);
            musicalBox.Stop = false;
            Thread musicThread = new Thread(musicalBox);
            musicThread.start();
        } catch (Exception e) {
        }
    }

    void Music()
    {
        
    }

    void TimingPong()
    {

    }
}

