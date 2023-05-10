import java.awt.*;
import java.util.Random;
import java.util.*;

public class Maze {
    int[][] maze;
    Powerups p;
    int x;
    int y;
    int initX;
    int initY;
    int done;
    
    public Maze(int initX, int initY) {
	maze = new int[15][25];
	p = new Powerups();
	randomizeMaze();
	done = 0;
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
		if (i == 2 && j == 0) {
		    Tile tile0 = new Tile(t, r);
		    while(tile0.tile[4][0] != 0) {
			while (t == 1) {
			    t = rand.nextInt(4);
			    tile0 = new Tile(t, r);
			}
			tile0.tile = tile0.rotate();
		    }
		    addTile(tile0, i, j);
		    //adds first tile to maze, but makes sure bottom right corner is free for pacman to spawn
		}
		else if (i == 1 && j == 2) {
		    Tile c = new Tile(4, 0);
		    addTile(c, i, j);
		    //if it's the center, call the cell tile
		}
		else {
		    Tile tile1 = new Tile(t, r);
		    addTile(tile1, i, j);
		}
	    }
	}
	while(!checkMaze()) {
	    fixMaze();
	}
	lastTile();
	lastTile();
	//fixes maze as many times as check maze is false, then fixes two lastTiels (there seems to be one vertical and one horizontal issue otherwise)
    }
    
    public void colorTiles(Graphics g) {
	x = initX;
	y = initY;
	for (int i = 14; i >= 0; i--) {
	    x = initX;
	    for (int j = 0; j < 25; j++) {
		if (maze[i][j] == 1) {
		    g.setColor(new Color(36, 36, 255));
		    g.fillRoundRect(x, y, 32, 32, 10, 10);
		}
		else if (maze[i][j] == 2) {
		    g.fillRoundRect(x, y, 32, 32, 10, 10);
		}
		//4 is the center cell values and 5 is the tiles that have had points eaten by pacman
		else if (maze[i][j] > 2 && maze[i][j] != 4 && maze[i][j] != 5) {
		    maze[i][j] = 0;
		}
		//if it would be a powerup (if it's not a block (1) or a ghost cell wall (2), set to zero (it can't be a center of the cell(4) or a block that used to be a powerup (5))
		x += 32;
	    }
	    y -= 32;
	}
    }

    //make this boolean, and return false to end game
    public void drawPoints(Graphics g) {
	// Random rand = new Random();
	// int r = rand.nextInt(20, 31);
	maze[14][0] = 5;
	p.powerUps[14][0] = 0;
	int numPlaced = 0;
	for (int i = 0; i < 15; i++) {
	    for (int j = 0; j < 25; j++) {
		if (maze[i][j] == 0) {
		    if (done == 0) {
			
			//randomly generate
			if (numPlaced != 20 && numPlaced != 40) {
			    p.drawNormal(i, j, g);
			    numPlaced++;
			}
			//draws normal points if != 20 or 40
			else if (numPlaced == 20) {
			    p.drawKill(i, j, g);
			    numPlaced++;
			}
			else if (numPlaced == 40) {
			    p.drawWalls(i, j, g);
			    numPlaced = 0;
			}
			maze[i][j] = 3;
		    }
		    else {
			if (p.powerUps[i][j] == 1) {
			    p.drawNormal(i, j, g);
			}
			else if (p.powerUps[i][j] == 2) {
			    p.drawWalls(i, j, g);
			}
			else if (p.powerUps[i][j] == 3){
			    p.drawKill(i, j, g);
			}
		    }
		    //if not generating the points for the first time, then color them in according to their values in the powerups array
		}
	    }
	}
	done++;
    }
		   
    
    public void addTile(Tile t, int r, int c) {
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		maze[(r * 5) + i][(c * 5) + j] = t.tile[i][j];
	    }
	    //add tile to the maze array
	}
    }

    public boolean checkMaze() {
	//starts at i = 14 bc we know that square is open for pacman
	maze[14][0] = 200;
	int k = 199;
	//boolean moved = true;
	while(k > 2) {
	    for (int i = 14; i >= 0; i--) {
		for (int j = 0; j < 25; j++) {
		    for (int r = i + 1; r > i - 2; r--) {
			for (int c = j - 1; c < j + 2; c++) {
			    if (c >= 0 && c < 25 && r >= 0 && r < 15) {
				if ((c == j - 1 && r == i + 1) || (c == j - 1 && r == i - 1) || (c == j + 1 && r == i + 1) || (c == j + 1 && r == i - 1)) {
				    continue;
				}
				else if (maze[r][c] == k + 1 && maze[i][j] == 0) {
				    maze[i][j] = k;
				}
			    }
			    //at every index in the maze, check every square around it and see (as long as in bounds), if a square around it equals the last value of k, set this square equal to the new k, and decrement k
			}
		    }
		}
	    }
	    k--;
	}
	boolean zero = true;
	
	for (int i = 0; i < 15; i++) {
	    for (int j = 0; j < 25; j++) {
		if (i > 4 && i < 10 && j > 9 && j < 15) {
		    continue;
		}
		else if (maze[i][j] == 0) {
		    zero = false;
		}
		//checks to see if there are any zeroes
	    }
	}
	return zero;
    }

    public void fixMaze() {
	//looks around the wall with a zero and a "number" (value greater than 2) and removes the one with the smallest number
	int smX = 0;
	int smY = 14;
	int current = 201;
	boolean zero = false;
	boolean number = false;	
	for (int i = 13; i > 0; i--) {
	    for (int j = 1; j < 24; j++) {
		zero = false;
		number = false;
		if (i > 4 && i < 10 && j > 9 && j < 15) {
		    continue;
		}
		else if (maze[i][j] == 1) {
		    for (int r = i + 1; r >= i - 1; r--) {
			for (int c = j - 1; c <= j + 1; c++) {
			    if (maze[r][c] == 0) {
				zero = true;
			    }
			}
		    }
		  
		    for (int r = i + 1; r >= i - 1; r--) {
			for (int c = j - 1; c <= j + 1; c++) {

			    if (maze[r][c] > 2 && maze[r][c] < current && zero == true) {
				current = maze[r][c];
				number = true;
			    }
			}
		    }
		}
		if (zero == true && number == true) {
		    smY = i;
		    smX = j;
		}
	    }
	}
	maze[smY][smX] = 0;
    }

    public void lastTile() {
	//keeps track of the largest difference on two sides of a tile
	//then it removes the tile that has this difference
	int lgDiff = 0;
	int remX = 0;
	int remY = 14;
	for (int i = 1; i < 14; i++) {
	    for (int j = 1; j < 24; j++) {
		if (Math.abs(maze[i][j-1] - maze[i][j + 1]) > lgDiff && maze[i][j - 1] > 4 && maze[i][j + 1] > 4) {
		    lgDiff = Math.abs(maze[i][j-1] - maze[i][j + 1]);
		    remX = j;
		    remY = i;
		}
		if (Math.abs(maze[i + 1][j] - maze[i - 1][j]) > lgDiff && maze[i + 1][j] > 4 && maze[i - 1][j] > 4){
		    lgDiff = Math.abs(maze[i + 1][j] - maze[i - 1][j]);
		    remX = j;
		    remY = i;
		}		    
	    }
	}
	maze[remY][remX] = 0;
    }
			
    
    public void prints() {
	for (int i = 0; i < 15; i++) {
	    System.out.print(i + "   ");
	    for (int j = 0; j < 25; j++) {
		if (maze[i][j] > 100) {
		    System.out.print(maze[i][j] + " ");
		}
		else {
		    System.out.print(maze[i][j] + "   ");
		}
	    }
	    System.out.println("");
	}
    }

    public boolean noPoints() {
	boolean noPoint = true;
	for (int i = 0; i < 15; i++) {
	    for (int j = 0; j < 25; j++) {
		if (maze[i][j] == 0) {
		    noPoint = false;
		}
	    }
	    //if there are no points left in the maze return true
	}
	return noPoint;
    }
}

