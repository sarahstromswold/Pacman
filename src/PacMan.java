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
    int tileX;
    int tileY;
    int tileDir;
    double posXi;
    double posYi;
    int numPoints;
    int lastTileDir;
    int nextTileDir;
    boolean wallWalk;
    int numMoved;
    boolean eatFire;
    
    public PacMan() {
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
	tileDir = 1;
	posXi = positionX;
	posYi = positionY;
	numPoints = 0;
	lastTileDir = 0;
	nextTileDir = 0;
	wallWalk = false;
	numMoved = 0;
	eatFire = false;
    } //pacman constructor

    public void centerPac() {
	//change for opp dir
	
	positionX = ((tileX * 32) + 54);
	
	positionY = (503 - ((14 - tileY) * 32));

    }//centers pacman in the tile

    public boolean isCentered() {
	if ((positionX - 54) % 32 == 0 && (503 - positionY) % 32 == 0) {
	    return true;
	}
	
	else {
	    return false;
	}//checks to see if pacman is centered by checking x and y position
    }
    
    public void update(double time, Maze m) {
	//maybe make it not this same formula for posX--while abs.value of position has not changed by 32
	if (tileDir == 0) {
	    centerPac();
	}
	
	if (tileX < 0 || tileX > 24) {
	    lastTileDir = tileDir;
	    tileDir = 0;
	}
	if (tileY < 0 || tileY > 14) {
	    lastTileDir = tileDir;
	    tileDir = 0;
	} //updates pacman

	//if the value in maze is zero, increase numPoints, change to not zero,and changes value of normal points in powerups
	if (m.maze[tileY][tileX] == 0) {
	    if (m.p.powerUps[tileY][tileX] == 1) {
		m.p.powerUps[tileY][tileX] = 0;
	    }
	    numPoints++;
	    m.maze[tileY][tileX] = 5;
	} 

	//if powerups is equal to two (square is blue), allow him to walk through walls
	if (m.p.powerUps[tileY][tileX] == 2) {
	    m.p.powerUps[tileY][tileX] = 0;
	    wallWalk = true;
	    numMoved = 0;
	} 

	//if wall walk is true and he has gone 15 squares and is currently not in a square set wall walk to false
	if (wallWalk && numMoved > 15 && m.maze[tileY][tileX] != 1) {
	    wallWalk = false;
	}

	//if square is pink, can eat fire
	if (m.p.powerUps[tileY][tileX] == 3) {
	    m.p.powerUps[tileY][tileX] = 0;
	    eatFire = true;
	    numMoved = 0;
	}

	if (eatFire && numMoved > 15) {
	    eatFire = false;
	}
	
	normalUpdate(m);
        
	//update velocity based on value of tileDir
	updateVel();

	chomp();

	//change the tileDir to next tileDir if pacman is currently centered in the tile
	if (isCentered()){
	    tileDir = nextTileDir;
	}
	positionY = positionY + (velocityY * time);
	positionX = positionX + (velocityX * time);
	
	
    }
    
    public void normalUpdate(Maze m) {
	if(tileDir == 2 || tileDir == 4) {
		horizontal(m);
	}
	if(tileDir == 1 || tileDir == 3) {
		vertical(m);
	}
    }

	
    public void horizontal(Maze m) {
	//try catch with method
	if (tileDir == 2) {
	    if (tileX == 0) {
		lastTileDir = 2;
		tileDir = 0;
	    }
	    if (tileX > 0) {
		if ((m.maze[tileY][tileX - 1] == 1 || m.maze[tileY][tileX - 1] == 2) && !wallWalk) {
		    lastTileDir = 2;
		    tileDir = 0;
		}
		else if (wallWalk && tileX == 15 && tileY > 4 && tileY < 10) {
		    lastTileDir = 2;
		    tileDir = 0;
		}
	    }
	    if (tileX > 0 && tileDir == 2 && Math.abs(posXi - positionX) > 32) {
		posXi = positionX;
		tileX--;
		numMoved++;
		centerPac();
	    }
	}//left
	if (tileDir == 4) {
	    if (tileX == 24) {
		lastTileDir = 4;
		tileDir = 0;
	    }
	    if (tileX < 24) {
		if ((m.maze[tileY][tileX + 1] == 1 || m.maze[tileY][tileX + 1] == 2) && !wallWalk) {
		    lastTileDir = 4;
		    tileDir = 0;
		}
		else if (wallWalk && tileX == 9 && tileY > 4 && tileY < 10) {
		    lastTileDir = 4;
		    tileDir = 0;
		}
	    }
	    if (tileX < 24 && tileDir == 4 && Math.abs(posXi - positionX) > 32) {
		posXi = positionX;
		tileX++;
		numMoved++;
		centerPac();
	    }
	}//right
	
    }
    public void vertical(Maze m) {
	if (tileDir == 1) {
	    if (tileY == 0) {
		lastTileDir = 1;
		tileDir = 0;
	    }
	    if (tileY > 0) {
		if ((m.maze[tileY - 1][tileX] == 1 || m.maze[tileY - 1][tileX] == 2) && !wallWalk) {
		    lastTileDir = 1;
		    tileDir = 0;
		}
		else if (wallWalk && tileY == 10 && tileX > 9 && tileX < 15) {
		    lastTileDir = 1;
		    tileDir = 0;
		}
	    }
	    if (tileY > 0 && tileDir == 1 && Math.abs(posYi - positionY) > 32) {
		posYi = positionY;
		tileY--;
		numMoved++;
		centerPac();
	    }
	} //up
	if (tileDir == 3) {
	    if (tileY == 14) {
		lastTileDir = 3;
		tileDir = 0;
	    }
	    if (tileY < 14) {
		if ((m.maze[tileY + 1][tileX] == 1 || m.maze[tileY + 1][tileX] == 2) && !wallWalk) {
		    lastTileDir = 3;
		    tileDir = 0;
		}
		else if (wallWalk && tileY == 4 && tileX > 9 && tileX < 15) {
		    lastTileDir = 3;
		    tileDir = 0;
		}
	    }
	    if (tileY < 14 && tileDir == 3 && Math.abs(posYi - positionY) > 32) {
		posYi = positionY;
		tileY++;
		numMoved++;
		centerPac();
	    }
	} //down
    }
    
    public void chomp() {
	if (tileDir == 0) {
	    updateAngle(0, lastTileDir);
	}
	else if (startangle1 == 0) {
	    updateAngle(0, tileDir);
	}
	else {
	    updateAngle(1, tileDir);
	}
    } //creates pacman's mouth movements

    public void updateAngle(int operation, int dir) {
	if (operation == 0) {
	    if (dir == 1) {
		startangle1 = 115;
		startangle2 = 65;
	    }
	    if (dir == 2) {
		startangle1 = -25;
		startangle2 = 25;
	    }
	    if (dir == 3) {
		startangle1 = -65;
		startangle2 = -115;
	    }
	    if (dir == 4) {
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
	g.setColor(new Color(250, 225, 239));
    } //draws pacman

    
}
