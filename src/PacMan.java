import java.awt.*;

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
        radius = 14;
    }

    public void update(double time) {
        positionX = positionX + (velocityX * time);
        positionY = positionY + (velocityY * time);
	if (positionX >= 820 || positionX <= 52) {
	    velocityX = 0;
	}
	if (positionY >= 503 || positionY <= 56) {
	    velocityY = 0;
	}
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc((int)positionX,(int)positionY,radius * 2, radius * 2,25,180);
        //velocity x neg, point
        g.fillArc((int)positionX,(int)positionY, radius * 2, radius * 2, -25,-180);
    }

}
