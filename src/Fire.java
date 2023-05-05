import java.awt.*;
import java.util.Random;
import java.util.*;

public class Fire {
    double positionX;
    double positionY;
    int velocityX;
    int velocityY;
    int radius;
    int roll;
    int tileX;
    int tileY;
    Random rand;
    Color color;
    double positionXi;
    double positionYi;
    int numTilesMoved;

    public Fire(int tileX, int tileY, Color color) {
        rand = new Random();
        
        velocityX = 0;
        velocityY = -100;
	radius = 12;
	this.color = color;
       
        this.tileX = tileX;
	//shifted by 4 in x
	
        this.tileY = tileY;
	//shifted by 6
	positionX = (tileX * 32) + 54;
        positionY = (500 - ((14- tileY) * 32) + 6);
	positionYi = positionY;
        positionXi = positionX;
        numTilesMoved = 0;
    }

    public void update(double time, Maze m) {
        positionX = positionX + (velocityX * time);
        positionY = positionY + (velocityY * time);
        if (numTilesMoved == 5) {
            direction(m);
            numTilesMoved = 0;
        }
        if (velocityY < 0) {
            if (tileY == 0) {
                direction(m);
            }
            else if (tileY > 0) {
                if (m.maze[tileY - 1][tileX] == 1) {
                    direction(m);
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
                direction(m);
            }
            else if (tileX > 0) {
                if (m.maze[tileY][tileX - 1] == 1) {
                    direction(m);
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
                direction(m);
            }
            else if (tileY < 14) {
                if (m.maze[tileY + 1][tileX] == 1 || m.maze[tileY + 1][tileX] == 2) {
                    direction(m);
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
                direction(m);
            }
            else if (tileX < 24) {
                if (m.maze[tileY][tileX + 1] == 1){
                    direction(m);
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
        }
        else if (roll == 2 && velocityY != -100) {
            velocityY = -100;
            velocityX = 0;
        }
        else if (roll == 3 && velocityX != 100) {
            velocityX = 100;
            velocityY = 0;
        }
        else if (roll == 4 && velocityY != 100) {
            velocityX = 0;
            velocityY = 100;
        }
        else {
            direction(m);
        }
    }

}

