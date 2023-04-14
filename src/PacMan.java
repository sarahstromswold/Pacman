import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PacMan implements KeyListener {
    int radius;
    double initialPositionX;
    double initialPositionY;
    double positionX;
    double positionY;
    double velocity;
    int numLives;
    Color color;
    public PacMan(World w) {
        initialPositionX = 50;
        initialPositionY = w.height - 50;
        positionX = 50;
        positionY = 500;
        velocity = 40;
        radius = 25;
        color = Color.YELLOW;

    }

    public void update(double time) {
        positionX = positionX + (velocity * time);
        positionY = positionY + (velocity * time);
    }

    public void draw(Graphics g) {
        //Color color = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillArc((int)positionX,(int)positionY,50,50,25,180);
        g.fillArc((int)positionX,(int)positionY,50,50, -25,-180);
        g.setColor(color);
    }
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'w') {
            velocity = -1 * velocity;
        }
        if (c == 'a') {
            velocity = -1 * velocity;
        }
        if (c == 's') {
            this.velocity = velocity;
        }
        if (c == 'd') {
            this.velocity = velocity;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void addNotify() {}

}
