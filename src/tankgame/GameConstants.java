package tankgame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class GameConstants {

    protected int x;
    protected int y;
    protected int vx;
    protected int vy;
//    protected int angle;
//    protected Boolean active;
    protected BufferedImage img;
    protected Rectangle r;
    private Rectangle bounds;
    public static ArrayList<GameConstants> gameobjects = new ArrayList<>();

    GameConstants( int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.bounds = new Rectangle(this.x, this.y, img.getWidth(),img.getHeight());
    }

    public Rectangle getBounds( ){
        return this.bounds;
    }

    public void updateBounds(){
        this.bounds = new Rectangle(this.x, this.y, img.getWidth(),img.getHeight());
    }

    public int getX(){ //to get position in x
        return this.x;
    }

    public int getY(){ // to get position in y
        return this.y;
    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, x, y, null);
    }
}
