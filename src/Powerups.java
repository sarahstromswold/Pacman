import java.awt.Color;
import java.awt.Graphics;

public class Powerups {
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

    public void drawNormal (int i, int j, Graphics g) {
	System.out.println("here23");
	g.setColor(Color.WHITE);
	g.fillOval(((j * 32) + 50 + 14), (500 - ((14 - i) * 32) + 14), 4, 4);
	powerUps[i][j] = 1;
    }

    public void drawWalls(int i, int j, Graphics g) {
	g.setColor(new Color(150, 235, 209));
	g.fillRoundRect(((j * 32) + 50 + 14), (500 - ((14 - i) * 32) + 14), 4, 4, 1, 1);
	powerUps[i][j] = 2;
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
