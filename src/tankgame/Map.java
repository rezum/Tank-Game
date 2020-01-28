package tankgame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


import static javax.imageio.ImageIO.read;

public class Map {
    public static final int WORLD_WIDTH = 1600;
    public static final int WORLD_HEIGHT = 1000;


    private BufferedImage background_image;
    private BufferedImage wall, wall2, pu1, pu2, pu3, bull;
    public static ArrayList<GameConstants>  gameConstants = new ArrayList<>();


    Map() {
    }

    public void init() {

        try {
            background_image = read(new File("resources/Background.png"));
            wall = read( new File("resources/Wall1.png"));
            wall2 = read( new File("resources/Wall2.png"));
            pu1 = read( new File("resources/powerup1.png"));
            pu2 = read( new File("resources/powerup2.png"));
            pu3 = read( new File("resources/powerup3.png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


        for (int c = 900; c <901 ; c+=1){  // power up 1
            gameConstants.add( new PowerUp1( 100, c, pu1));
        }

        for (int c = 800; c <801 ; c+=1){  //power up 2
            gameConstants.add( new PowerUp2( 100, c, pu2));
        }

        for (int c = 850; c <851 ; c+=1){  //power up 3
            gameConstants.add( new PowerUp3( 200, c, pu3));
        }

        for (int c = 100; c <101 ; c+=1){  // power up 1
            gameConstants.add( new PowerUp1( 1400, c, pu1));
        }

        for (int c = 150; c <151 ; c+=1){  //power up 2
            gameConstants.add( new PowerUp2( 1450, c, pu2));
        }

        for (int c = 200; c <201 ; c+=1){  //power up 3
            gameConstants.add( new PowerUp3( 1400, c, pu3));
        }

        //walls

        for(int t = 10; t < 250; t+=30){ // breakable  top
            gameConstants.add(new BreakableWall(wall2, 1250, t));
        }

        for (int a = 1250; a <1600; a +=30){// breakable middle top
            gameConstants.add(new BreakableWall(wall2, a, 700));
        }

        for (int z = 300; z < 650; z +=30){ //middle top
            gameConstants.add(new Wall(wall,760 , z));
        }



        for (int b = 0; b < 300; b +=30){ // breakable middle bottom
            gameConstants.add(new BreakableWall(wall2, b, 250));
        }

        for (int y = 600; y < 950; y +=30){ //middle bottom
            gameConstants.add(new Wall(wall, y, 450));
        }

        for(int t = 750; t < 970; t+=30){ // breakable  bottom
            gameConstants.add(new BreakableWall(wall2, 270, t));
        }



        for(int h = 30; h < 300; h+=30){ // breakable  left
            gameConstants.add(new BreakableWall(wall2, h, 750));
        }

        for (int c = 700; c < 1000; c +=30){ //breakable middle left
            gameConstants.add(new BreakableWall(wall2, 1250, c));
        }

//        for (int w = 300; w < 600; w +=150){ //middle left
//            gameConstants.add(new Wall(wall, 400, w));
//        }

        for(int h = 1250; h < 1550; h+=30){ // breakable  right
            gameConstants.add(new BreakableWall(wall2, h, 250));
        }

        for (int v = 0; v < 250; v +=30){ //breakable middle right
            gameConstants.add(new BreakableWall(wall2, 270, v));
        }

//        for (int v = 300; v < 600; v +=150){ //middle right
//            gameConstants.add(new Wall(wall, 960, v));
//        }



        // wall border
        for(int i = 0; i < 1600; i+=30) { //wall top
            gameConstants.add(new Wall(wall, i, 10));
        }
        for(int j = 10; j < 1000; j+=30){ // wall left
            gameConstants.add(new Wall(wall, 0, j));
        }
        for(int k = 0; k < 1600; k+=30) { //wall bottom
            gameConstants.add(new Wall(wall, k,970));
        }
        for(int jk = 10; jk < 1000; jk +=30){ // wall right
            gameConstants.add(new Wall(wall, 1560, jk));
        }

    }

    public void drawImage (Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < WORLD_WIDTH / background_image.getWidth() + 1; i++) {
            for (int j = 0; j < WORLD_HEIGHT / background_image.getHeight() + 1; j++) {

                g2.drawImage(background_image, i * background_image.getWidth(), j * background_image.getHeight(), null);
            }
        }

        for (int i = 0; i < gameConstants.size(); i++){
            gameConstants.get(i).drawImage(g2);
        }


    }


}


