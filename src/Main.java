import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Main extends JPanel implements KeyListener{
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int FPS = 60;
    World world;

    public Main() {
	world = new World (WIDTH, HEIGHT);
    addKeyListener(this);
    Thread mainThread = new Thread(new Runner());
    mainThread.start();
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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

    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'w') {
            world.pacman.velocityY = -100;
            world.pacman.velocityX = 0;
        }
        if (c == 'a') {
            world.pacman.velocityX = -100;
            world.pacman.velocityY = 0;
        }
        if (c == 's') {
            world.pacman.velocityY = 100;
            world.pacman.velocityX = 0;
        }
        if (c == 'd') {
            world.pacman.velocityX = 100;
            world.pacman.velocityY = 0;
        }
    }
    class Runner implements Runnable {
        public void run() {
            while (true) {
                world.update(1.0 / (double) FPS);
                repaint();
                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
}

class World {
    int height;
    int width;
    int numPoints;
    PacMan pacman = new PacMan(this);
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
	pacman.draw(g);
	//draw fire guys
	//draw points
	//draw powerups
    }

    public void update(double time) {
    pacman.update(time);
    }

}
