import java.awt.*;

public class PacMan {
    int radius;
    double initialPositionX;
    double initialPositionY;
    double positionX;
    double positionY;
    double velocityX;
    double velocityY;
    int startangle1;
    int startangle2;
    int numLives;
    int tileX;
    int tileY;
    public PacMan(Maze m) {
        initialPositionX = 52;
        initialPositionY = 500;
        positionX = 52;
        positionY = 500;
        velocityX = 0;
        velocityY = 0;
        radius = 14;
        startangle1 = 25;
        startangle2 = -25;
        tileX = (int)(positionX - m.initX)/32;
        tileY = (int)(((positionY - (m.initY - 480))/32) - 1);
        System.out.println(tileY);
    } //pacman constructor

    public void update(double time,Maze m) {
        positionX = positionX + (velocityX * time);
        positionY = positionY + (velocityY * time);
	if (positionX >= 820 || positionX <= 52) {
	    velocityX = 0;
	}
	if (positionY >= 503 || positionY <= 56) {
	    velocityY = 0;
	} //updates pacman
    if (m.maze[tileX][tileY] == 1) {
        velocityY = 0;
        velocityX = 0;
    }
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc((int)positionX,(int)positionY,radius * 2, radius * 2,startangle1,180);
        g.fillArc((int)positionX,(int)positionY, radius * 2, radius * 2, startangle2,-180);
    } //draws pacman

}
