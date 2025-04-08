import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Tiles extends JPanel{

    //What tile number is it
    int tileNumber;
    Countdown Count = new Countdown(0,0, null);
    //for music
    MusicBox musicalBox;
    JProgressBar musicDuration = new JProgressBar();
    String credits;
    String curSong;
    int musicTime = 60000;
    int musicSong = 1;
    //for popups
    int PopupGame;
    
    int Timer;

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
    
    Tiles(int tileNum, String tileType , int T)
    {
        this.tileNumber = tileNum;
        this.tileType = tileType;
        this.Timer = T;
        specificTileGoodies(tileType);
        backgroundSet(tileType);
        assignGame(tileType);
    }
    
    
    void StartCountdown(boolean[] GameOutcome)
    {
    	
    	Count = new Countdown(2, Timer ,GameOutcome);
    	Thread C = new Thread(Count);
    	C.start();
    }

    //For tiles that have timer bars or notifications
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
            case("CRUSH") ->
            {
                setBackground(Color.PINK);
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
    void Music()
    {
        musicalBox = new MusicBox(musicTime, musicDuration, musicSong, credits, curSong);
    }

    void MusicInput(String input)
    {
        musicalBox.GetInput(input);
    }

    void TimingPong()
    {

    }
}

