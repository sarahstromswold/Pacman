import java.awt.*;
import java.util.Random;
import java.util.*;

public class Maze {
    Tile t;
    int[][] a;
    int x;
    int y;
    int initX;
    int initY;
    
    public Maze(int initX, int initY) {
        t = new Tile(3);
	a = t.tile;
	this.initX = initX;
	this.initY = initY;
	x = initX;
	y = initY;
    }

    public void colorTile(Graphics g) {
	g.setColor(Color.WHITE);
	for (int i = 0; i < 5; i++) {
	    y += 10;
	    x = initX;
	    for (int j = 0; j < 5; j++) {
		if (a[i][j] == 1) {
		    g.fillRect(x, y, 10, 10);
		}
		x += 10;
	    }
	}
    }
}
