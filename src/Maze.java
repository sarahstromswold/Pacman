import java.awt.*;
import java.util.Random;
import java.util.*;

public class Maze {
    int[][] maze;
    int x;
    int y;
    int initX;
    int initY;
    
    public Maze(int initX, int initY) {
	maze = new int[25][15];
	randomizeMaze();
      	this.initX = initX;
	this.initY = initY;
	x = initX;
	y = initY;
    }

    public void randomizeMaze() {
	Random rand = new Random();
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 3; j++) {
		int t = rand.nextInt(4);
		int r = rand.nextInt(4);
		if (i == 2 && j == 1) {
		    Tile c = new Tile(4, 0);
		    addTile(c, i, j);
		}
		else {
		    Tile tile1 = new Tile(t, r);
		    addTile(tile1, i, j);
		}
		//maze[(i * 5) + k][(j * 5) + l] = tile[k][l];
	    }
	}
    }
    
    public void colorTiles(Graphics g) {
	for (int i = 24; i >= 0; i--) {
	    y -= 10;
	    x = initX;
	    for (int j = 0; j < 15; j++) {
		if (maze[i][j] == 1) {
		    g.setColor(Color.WHITE);
		    g.fillRect(x, y, 10, 10);
		}
		else if (maze[i][j] == 2) {
		    g.setColor(Color.GRAY);
		    g.fillRect(x, y, 10, 10);
		}
		x += 10;
	    }
	}
    }
    
    public void addTile(Tile t, int r, int c) {
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		maze[(r * 5) + i][(c * 5) + j] = t.tile[i][j];
	    }
	}
    }
}

