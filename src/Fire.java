import java.awt.*;
import java.sql.SQLOutput;
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

    public Fire(int initialPositionX, int initialPositionY, Color color) {
        rand = new Random();
        positionX = initialPositionX;
        positionY = initialPositionY;
        velocityX = 0;
        velocityY = -100;
	    radius = 12;
	    this.color = color;
        positionYi = positionY;
        positionXi = positionX;
        tileX = 11;
        tileY = 6;
    }

    public void update(double time, Maze m) {
        positionX = positionX + (velocityX * time);
        positionY = positionY + (velocityY * time);
        if (velocityY < 0) {
            if (tileY == 0) {
                direction(m);
            }
            else if (tileY > 0) {
                if (m.maze[tileY - 1][tileX] == 1) {
                    direction(m);
                }
            }
            else if (tileY > 0 && Math.abs(positionYi - positionY) > 32) {
                positionYi = positionY;
                tileY--;
            }
        } //fire guy moves up

        if (velocityX < 0) {
            if (tileX == 0) {
                direction(m);
            }
            else if (tileX > 0) {
                if (m.maze[tileY][tileX + 1] == 1) {
                    direction(m);
                }
            }
            else if (tileX > 0 && Math.abs(positionXi - positionX) > 32) {
                positionXi = positionX;
                tileX--;
            }
        } //fire guy moves left

        if (velocityY > 0) {
            if (tileY == 14) {
                direction(m);
            }
            else if (tileY < 14) {
                if (m.maze[tileY + 1][tileX] == 1) {
                    direction(m);
                }
            }
            else if (tileY < 14 && Math.abs(positionYi - positionY) > 32) {
                positionYi = positionY;
                tileY++;
            }
        } //fire guy moves down

        if (velocityX > 0) {
            if (tileX == 24) {
                direction(m);
            }
            else if (tileX < 24) {
                if (m.maze[tileY][tileX + 1] ==1){
                    direction(m);
                }
            }
            else if (tileX < 24 && Math.abs(positionXi - positionX) > 32) {
                positionXi = positionX;
                tileX++;
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
        roll = rand.nextInt(0, 2);
        System.out.println(velocityY);
       if(velocityY < 0) {
           System.out.println(velocityY);
            if(m.maze[tileY][tileX + 1] != 1 && m.maze[tileY][tileX - 1] != 1) {
                if(roll == 0) {
                    velocityX = -100;
                    velocityY = 0;
                }
                else if(roll == 1){
                    velocityX = 100;
                    velocityY = 0;
                }
            }
            else if (m.maze[tileY][tileX + 1] != 1){
                velocityX = 100;
                velocityY = 0;
            }
            else if (m.maze[tileY][tileX - 1] != 1){
                velocityX = 100;
                velocityY = 0;
            }
            else {
                velocityY = 100;
            }
        }
        else if(velocityY > 0) {
            if (m.maze[tileY][tileX + 1] == 0 && m.maze[tileY][tileX - 1] == 0) {
                if (roll == 0) {
                    velocityX = -100;
                    velocityY = 0;
                }
                else if (roll == 1) {
                    velocityX = 100;
                    velocityY = 0;
                }
            }
            else if (m.maze[tileY][tileX + 1] == 0) {
                velocityX = 100;
                velocityY = 0;
            }
            else if (m.maze[tileY][tileX - 1] == 0) {
                velocityX = -100;
                velocityY = 0;
            }
            else {
                velocityY = -100;
            }
        }
        else if(velocityX < 0) {
            if (m.maze[tileY + 1][tileX] == 0 && m.maze[tileY - 1][tileX] == 0) {
                if (roll == 0) {
                    velocityY = -100;
                    velocityX = 0;
                } else if (roll == 1) {
                    velocityY = 100;
                    velocityX = 0;
                }
            } else if (m.maze[tileY + 1][tileX] == 0) {
                velocityY = 100;
                velocityX = 0;
            } else if (m.maze[tileY - 1][tileX] == 0) {
                velocityY = -100;
                velocityX = 0;
            } else {
                velocityX = -100;
            }
        }
        else if(velocityX > 0) {
            if (m.maze[tileY + 1][tileX] == 0 && m.maze[tileY - 1][tileX] == 0) {
                if (roll == 0) {
                    velocityY = -100;
                    velocityX = 0;
                } else if (roll == 1) {
                    velocityY = 100;
                    velocityX = 0;
                }
            } else if (m.maze[tileY + 1][tileX] == 0) {
                velocityY = 100;
                velocityX = 0;
            } else if (m.maze[tileY - 1][tileX] == 0) {
                velocityY = -100;
                velocityX = 0;
            } else {
                velocityX = 100;
            }
        }
    }
}

