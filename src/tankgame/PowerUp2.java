package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp2 extends GameConstants {

    private JFrame jf;
    private Graphics2D buffer;

    private Rectangle bounds;

    PowerUp2(int x, int y , BufferedImage img){

        super (x,y, img);
        this.bounds = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        gameobjects.add(this);
    }
}
