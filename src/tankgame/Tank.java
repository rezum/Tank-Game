package tankgame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;


public class Tank extends GameConstants{

    private int vx;
    private int vy;
    private int angle;
    private int health;
    private final int R = 3;
    private final int ROTATIONSPEED = 2;


    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private Bullet bullet;
    private Rectangle bounds = new Rectangle(this.x, this.y, img.getWidth(),img.getHeight());
    ArrayList<GameConstants>  gameConstants = new ArrayList<>();
    ArrayList<Bullet> bulletList =new ArrayList<>();

    private int lives;

    private float coolDown = 100;
    private float coolDownTimer = 0;

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super( x, y , img);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;

        Map.gameConstants.add(this);
        this.health = 100;
        this.lives = 3;
    }

    public int getHealth(){
        return health;
    }

    public int getLives(){
        return lives;
    }

    public void Damage(int damage){
        health -= damage;

        if (lives != 0) {
            if (health <= 0) {
                lives -= 1;
                health = 100;
            }
        }
        else {
            System.exit(0);
        }

    }

    public Rectangle getBounds(){
        return this.bounds;
    }

    public void updateBounds(){
        this.bounds = new Rectangle(this.x, this.y, img.getWidth(),img.getHeight());
    }


    public void checkCollision(Tank tank){
        GameConstants obj;
        Rectangle tbound = tank.getBounds();
        for (int i =0; i< Map.gameConstants.size();i++){
            obj = Map.gameConstants.get(i);
            if (tbound.intersects(obj.getBounds())){
                handle(obj);
            }
        }
    }

    public void handle(GameConstants obj){

            if (obj instanceof Wall) {
                if (this.UpPressed) {
                    this.x -= vx;
                    this.y -= vy;
                }
                if (this.DownPressed) {
                    this.x += vx;
                    this.y += vy;
                }
            }
            if (obj instanceof BreakableWall) {
                if (this.UpPressed) {
                    this.x -= vx;
                    this.y -= vy;
                }
                if (this.DownPressed) {
                    this.x += vx;
                    this.y += vy;
                }
            }

            if (obj instanceof PowerUp1) {
                this.lives += 1;
                Map.gameConstants.remove(obj);
            }

            if (obj instanceof PowerUp2) {
                if (this.health != 100) {
                    this.health = 100;
                }
                Map.gameConstants.remove(obj);
            }

            if (obj instanceof PowerUp3){
                //this.lives += 5;
                this.coolDown = 20;
                Map.gameConstants.remove(obj);
            }
    }



    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void shootPressed() { this.shootPressed = true; }

    void shootNotPressed() { this.shootPressed = false; }

    public void update() {

            if (this.UpPressed) {
                this.moveForwards();
                updateBounds();
            }
            if (this.DownPressed) {
                this.moveBackwards();
                updateBounds();
            }

            if (this.LeftPressed) {
                this.rotateLeft();
                updateBounds();
            }
            if (this.RightPressed) {
                this.rotateRight();
                updateBounds();
            }

            if (this.shootPressed) {
                this.shoot(bullet);
                updateBounds();
            }

            if (coolDownTimer < coolDown) {
                coolDownTimer += 1;
            }

            for (int i = 0; i < bulletList.size(); i++) {
                if (bulletList.get(i).isAlive()) {
                    bulletList.get(i).moveBullet();
                }
            }

            checkCollision(this);

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
        updateBounds();
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
        updateBounds();
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        updateBounds();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        updateBounds();
    }

    private void shoot(Bullet bullet){

        if(coolDownTimer >= coolDown) {
            bulletList.add(new Bullet(this, x, y, angle));
            coolDownTimer = 0;
        }

    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TRE.WORLD_WIDTH - 88) {
            x = TRE.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TRE.WORLD_HEIGHT - 80) {
            y = TRE.WORLD_HEIGHT - 80;
        }
    }



    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle + Arrays.toString(bulletList.toArray());
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);


        for (int i=0; i< bulletList.size(); i++){
            if(bulletList.get(i).isAlive()){
                bulletList.get(i).drawImage(g2d);
            }
        }

    }

}
