import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.*;

public class DatingSim extends JFrame {

JPanel Image;
JPanel TextPanel;
JLabel Text;

boolean deadEnd = false;

String[] Dialog = new String[90];

public int Score;
node Curnode;



    DatingSim()
    {
        setLayout(new FlowLayout(1,0,10));
        setSize(650,650);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)(screensize.getWidth() / 2 - getSize().getHeight() / 2),
         (int)(screensize.getHeight() / 2 - getSize().getWidth() / 2));
        setResizable(false);
        setTitle("Crush");

        /*
         * add functions for different text options
         */


        Image = new JPanel();
        Image.setSize(650, 450);
        Image.setPreferredSize(new Dimension(650,450));
        Image.setBackground(Color.PINK);
        
        TextPanel = new JPanel(new BorderLayout());
        TextPanel.setSize(630, 200);
        TextPanel.setPreferredSize(new Dimension(630,200));

        Text = new JLabel();
         
        add(Image);   
        add(TextPanel);
        TextPanel.add(Text, BorderLayout.PAGE_START);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    JFrame Start()
    {
        GetDialog();
        Text.setText(Curnode.dialog);
       Score = 0;
       deadEnd = false;
        return this;
    }

    void kill()
    {
        setVisible(false);
        dispose();
    }

    void GetInput(String s)
    {
        
        //Traverse our node tree
        //Pray for me
        if(s.equals(" ") && Curnode.right == null && Curnode.left == null && Curnode.middle != null)
        {
            Curnode = Curnode.middle;
            Text.setText(Curnode.dialog);
        }
        else if(s.equals("a") && Curnode.right != null)
        {
            Curnode = Curnode.right;
            Text.setText(Curnode.dialog);
        }
        else if(s.equals("d")&& Curnode.left != null)
        {
            Curnode = Curnode.left;
            Text.setText(Curnode.dialog);
        }
       
    }


    void GetDialog()
    {
        Dialog[0] = "<html>You invited everyone from school to this party, you didn't think you could do it and you were even less sure that <br>she would come. But there she is across the room and you have just enough courage to try talking to her. <br><br> [Press SPACE to continue] </html>";
        Dialog[1] = "<html>Radiant and shining as ever there she is.... Joshalina. <br><br> [Press A to see how you're feeling] <br> [Press D to approach confidently]</html>";
        Dialog[2] = "<html>You feel your chest start to tighten, this isn't good, you can't have your first interaction with her be this sweaty.<br> You quickly turn around to try and leave before she sees your disgusting mess. <br><br> [Press SPACE to continue] </html>";
        Dialog[3] = "<html>You never get the courage to go back and talk to her... womp womp <br><br> [Press E to escape]</html>";
        Dialog[4] = "<html>You march over with the confidence of a thousand bulls, you've never talked to a woman before but has that <br> ever stopped someone? NO. This is YOUR PARTY and by God you are going to grow as a person and talk to girls. <br><br> [Press SPACE to continue] </html>";
        Dialog[5] = "<html>A poorly placed banana peel meets your foot and slip, crashing into a table which send a drink someone was <br> holding straight into Joshalina, you will never socially recover from this <br><br>[Press E to escape] </html>";
        
        node Start = new node(Dialog[0], 0);
        Curnode = Start;

        node Introduction = new node(Dialog[1], 0);
        Start.middle = Introduction;

        node panic = new node(Dialog[2], -1);
        node womp = new node(Dialog[3], -55555);
        panic.middle = womp;

        node bravado = new node(Dialog[4], 5);
        node fumble = new node(Dialog[5], -999999);
        bravado.middle = fumble;

        Introduction.left = panic;
        Introduction.right = bravado;

    }

    class node {
        String dialog;
        int scoreEffect;
        node left,middle,right;

        public node(String dialog, int scoreEffect)
        {
            this.dialog = dialog;
            this.scoreEffect = scoreEffect;
            left = middle = right = null;
        }
    }
}
