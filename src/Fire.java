import java.awt.*;
import java.util.*;

public class Fire {
    double positionX;
    double positionY;
    int velocityX;
    int velocityY;
    int radius;
    int tileX;
    int tileY;

    Color intialcolor;
    Color color;
    double positionXi;
    double positionYi;
    int numTilesMoved;
    int initialPosX;
    int initialPosY;
    int roll;
    Random rand = new Random();

    public Fire(int tileX, int tileY, int i) {
	
	if (i == 0) {
	    color = new Color(157,196,168);
	}
	else if (i == 1) {
	    color = new Color(242, 53, 141);
	}
	else if ( i == 2) {
	    color = new Color(161, 149, 219);
	}
	else if (i == 3) {
	    color = new Color(245, 194, 66);
	}
	
        rand = new Random();
        
        velocityX = 0;
        velocityY = -100;
	radius = 12;
	this.intialcolor = color;
       
        this.tileX = tileX;
	//shifted by 4 in x
	
        this.tileY = tileY;
	//shifted by 6
	positionX = (tileX * 32) + 54;
        positionY = (500 - ((14- tileY) * 32) + 6);
	positionYi = positionY;
        positionXi = positionX;
        numTilesMoved = 0;
	initialPosX = tileX;
	initialPosY = tileY;
    }

    public void update(double time, World w) {
        positionX = positionX + (velocityX * time);
        positionY = positionY + (velocityY * time);
        if (w.pacman.eatFire) {
            color = new Color(rand.nextInt(0,255),rand.nextInt(0,255),rand.nextInt(0,255));
        }
        else {
            color = intialcolor;
        }
        if (velocityY < 0) {
            if (tileY == 0) {
                direction(w.m);
            }
            else if (tileY > 0) {
                if (w.m.maze[tileY - 1][tileX] == 1) {
                    direction(w.m);
                }
            }
            if (tileY > 0 && Math.abs(positionYi - positionY) > 32) {
                positionYi = positionY;
                tileY--;
                numTilesMoved++;
            }
        } //fire guy moves up

        else if (velocityX < 0) {
            if (tileX == 0) {
                direction(w.m);
            }
            else if (tileX > 0) {
                if (w.m.maze[tileY][tileX - 1] == 1) {
                    direction(w.m);
                }
            }
            if (tileX > 0 && Math.abs(positionXi - positionX) > 32) {
                positionXi = positionX;
                tileX--;
                numTilesMoved++;
            }
        } //fire guy moves left

        else if (velocityY > 0) {
            if (tileY == 14) {
                direction(w.m);
            }
            else if (tileY < 14) {
                if (w.m.maze[tileY + 1][tileX] == 1 || w.m.maze[tileY + 1][tileX] == 2) {
                    direction(w.m);
                }
            }
            if (tileY < 14 && Math.abs(positionYi - positionY) > 32) {
                positionYi = positionY;
                tileY++;
                numTilesMoved++;
            }
        } //fire guy moves down

        else if (velocityX > 0) {
            if (tileX == 24) {
                direction(w.m);
            }
            else if (tileX < 24) {
                if (w.m.maze[tileY][tileX + 1] == 1){
                    direction(w.m);
                }
            }
            if (tileX < 24 && Math.abs(positionXi - positionX) > 32) {
                positionXi = positionX;
                tileX++;
                numTilesMoved++;
            }
        } //fire guy moves right

    }

    public void draw(Graphics g) {
	g.setColor(color);
        g.fillArc((int)positionX, (int)positionY, radius * 2, radius * 2, 0, -180);
	int[] xs = {(int)positionX, (int)positionX + 12, (int)positionX + 24};
	int[] ys = {(int)positionY + 12, (int)positionY - 3, (int)positionY + 12};
	g.fillPolygon(xs, ys, 3);
	int[] x2s = {(int)positionX, (int)positionX, (int)positionX + 12};
	int[] y2s = {(int)positionY + 12, (int)positionY + 3, (int)positionY + 12};
	g.fillPolygon(x2s, y2s, 3);
	int[] x3 = {(int)positionX + 24, (int)positionX + 24, (int)positionX + 12};
	int[] y3 = {(int)positionY + 12, (int)positionY + 3, (int)positionY + 12};
	//rainbow when eatable
	g.fillPolygon(x3, y3, 3);
    } //draw fire

    public void direction(Maze m) {
        roll = rand.nextInt(1, 5);
        if (roll == 1 && velocityX != -100) {
            velocityX = -100;
            velocityY = 0;
        } //randomly move left
        else if (roll == 2 && velocityY != -100) {
            velocityY = -100;
            velocityX = 0;
        }//randomly move up
        else if (roll == 3 && velocityX != 100) {
            velocityX = 100;
            velocityY = 0;
        }//randomly move right
        else if (roll == 4 && velocityY != 100) {
            velocityX = 0;
            velocityY = 100;
        }//randomly move down
        else {
            direction(m);
        }
    }

    public void resetPos() {
	tileX = initialPosX;
	tileY = initialPosY;
	positionX = (tileX * 32) + 54;
        positionY = (500 - ((14- tileY) * 32) + 6);
	positionYi = positionY;
        positionXi = positionX;
    }  //when pacman eats the ghosts, they get set back to the box

}

