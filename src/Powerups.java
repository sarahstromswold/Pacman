import java.awt.Color;
import java.awt.Graphics;

public class Powerups {
    //creates powerups array the same length of maze array that represents the powerups in the maze
    public static int[][] powerUps = new int[15][25];
    int type;
    
    public Powerups() {
	for (int i = 0; i < 15; i++) {
	    for (int j = 0; j < 25; j++) {
		powerUps[i][j] = 0;
	    }
	}
	this.powerUps = powerUps;
    }

    //adds normal points to the array
    //draws normal points
    public void drawNormal (int i, int j, Graphics g) {
	g.setColor(Color.WHITE);
	g.fillOval(((j * 32) + 50 + 14), (500 - ((14 - i) * 32) + 14), 4, 4);
	powerUps[i][j] = 1;
    }
    
    //adds and draws wallwalk powerups
    public void drawWalls(int i, int j, Graphics g) {
	g.setColor(new Color(150, 235, 209));
	g.fillRoundRect(((j * 32) + 50 + 11), (500 - ((14 - i) * 32) + 11), 10, 10, 2, 2);
	powerUps[i][j] = 2;
    }

    //adds and draws kill powerups
    public void drawKill(int i, int j, Graphics g) {
	g.setColor(new Color(250, 225, 239));
	g.fillRoundRect(((j * 32) + 50 + 11), (500 - ((14 - i) * 32) + 11), 10, 10, 2, 2);
	powerUps[i][j] = 3;
    }

    
    public void prints() {
	for (int i = 0; i < 15; i++) {
	    System.out.print(i + "   ");
	    for (int j = 0; j < 25; j++) {
		if (powerUps[i][j] > 100) {
		    System.out.print(powerUps[i][j] + " ");
		}
		else {
		    System.out.print(powerUps[i][j] + "   ");
		}
	    }
	    System.out.println("");
	}
    }
    
}
