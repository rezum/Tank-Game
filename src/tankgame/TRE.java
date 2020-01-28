 package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;


public class TRE extends JPanel{

    public static final int WORLD_WIDTH = 1600;
    public static final int WORLD_HEIGHT = 1000;

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 750;


    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;
    private Tank t1;
    private Tank t2;


    private Map bg;



    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        try {

            while (true) {

                trex.t1.update(); //update tank
                trex.t2.update();
                trex.repaint(); //redraw game
//               System.out.println(trex.t1);
//               System.out.println(trex.t2);
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.jf = new JFrame("Tank Rotation");
        this.world = new BufferedImage(TRE.WORLD_WIDTH, TRE.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null, t2img = null ;

        try {
            System.out.println(System.getProperty("user.dir"));
            t1img = read( new File("resources/tank1.png") );
            t2img = read( new File("resources/tank2.png") );

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        bg = new Map();
        bg.init();


        t1 = new Tank(1300, 900, 0, 0, 0, t2img);

        t2 = new Tank(50, 50, 0, 0, 0, t1img);

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);

        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

    }


    public int  checkboundsX (Tank tankx){
        int result = tankx.getX() - SCREEN_WIDTH/4;

        if (result< 0){
            result= 0;
        }
        else if ( result > WORLD_WIDTH -SCREEN_WIDTH/2){
            result = WORLD_WIDTH -SCREEN_WIDTH/2;
        }
        return result;
    }

    public int checkboundsY (Tank tanky){
        int result = tanky.getY();

        if (result < 0){
            result = 0;
        }
        else if (result > WORLD_HEIGHT - SCREEN_HEIGHT ){
            result = WORLD_HEIGHT - SCREEN_HEIGHT;
        }
        return result;
    }



    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);


        this.bg.drawImage(buffer);

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        g2.drawImage(world,0,0,null);


        //split screen

        int subtx = checkboundsX(t1);
        int subty = checkboundsY(t1);
        int subt2x = checkboundsX(t2);
        int subt2y = checkboundsY(t2);

        BufferedImage leftScreen = this.world.getSubimage( subt2x,  subt2y  , SCREEN_WIDTH / 2, SCREEN_HEIGHT);
        BufferedImage rightScreen = this.world.getSubimage(subtx, subty , SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        g2.drawImage(leftScreen,0,0,null);
        g2.drawImage(rightScreen, SCREEN_WIDTH/2,0,null );

        //minimap
        AffineTransform mini = AffineTransform.getTranslateInstance(SCREEN_WIDTH/2.5, 0);
        mini.scale(0.17, 0.17);
        g2.drawImage(world , mini,  null);



        g2.setFont(new Font("Courier", Font.PLAIN, 20));


        g2.setColor(Color.WHITE);
        g2.drawString("Lives:" + this.t1.getLives(), SCREEN_WIDTH * 65/80, SCREEN_HEIGHT * 35 / 40);

        g2.drawString("Tank 1 Health:" + this.t2.getHealth(), SCREEN_WIDTH * 12 / 80, SCREEN_HEIGHT * 35 / 40);

        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t2.getHealth() ; i++) {
            g2.drawRect(SCREEN_WIDTH * 13 / 80 + i, SCREEN_HEIGHT * 36 / 40, 60, 30);
        }
        
        // displays health and lives for Tank1

        g2.setColor(Color.WHITE);
        g2.drawString("Lives:" + this.t2.getLives(), SCREEN_WIDTH * 27/80, SCREEN_HEIGHT * 35 / 40);


        g2.setColor(Color.WHITE);
        g2.drawString("Tank 2 Health:" + this.t1.getHealth(), SCREEN_WIDTH * 50 / 80, SCREEN_HEIGHT * 35 / 40);

        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t1.getHealth() ; i++) {
            g2.drawRect(SCREEN_WIDTH * 50 / 80 + i, SCREEN_HEIGHT * 36 / 40, 60, 30);
        }

    }

}
