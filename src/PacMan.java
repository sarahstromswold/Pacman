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
    int tileDir;
    double posXi;
    double posYi;
    int numPoints;
    
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
	tileX = 0;
	tileY = 14;
	tileDir = 0;
	posXi = positionX;
	posYi = positionY;
	numPoints = 0;
    } //pacman constructor

    public void centerPac() {
	positionX = ((tileX * 32) + 54);
	positionY = (503 - ((14 - tileY) * 32));
    }
    
    public void update(double time, Maze m) {
	//maybe make it not this same formula for posX--while abs.value of position has not changed by 32
	System.out.println("tileX at beg" + tileX + "tileY at beg" + tileY);
	System.out.println("tileDir at beg" + tileDir);
	
	if (tileX < 0 || tileX > 24) {
	    tileDir = 0;
	}
	if (tileY < 0 || tileY > 14) {
	    tileDir = 0;
	} //updates pacman

	if (m.maze[tileY][tileX] == 3) {
	    numPoints++;
	    m.maze[tileY][tileX] = 5;
	}
	    
	if (tileDir == 1) {
	    if (tileY == 0) {
		tileDir =0;
	    }
	    if (tileY > 0) {
		if (m.maze[tileY - 1][tileX] == 1) {
		    System.out.println("Setting to zero 1");
		    tileDir = 0;
		}
	    }
	    if (tileY > 0 && tileDir == 1 && Math.abs(posYi - positionY) > 32) {
		posYi = positionY;
		tileY--;
	    }
	}
	
	if (tileDir == 2) {
	    if (tileX == 0) {
		tileDir = 0;
	    }
	    if (tileX > 0) { 
		if (m.maze[tileY][tileX - 1] == 1) {
		    System.out.println("setting to zero 2");
		    tileDir = 0;
		}
	    }
	    if (tileX > 0 && tileDir == 2 && Math.abs(posXi - positionX) > 32) {
		posXi = positionX;
		tileX--;
	    }
	}
	
	if (tileDir == 3) {
	    if (tileY == 14) {
		tileDir = 0;
	    }
	    if (tileY < 14) {
		if (m.maze[tileY + 1][tileX] == 1) {
		    System.out.println("setting to zero 3");
		    tileDir = 0;
		}
	    }
	    if (tileY < 14 && tileDir == 3 && Math.abs(posYi - positionY) > 32) {
		posYi = positionY;
		tileY++;
	    }
	}
	
	if (tileDir == 4) {
	    if (tileX == 24) {
		tileDir = 0;
	    }
	    if (tileX < 24) {
		if (m.maze[tileY][tileX + 1] == 1) {
		    System.out.println("tileY" + tileY);
		    System.out.println("tileX" + tileX);
		    System.out.println("maze at point" + m.maze[tileY][tileX + 1]);
		    System.out.println("setting to zero 4");
		    tileDir = 0;
		}
	    }
	    if (tileX < 24 && tileDir == 4 && Math.abs(posXi - positionX) > 32) {
		posXi = positionX;
		tileX++;
	    }
	}

	updateVel();

	chomp();
	
	positionY = positionY + (velocityY * time);
	positionX = positionX + (velocityX * time);
	
    }

    public void chomp() {
	if (startangle1 == 0) {
	    updateAngle(0);
	}
	else {
	    updateAngle(1);
	}
    }

    public void updateAngle(int operation) {
	if (operation == 0) {
	    if (tileDir == 1) {
		startangle1 = 115;
		startangle2 = 65;
	    }
	    if (tileDir == 2) {
		startangle1 = -25;
		startangle2 = 25;
	    }
	    if (tileDir == 3) {
		startangle1 = -65;
		startangle2 = -115;
	    }
	    if (tileDir == 4) {
		startangle1 = 25;
		startangle2 = -25;
	    }
	}
	else if (operation == 1) {
	    startangle1 = 0;
	    startangle2 = 0;
	}
    }
			   
	    
    private void updateVel() {
	
	if (tileDir == 0) {
	    velocityX = 0;
	    velocityY = 0;
	}
	if (tileDir == 1) {
	    velocityX = 0;
	    velocityY = -100;
	}
	if (tileDir == 2) {
	    velocityX = -100;
	    velocityY = 0;
	}
	if (tileDir == 3) {
	    velocityX = 0;
	    velocityY = 100;
	}
	if (tileDir == 4) {
	    velocityX = 100;
	    velocityY = 0;
	}
	
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc((int)positionX,(int)positionY,radius * 2, radius * 2,startangle1,180);
        g.fillArc((int)positionX,(int)positionY, radius * 2, radius * 2, startangle2,-180);
    } //draws pacman

}
