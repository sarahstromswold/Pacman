import java.awt.*;
import java.util.Random;
import java.util.*;

public class Fire {
    int positionX;
    int positionY;
    int velocityX;
    int velocityY;
    int radius;
    Random rand;
    Color color;
    
    public Fire(int initialPositionX, int initialPositionY) {
        rand = new Random();
        positionX = initialPositionX;
        positionY = initialPositionY;
        velocityX = 0;
        velocityY = 0;
	radius = 12;
	color = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
    }

//    public void update(double time) {
//        positionX = positionX + (velocityX * time);
//        positionY = positionY + (velocityY * time);
//    }

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

