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
        initialPositionX = 54;
        initialPositionY = 503;
        positionX = 54;
        positionY = 503;
        velocityX = 0;
        velocityY = 0;
        radius = 12;
        startangle1 = 25;
        startangle2 = -25;
        
        System.out.println(tileY);
    } //pacman constructor

    public void update(double time, Maze m) {
        positionX = positionX + (velocityX * time);
        positionY = positionY + (velocityY * time);
	/*if (positionX > 820 || positionX < 52) {
	    velocityX = 0;
	}
	if (positionY > 503 || positionY < 56) {
	    velocityY = 0;
	    }*/ //updates pacman
	tileX = (int)((positionX - 2) - m.initX) / 32;
        tileY = ((int)(positionY - (m.initY - 480)) / 32) - 1;
	/*if (m.maze[tileX][tileY] == 1) {
	    velocityY = 0;
	    velocityX = 0;
	    }*/
        if (velocityY < 0) {
	    if (tileY < 0) {
		velocityY = 0;
	    }
	    else if (m.maze[tileY - 1][tileX] == 1) {
		velocityY = 0;
	    }
	}
	else if (velocityY > 0) {
	    if (tileY > 14) {
		velocityY = 0;
	    }
	    else if (m.maze[tileY + 1][tileX] == 1) {
		velocityY = 0;
	    }
	}
	else if (velocityX > 0) {
	    if (tileX > 24) {
		velocityY = 0;
	    }
	    else if (m.maze[tileY][tileX + 1] == 1) {
		velocityX = 0;
	    }
	}
	else if (velocityX < 0) {
	    if (tileX < 1) {
		velocityX = 0;
	    }
	    else if (m.maze[tileY][tileX - 1] == 1) {
		velocityX = 0;
	    }
	}
	//System.out.println("TileX: " + tileX + "TileY " + tileY);
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc((int)positionX,(int)positionY,radius * 2, radius * 2,startangle1,180);
        g.fillArc((int)positionX,(int)positionY, radius * 2, radius * 2, startangle2,-180);
    } //draws pacman

}
