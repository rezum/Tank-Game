package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

public class Bullet extends GameConstants{

    private final int R = 4;
    private int vx;
    private int vy;
    private int angle;
    private Rectangle bound = new Rectangle(this.x, this.y, this.bull.getWidth(), this.bull.getHeight());
    private static BufferedImage bull;
    private boolean isAlive;
    Tank tank;



    static {
        try{
            bull = read( new File("resources/Shell.png"));
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    Bullet(Tank tank, int x, int y, int angle ) {
        super(x,y, bull);
       this.angle = angle;
       isAlive =true;
       this.tank = tank;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void moveBullet() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkCollision(this);
        updateBounds();
    }


    public void checkCollision(Bullet bullet){
        GameConstants obj;
        Rectangle tbound = bullet.getBounds();
        for (int i =0; i< Map.gameConstants.size();i++){
            obj = Map.gameConstants.get(i);
            if (tbound.intersects(obj.getBounds())&& obj != tank){
                if(obj instanceof BreakableWall) {
                    Map.gameConstants.remove(obj); //removes breakable wall
                }
                if(obj instanceof Tank){
                    ((Tank) obj).Damage(5);
                }
                isAlive =false;

//                if(obj instanceof PowerUp1){
//
//                }
            }
        }
    }




    public Rectangle getBounds(){
        return this.bound;
    }

    public void updateBounds(){
        this.bound = new Rectangle(this.x, this.y, bull.getWidth(), bull.getHeight());
    }


    public void drawImage(Graphics g) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bull.getWidth() / 2.0, this.bull.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.bull, rotation, null);
    }


}
