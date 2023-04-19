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
	maze = new int[15][25];
	randomizeMaze();
      	this.initX = initX;
	this.initY = initY;
	x = initX;
	y = initY;
    }

    public void randomizeMaze() {
	Random rand = new Random();
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 5; j++) {
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
	x = initX;
	y = initY;
	for (int i = 14; i >= 0; i--) {
	    x = initX;
	    for (int j = 0; j < 25; j++) {
		if (maze[i][j] == 1) {
		    g.setColor(Color.WHITE);
		    g.fillRoundRect(x, y, 32, 32, 10, 10);
		}
		else if (maze[i][j] == 2) {
		    g.setColor(Color.GRAY);
		    g.fillRoundRect(x, y, 32, 32, 10, 10);
		}
		x += 32;
	    }
	    y -= 32;
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

