import java.awt.*;
import java.util.Random;
import java.util.*;

public class Fire {
    int positionX;
    int positionY;
    int velocityX;
    int velocityY;
    int radius;
    int roll;
    int tileX;
    int tileY;
    Random rand;
    Color color;
    
    public Fire(int initialPositionX, int initialPositionY, Color color) {
        rand = new Random();
        positionX = initialPositionX;
        positionY = initialPositionY;
        velocityX = 0;
        velocityY = -100;
	    radius = 12;
	    this.color = color;
        tileX = 11;
        tileY = 6;
    }

    public void update(double time, World world) {
        positionX = positionX + (int)(velocityX * time);
        positionY = positionY + (int)(velocityY * time);
        roll = rand.nextInt(0,2);
        if (world.m.maze[tileY][tileX] == 1) {
            velocityY = 0;
            velocityX = 0;
        }
	

    }

    public void draw(Graphics g) {
	g.setColor(color);
        g.fillArc((int)positionX, (int)positionY, radius * 2, radius * 2, 0, -180);
	int[] xs = {positionX, positionX + 12, positionX + 24};
	int[] ys = {positionY + 12, positionY - 3, positionY + 12};
	g.fillPolygon(xs, ys, 3);
	int[] x2s = {positionX, positionX, positionX + 12};
	int[] y2s = {positionY + 12, positionY + 3, positionY + 12};
	g.fillPolygon(x2s, y2s, 3);
	int[] x3 = {positionX + 24, positionX + 24, positionX + 12};
	int[] y3 = {positionY + 12, positionY + 3, positionY + 12};
	//rainbow when eatable
	g.fillPolygon(x3, y3, 3);
    } //draw fire
}

