import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class Platformer extends JFrame{

    JFrame frame;
    
    JPanel GamePanel;

    Timer gameTimer;

    int HeroX = 50;
    int HeroY = 400;

    int HeroHieght = 100;
    int HeroWidth = 50;

    double HeroXSpeed = 0;
    double HeroYSpeed = 0;

    Rectangle HitBox;

    ArrayList<Rectangle> walls = new ArrayList();

    Rectangle death = new Rectangle(-300,700,1500,5);
    Rectangle win = new Rectangle(600,300,10,50);

    boolean Active = false;
    boolean StartUp = false;

    boolean KeyLeft;
    boolean KeyRight;
    boolean KeyUp;
    boolean KeyDown;

    int i;

    int Win = 10;
    int Lose = -5;

    JLabel gameOver = new JLabel();

    Platformer platformer;

    Platformer()
    {
        setSize(650,650);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)(screensize.getWidth() / 2 - getSize().getHeight() / 2),
         (int)(screensize.getHeight() / 2 - getSize().getWidth() / 2));
        setResizable(false);
        setTitle("Platformer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GamePanel = new JPanel();
        GamePanel.setSize(getSize());
        GamePanel.setBackground(Color.lightGray);
        GamePanel.add(gameOver);
        add(GamePanel);

        //Painting the walls first
        StartUp =true;
        CreateLevel();

        
        
        //Player initialziation
        HitBox = new Rectangle(HeroX,HeroY,HeroWidth,HeroHieght);

        //For updating player per frame
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            
            @Override
            public void run()
            {
                //move player
            if(Active)
            {
               SetHero();
               repaint();
               StartUp = false;
            }
            }

        },0,17);
    }

    
    JFrame Start()
 	{
        HitBox.y = 400;
        HitBox.x = 50;
        HeroX = 50;
        HeroY = 400;
        i = 0;
        KeyLeft = false;
        KeyRight = false;
        KeyUp = false;
        KeyDown = false;
        Active = true;
        gameOver.setText("");
        return this;
 	}

    void kill()
    {
        i = 0;
        Active = false;
        setVisible(false);
        dispose();
    }

    void SetHero()
    {
        if(KeyLeft && KeyRight || !KeyLeft && !KeyRight)
        {
            HeroXSpeed *= .2;
        }
        else if(KeyLeft && !KeyRight)
        {
            HeroXSpeed--;
        }
        else if(KeyRight && !KeyLeft)
        {
            HeroXSpeed++;
        }

        if(HeroXSpeed > 0 && 0.75 > HeroXSpeed)
        { 
            HeroXSpeed = 0;
        }
        if(HeroXSpeed< 0 && -0.75 < HeroXSpeed)
        { 
            HeroXSpeed = 0;
        }
        if(HeroXSpeed > 7)
       { HeroXSpeed = 7; }
       if(HeroXSpeed < -7)
       { HeroXSpeed = -7; }


        if(KeyUp)
        {
            //if touching ground
            HitBox.y++;
            for (Rectangle rectangle : walls) {
                if(rectangle.intersects(HitBox))
                 HeroYSpeed = -12;
            }
            HitBox.y--;
           
        }

        if(HitBox.intersects(death))
        {
            gameOver.setText("Game over! Press any key to continue");
            i = -1;
        }

        if(HitBox.intersects(win))
        {
            gameOver.setText("Win! Press any key to continue");
            i= 1;
        }

        //Vertical collision
        HitBox.y += HeroYSpeed / 2;
        for(Rectangle rectangle : walls)
        {
            if(HitBox.intersects(rectangle))
            { HitBox.y -= HeroYSpeed;
                while(!rectangle.intersects(HitBox))
                {HitBox.y += Math.signum(HeroYSpeed);}
                HitBox.y -= Math.signum(HeroYSpeed);
                HeroYSpeed = 0;
                HeroY = HitBox.y;
            }
        }
        //Horizontal collision
        
        HitBox.x += HeroXSpeed;
        for(Rectangle rectangle : walls)
        {
            if(HitBox.intersects(rectangle))
            { HitBox.x -= HeroXSpeed;
                while(!rectangle.intersects(HitBox))
                {HitBox.x += Math.signum(HeroXSpeed);}
                HitBox.x -= Math.signum(HeroXSpeed);
                HeroXSpeed = 0;
                HeroX = HitBox.x;
            }
        }
            

       

        HeroYSpeed += .5;

        if(i != 1)
        {
        HeroX += HeroXSpeed;
        HeroY += HeroYSpeed;

        HitBox.x = HeroX;
        HitBox.y = HeroY;
        }
        else if(i ==1)
        {
            HeroXSpeed = 0;
            HeroYSpeed = 0;
    
        }


    }


    //All the flcikering is becuase we are using JFrame
    //And not JPanels for drawing, sorry lads
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        draw(g2d);
        drawWalls(g2d);
    }
   
    public void draw(Graphics2D gtd)
    {
        gtd.setColor(Color.BLACK);
        gtd.fillRect(HeroX, HeroY, HeroWidth, HeroHieght);
    }

    public void drawWalls(Graphics2D gtd)
    {
        for (Rectangle rectangle : walls) {
            gtd.setColor(Color.BLUE);
            gtd.fillRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
        }
        gtd.setColor(Color.yellow);
        gtd.fillRect(win.x,win.y,win.width,win.height);
    }

    int GetInput(String input)
 	{

        if(i ==1)
        {
            return 1;
        }
        if(i == -1)
        {
            return -1;
        }

         switch(input)
         {
         	// Movement Function
         	case "w" -> {KeyUp = true;}
         	case "a" -> {KeyLeft = true;}
         	case "s" -> {KeyDown = true;}
         	case "d" -> {KeyRight = true;}
            case "DUP" -> {KeyRight = false;}
            case "SUP" -> {KeyDown = false;}
            case "AUP" -> {KeyLeft = false;}
            case "WUP" -> {KeyUp = false;}
            case "e" -> i = 2;
            default -> {
            }
         }
         System.out.print(i);
         return 0;
 	}

    //Can be modified to send which level to make
    void CreateLevel()
    {
        int level = 1;
        if(level == 1)
        {
            walls.add(new Rectangle(0,600,150,250));
            walls.add(new Rectangle(250,470,100,50));
            walls.add(new Rectangle(500,350, 150,700));

        }
    }

}