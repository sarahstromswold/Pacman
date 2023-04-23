import java.awt.*;
import java.util.Random;
import java.util.*;

public class Fire {
    double positionX;
    double positionY;
    double velocityX;
    double velocityY;
    Color color;
    public Fire(World w, int initialPositionX, int initialPositionY) {
        Random rand = new Random();
        positionX = 300;
        positionY = 600;
        velocityX = 0;
        velocityY = 0;
        color = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
    }

//    public void update(double time) {
//        positionX = positionX + (velocityX * time);
//        positionY = positionY + (velocityY * time);
//    }

    public void draw(Graphics g) {
        Color color = g.getColor();
        g.setColor(color);
        g.fillArc((int)positionX,(int)positionY,50,50, 0,-180);
    } //draw fire
}

