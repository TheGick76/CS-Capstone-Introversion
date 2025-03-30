import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.*;

public class DatingSim extends JFrame {

//Visuals
JPanel Image;
JPanel TextPanel;
JLabel Text;

//An arbitrary string array that will contain our dialog
//Just for storage when needed
String[] Dialog = new String[90];

public int PositiveInteraction = 5;
public int NegativeInteraction = -5;
int rating = 0;
//What node we are currently looking at
node Curnode;

    //Object creation constructor
    DatingSim()
    {
        setLayout(new FlowLayout(1,0,10));
        setSize(650,650);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)(screensize.getWidth() / 2 - getSize().getHeight() / 2),
         (int)(screensize.getHeight() / 2 - getSize().getWidth() / 2));
        setResizable(false);
        setTitle("Crush");

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
        //Putting this here also saves what node the player ended on
        //Unintentional but very useful!
        GetDialog();
    }

    JFrame Start()
    {
        Text.setText(Curnode.dialog);
        return this;
    }

    void kill()
    {
        //Removes the window but still keeps the game object
        setVisible(false);
        dispose();
    }

    int GetInput(String s)
    {
        //Traverse our node tree
        //Pray for me
        if(s.equals(" ") && Curnode.right == null && Curnode.left == null && Curnode.middle != null)
        {
            Curnode = Curnode.middle;
            Text.setText(Curnode.dialog);
            return 0;
        }
        if(s.equals(" ") && Curnode.right == null && Curnode.left == null && Curnode.middle == null)
        {
           if(rating == 3)
           {
            Text.setText(Dialog[54]);
           }
           else if(rating == -3)
           {
            Text.setText(Dialog[52]);
           }
           else
           {
            Text.setText(Dialog[53]);
           }
        }
        else if(s.equals("a") && Curnode.left != null)
        {
            Curnode = Curnode.left;
            Text.setText(Curnode.dialog);
            if(Curnode.scoreEffect == -1)
            {
                rating += -1;
                return NegativeInteraction;
            }
            else if(Curnode.scoreEffect == 1)
            {
                rating += 1;
                return PositiveInteraction;
            }
            
        }
        else if(s.equals("d")&& Curnode.right != null)
        {
            Curnode = Curnode.right;
            Text.setText(Curnode.dialog);
            if(Curnode.scoreEffect == -1)
            {
                rating += -1;
                return NegativeInteraction;
            }
            else if(Curnode.scoreEffect == 1)
            {
                rating += 1;
                return PositiveInteraction;
            }
        }
    //When input has no valid option
     return 0;
    }


    void GetDialog()
    {
        //Chosing to store strings this way is a little bit more storage intensive
        //But this is faster and doesn't not require additional code for reading files
        //and only reading a line from file X and line Y. Its ugly but this is effective.
        Dialog[0] = "<html>You invited everyone from school to this party, you didn't think you could do it and you were even less sure that <br>she would come. But there she is across the room and you have just enough courage to try talking to her. <br><br> [Press SPACE to continue] </html>";
        Dialog[1] = "<html>Radiant and shining as ever there she is.... Joshalina.<br><br> [Press SPACE to continue]</html>";
        Dialog[2] = "<html>She seems to be inspecting the snack table carefully, while glancing up at the couch where some people have taken up playing the game you set out (Platformer Supreme 1996). She glances up at you and waves.<br><br> [Press SPACE to continue]</html>";
        Dialog[3] = "<html>[Joshalina]<br>“Hey man! Killer party you got going on.”<br><br> [Press SPACE to continue]</html>";
        Dialog[4] = "<html>[Joshalina]<br>“I like the way you laid out the snacks, I never would’ve thought to do it in alphabetical order. And you have the peanuts on the other side of the table which is awesome since I’m allergic.”<br><br> [Press SPACE to continue]</html>";
        Dialog[5] = "<html>[Joshalina]<br>“AND, you got a game tesseract with Platformer Supreme, how did you even find one of those? I tried to <br> get one for my little brother but people are selling those things for a ridiculous amount of money, and I’m out of kidneys to sell.”<br><br> [Press SPACE to continue]</html>";
        Dialog[6] = "<html>Joshalina chuckles to herself and then meets your gaze.<br><br> [Press SPACE to continue]</html>";
        Dialog[7] = "<html>[Joshalina]<br>“Sorry, I’m Joshalina, it’s nice to see you again.”<br><br> [Press SPACE to continue]</html>";
        //Choice 1
        Dialog[8] = "<html>Joshalina extends her hand for a handshake.<br><br>Press ‘A’ to shake her hand. <br>Press ‘D’ to go in for the hug.";
        Dialog[9] = "<html>You can’t believe it, premarital hand holding, the thing your grandma warned you about. You can hear her rolling in her grave at mach speed as you lock in and reach for her hand while NOT being weird about it.<br><br> [Press SPACE to continue]</html>";
        Dialog[10] = "<html>You realize 5 microseconds after you began lifting up your hands to go in for a hug that she was very clearly <br>extending her hand for a handshake, you weirdo. But it's too late you have to commit. Let's hope it won’t be weird… Or weirder than you’ve already made it.<br><br> [Press SPACE to continue]</html>";
        Dialog[11] = "<html>[You]<br>“Hey, I’m Himothy. I'm glad you were able to make it.”<br><br>[Press SPACE to continue]</html>";
        Dialog[12] = "<html>Your hands meet, your grandma has reached maximum velocity, one small step for man and one giant leap for your social status. In the crispest non sweaty handshake you’ve ever done you introduced yourself properly.<br><br> [Press SPACE to continue]</html>";
        Dialog[13] = "<html>You and Joshalina have a wonderful time talking (and complaining) about school, video games, and a surprising mutual interest in retro TTRPGs<br><br> [Press SPACE to continue]</html>.";
        Dialog[14] = "<html>[You]<br>“Hey, it's so nice not seeing you in the hallways anymore! I’m Himothy”<br><br> [Press SPACE to continue]</html>";
        Dialog[15] = "<html>Joshalina stands there a bit shocked after that jumpscare, the air got a bit awkward.<br><br> [Press SPACE to continue]</html>";
        Dialog[16] = "<html>You and Joshalina are able to partially recover the conversation by talking about Platformer Supreme that you had hooked up on the TV, and try to move past that.<br><br> [Press SPACE to continue]</html>";
        //end of choice 1
        Dialog[17] = "<html>After a few minutes of pleasant conversation Joshalina gets a slight grimace on her face.<br><br> [Press SPACE to continue]</html>";
        Dialog[18] = "<html>[Joshalina]<br>“Hey I hate to ask this but do you have any food here that isn’t just snacks? I’m kinda starving and I don’t want to eat half of this table.”<br><br> [Press SPACE to continue]</html>";
        Dialog[19] = "<html>[You]<br>“Oh yeah of course! Let me go get you something!”<br><br> [Press SPACE to continue]</html>";
        Dialog[20] = "<html>You walk into the kitchen and realize very quickly you only had enough money to buy drinks and snacks for this party and you only have 6 ingredients in your fridge. Well this isn’t good but you are nothing if not resourceful so you pull it out to reveal Bread, Lettuce, Dressing, Peanut Butter, Jelly, and Shredded Cheese. <br><br>[Press SPACE to continue]</html>";
       //Choice 2
        Dialog[21] = "<html>You put together that you can either make her a PB&J or a Salad because anything else would be an abomination.<br><br>Press ‘A’ to make a Salad.<br>Press ‘D’ to make a PB&J";
        Dialog[22] = "<html>You walk back with a cobbled together salad, before you can apologize for the rather sad looking green pile you have you see Joshalina’s eye brighten up<br><br> [Press SPACE to continue]</html>";
        Dialog[23] = "<html>[Joshalina]<br>“Oh this is perfect!”<br><br> [Press SPACE to continue]</html>";
        Dialog[24] = "<html>She eagerly takes the salad from you and begins chowing down<br><br> [Press SPACE to continue]</html>";
        Dialog[25] = "<html>[Joshalina]<br>“Thank you so much Himothy! I haven't had a salad in like forever. This is really good!”<br><br> [Press SPACE to continue]</html>";
        Dialog[26] = "<html>You both find a couple of chairs and spectate some people playing Cards Against People, its endearing watching Joshalina try not choke to death when someone plays “Pitbull named princess vs Coughing baby rap battle”<br><br> [Press SPACE to continue]</html>";
        Dialog[27] = "<html>You walk back with one of the most well constructed PB&J’s you ever made, it is gourmet, it is built to absolute perfection. Before you can announce your major success you see a look of disgust cross Joshalina’s face.<br><br> [Press SPACE to continue]</html>";
        Dialog[28] = "<html>[Joshalina]<br>“I’m allergic to peanuts, remember?”<br><br> [Press SPACE to continue]</html>";
        Dialog[29] = "<html>You do remember, right now, you both stare down awkwardly at the paper plate you are holding which now suddenly feels a million times heavier.<br><br> [Press SPACE to continue]</html>";
        Dialog[30] = "<html>[Joshalina]<br>“It's fine I can find something here to eat. You should probably eat that so you don’t waste it.”<br><br> [Press SPACE to continue]</html>";
        Dialog[31] = "<html>You both stand around with you eating the PB&J, real smooth bro, just like the peanut butter. You can feel the pressure building.<br><br> [Press SPACE to continue]</html>";
        Dialog[32] = "<html>After the meal is finished you can feel the conversation starting to end, you want to ask her out but you don’t think you’ve really made the deep connection you need to seal the deal. You need to find one more thing to talk about.<br><br> [Press SPACE to continue]</html>";
        //End of choice 2
        Dialog[33] = "<html>Oh god your blanking, you can see her getting more disinterested by the second. Cmon think…. Think…… just say the first thing that comes to mind.<br><br> [Press SPACE to continue]</html>";
        Dialog[34] = "<html>Press ‘A’ to say that you think the Earth is flat.<br>Press ‘D’ to say literally every dinosaur fact you know.";
        //Choice 3
        Dialog[35] = "<html>Joshalina whips her head around to look at you, and begins laughing. She looks back up at you and sees that you are serious. The laughing gets noticeably less heartfelt.<br><br> [Press SPACE to continue]</html> ";
        Dialog[36] = "<html>[Joshalina]<br>“...What?”<br><br> [Press SPACE to continue]</html>";
        Dialog[37] = "<html>[You]<br>“I just don’t think there's enough evidence for it.”<br><br> [Press SPACE to continue]</html>";
        Dialog[38] = "<html>[Joshalina]<br>“Not enough evidence of it? We’ve known the Earth has been round for thousands of years.”<br><br> [Press SPACE to continue]</html>";
        Dialog[39] = "<html>[You]<br>“That's just what they want you to think.”<br><br> [Press SPACE to continue]</html>";
        Dialog[40] = "<html>[Joshalina]<br>“Are you calling me stupid?”<br><br> [Press SPACE to continue]</html>";
        Dialog[41] = "<html>[You]<br>“No no no, its not your fault that you’ve been tricked. They control the media and all that, there's actually this really interesting article I can send to you.”<br><br> [Press SPACE to continue]</html>";
        Dialog[42] = "<html>Way to go…. Freak. Lets hope she forgets this soon.<br><br> [Press SPACE to continue]</html>";
        Dialog[43] = "<html>You begin rambling about quite literally every single dinosaur fact you know, which is a lot. Like a lot. Like you are one thesis away from being a registered paleontologist. There's just so many and they are all so interesting.<br><br> [Press SPACE to continue]</html>";
        Dialog[44] = "<html>[Joshalina]<br>“Wow.”<br><br> [Press SPACE to continue]</html>";
        Dialog[45] = "<html>[Joshalina]<br>“What made you so passionate about dinosaurs?”<br><br> [Press SPACE to continue]</html>";
        Dialog[46] = "<html>[You]<br>“It was a childhood thing but before my mom passed away she took me to the Museum in the city with all the dinosaur bones. She made me promise to learn as many dinosaur facts as I could for her so that when I visited her in the hospital we’d have something fun to talk about instead of…. Y’know.”<br><br> [Press SPACE to continue]</html>";
        Dialog[47] = "<html>You can see Joshalina’s eyes start to get misty as she looks away. For a minute there is a soft silence between you two.<br><br> [Press SPACE to continue]</html>";
        Dialog[48] = "<html>[Joshalina]<br>“Thank you for sharing that. I’m actually doing something similar for my little brother.”<br><br> [Press SPACE to continue]</html>";
        Dialog[49] = "<html>You and Joshalia carry on sincere and somber talk.<br><br> [Press SPACE to continue]</html>";
        //end of chocie 3
        Dialog[50] = "<html>You can feel the conversation winding down, there's nothing else you can do. You just have to go for it.<br><br> [Press SPACE to continue]</html>";
        Dialog[51] = "<html>[You]<br>“Joshalina, would you like to go on a date Saturday?”<br><br> [Press SPACE to continue]</html>";
        //Results
        Dialog[52] = "<html>[Joshalina]<br>“...No, I would actually prefer it if this was the last time we talked.”</html>";
        Dialog[53] = "<html>[Joshalina]<br>“Hey, you’re really nice, and I would like to keep talking to you but just as a friend.”</html>";
        Dialog[54] = "<html>[Joshalina]<br>“I think I would! I’m really glad we talked.”";
        //Creating our node tree
        node Start = new node(Dialog[0], 0);
        Curnode = Start;

        node Introduction = new node(Dialog[1], 0);
        Start.middle = Introduction;

        node Introduction1 = new node(Dialog[2], 0);
        Introduction.middle = Introduction1;
        node Introduction2 = new node(Dialog[3], 0);
        Introduction1.middle = Introduction2;
        node Introduction3 = new node(Dialog[4], 0);
        Introduction2.middle = Introduction3;
        node Introduction4 = new node(Dialog[5], 0);
        Introduction3.middle = Introduction4;
        node Introduction5 = new node(Dialog[6], 0);
        Introduction4.middle = Introduction5;
        node Introduction6 = new node(Dialog[7], 0);
        Introduction5.middle = Introduction6;

        node Choice1 = new node(Dialog[8],0);
        Introduction6.middle = Choice1;
        node Choice1A = new node(Dialog[9], 1);
        Choice1.left = Choice1A;
        node Choice1B = new node(Dialog[10], -1);
        Choice1.right = Choice1B;
        node Choice1A1 = new node(Dialog[11],0);
        Choice1A.middle = Choice1A1;
        node Choice1A2 = new node(Dialog[12],0);
        Choice1A1.middle = Choice1A2;
        node Choice1A3 = new node(Dialog[13],0);
        Choice1A2.middle = Choice1A3;
        node Choice1B1 = new node(Dialog[14], 0);
        Choice1B.middle = Choice1B1;
        node Choice1B2 = new node(Dialog[15], 0);
        Choice1B1.middle = Choice1B2;
        node Choice1B3 = new node(Dialog[16], 0);
        Choice1B2.middle = Choice1B3;
        node ConvergeChoice1 = new node(Dialog[17],0);
        Choice1B3.middle = ConvergeChoice1;
        Choice1A3.middle = ConvergeChoice1;

        node Hungry = new node(Dialog[18], 0);
        ConvergeChoice1.middle = Hungry;
        node Hungry1 = new node(Dialog[19], 0);
        Hungry.middle= Hungry1;
        node Hungry2 = new node(Dialog[20],0);
        Hungry1.middle = Hungry2;

        node Choice2 = new node(Dialog[21],0);
        Hungry2.middle = Choice2;
        node Choice2A = new node(Dialog[22],1);
        Choice2.left = Choice2A;
        node Choice2A1 = new node(Dialog[23],0);
        Choice2A.middle = Choice2A1;
        node Choice2A2 = new node(Dialog[24],0);
        Choice2A1.middle = Choice2A2;
        node Choice2A3 = new node(Dialog[25],0);
        Choice2A2.middle = Choice2A3;
        node Choice2A4 = new node(Dialog[26],0);
        Choice2A3.middle = Choice2A4;
        node Choice2B = new node(Dialog[27],-1);
        Choice2.right = Choice2B;
        node Choice2B1 = new node(Dialog[28],0);
        Choice2B.middle = Choice2B1;
        node Choice2B2 = new node(Dialog[29],0);
        Choice2B1.middle = Choice2B2;
        node Choice2B3 = new node(Dialog[30],0);
        Choice2B2.middle = Choice2B3;
        node Choice2B4 = new node(Dialog[31],0);
        Choice2B3.middle = Choice2B4;
        node ConvergeChoice2 = new node(Dialog[32],0);
        Choice2B3.middle = ConvergeChoice2;
        Choice2A3.middle = ConvergeChoice2;
        
        node blanking = new node(Dialog[33],0);
        ConvergeChoice2.middle = blanking;
        node Choice3 = new node(Dialog[34],0);
        blanking.middle= Choice3;

        node Choice3A = new node(Dialog[35], -1);
        Choice3.left = Choice3A;
        node Choice3A1 = new node(Dialog[36], 0);
        Choice3A.middle = Choice3A1;
        node Choice3A2 = new node(Dialog[37], 0);
        Choice3A1.middle = Choice3A2;
        node Choice3A3 = new node(Dialog[38], 0);
        Choice3A2.middle = Choice3A3;
        node Choice3A4 = new node(Dialog[39], 0);
        Choice3A3.middle = Choice3A4;
        node Choice3A5 = new node(Dialog[40], 0);
        Choice3A4.middle = Choice3A5;
        node Choice3A6 = new node(Dialog[41], 0);
        Choice3A5.middle = Choice3A6;
        node Choice3A7 = new node(Dialog[42], 0);
        Choice3A6.middle = Choice3A7;
        node Choice3B = new node(Dialog[43], 1);
        Choice3.right = Choice3B;
        node Choice3B1 = new node(Dialog[44],0);
        Choice3B.middle = Choice3B1;
        node Choice3B2 = new node(Dialog[45],0);
        Choice3B1.middle = Choice3B2;
        node Choice3B3 = new node(Dialog[46],0);
        Choice3B2.middle = Choice3B3;
        node Choice3B4 = new node(Dialog[47],0);
        Choice3B3.middle = Choice3B4;
        node Choice3B5 = new node(Dialog[48],0);
        Choice3B4.middle = Choice3B5;
        node Choice3B6 = new node(Dialog[49],0);
        Choice3B5.middle = Choice3B6;

        node ConvergingChoice3 = new node(Dialog[50],0);
        Choice3B6.middle = ConvergingChoice3;
        Choice3A7.middle = ConvergingChoice3;

        node AskHer = new node(Dialog[51],0);
        ConvergingChoice3.middle = AskHer;






    }

    //The nodes that make up our tree
    class node {
        String dialog;
        int scoreEffect;
        node left,middle,right;

        //Node constructor
        public node(String dialog, int scoreEffect)
        {
            this.dialog = dialog;
            this.scoreEffect = scoreEffect;
            left = middle = right = null;
        }
    }
}
