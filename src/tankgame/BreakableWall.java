package tankgame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameConstants {


    private Rectangle bounds;

    BreakableWall(BufferedImage img, int x, int y ){

        super( x, y, img );

        this.bounds = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        gameobjects.add(this);
    }

}
