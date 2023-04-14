import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Main extends JPanel {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int FPS = 60;
    World world;

    public Main() {
	world = new World (WIDTH, HEIGHT);
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	repaint();
    }
    public static void main(String[] args) {
	JFrame frame = new JFrame("PacMan");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Main game = new Main();
	frame.setContentPane(game);
	frame.pack();
	frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, WIDTH, HEIGHT);

	world.drawWorld(g);
    }
    //need menu method
}

class World {
    int height;
    int width;
    int numPoints;
    //PacMan pacman;
    //Fire fire;
    //Points points;
    //Powerups power;

    public World(int initWidth, int initHeight) {
	width = initWidth;
	height = initHeight;
	numPoints = 0;
    }

    public void drawWorld(Graphics g) {
	g.setColor(Color.WHITE);
	//g.drawString("Points: " + numPoints);

	//draw pacman
	//draw fire guys
	//draw points
	//draw powerups
    }
    //update method (double time)
}
