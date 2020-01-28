package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Wall extends GameConstants {


    private Rectangle bounds;

    Wall( BufferedImage img, int x, int y ){
        super(x, y, img);

        this.bounds = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        gameobjects.add(this);
    }



}
