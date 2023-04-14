import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PacMan {
    int radius;
    double initialPositionX;
    double initialPositionY;
    double positionX;
    double positionY;
    double velocityX;
    double velocityY;
    int numLives;
    public PacMan(World w) {
        initialPositionX = 50;
        initialPositionY = w.height - 50;
        positionX = 50;
        positionY = 500;
        velocityX = 0;
        velocityY = 0;
        radius = 25;

    }

    public void update(double time) {
        positionX = positionX + (velocityX * time);
        positionY = positionY + (velocityY * time);
    }

    public void draw(Graphics g) {
        //Color color = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillArc((int)positionX,(int)positionY,50,50,25,180);
        //velocity x neg, point
        g.fillArc((int)positionX,(int)positionY,50,50, -25,-180);
    }

}
