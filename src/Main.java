import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.event.MouseListener;

public class Main extends JPanel implements KeyListener{
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int FPS = 500;
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
        if (c == 'w' && world.pacman.tileY >= 1) { //move up
            //world.pacman.velocityY = -100;
            //world.pacman.velocityX = 0;
            world.pacman.startangle1 = 115;
            world.pacman.startangle2 = 65;
	    world.pacman.tileDir = 1;
	    world.pacman.centerPac();
        }
	
        if (c == 'a' && world.pacman.tileX >= 1) { //move left
            //world.pacman.velocityX = -100;
            //world.pacman.velocityY = 0;
            world.pacman.startangle1 = -25;
            world.pacman.startangle2 = 25; //larger start angle = smaller mouth opening
	    world.pacman.tileDir = 2;
	    world.pacman.centerPac();
        }
	
        if (c == 's' && world.pacman.tileY < 24) { //move down
            //world.pacman.velocityY = 100;
            //world.pacman.velocityX = 0;
            world.pacman.startangle1 = -65;
            world.pacman.startangle2 = -115;
	    world.pacman.tileDir = 3;
	    world.pacman.centerPac();
        }
	
        if (c == 'd' && world.pacman.tileX < 24) { //move right
            //world.pacman.velocityX = 100;
            //world.pacman.velocityY = 0;
            world.pacman.startangle1 = 25;
            world.pacman.startangle2 = -25;
	    world.pacman.tileDir = 4;
	    world.pacman.centerPac();
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
    /*public void mouseClicked(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {
	
    }
    public void mouseExited(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }*/
}

class World {
    int height;
    int width;
    //int numPoints;
    Fire f;
    Maze m = new Maze(50, 500);
    PacMan pacman = new PacMan(m);
    int numLives;
    //Points points;
    //Powerups power;

    public World(int initWidth, int initHeight) {
	width = initWidth;
	height = initHeight;
	int fposX = 406;
	int fposY = 250;
	f = new Fire(fposX, fposY, new Color(157,196,168));
	numLives = 1;
	/*fire = new Fire[5];
	for (int i = 0; i < 5; i++) {
	    fire[i] = new Fire(this, width/2 + i * 20, height/2);
	    }*/
    }

    public void drawWorld(Graphics g) {
	if(numLives == 0) {
            //System.exit(0);
	    Font font = new Font("SansSerif", Font.BOLD, 25);
	    g.setColor(Color.BLUE);
	    g.fillRoundRect(400, 200, 100, 40, 20, 20);
	    g.fillRoundRect(400, 400, 100, 40, 20, 20);
	    g.setColor(Color.WHITE);
	    g.drawString("Play Again", 400, 200);
	    g.drawString("Exit", 400, 400);
            menu(g);
	    

        }
	else {
	    pacman.draw(g); //pacman
	    /*for (int i = 0; i < 5; i++) {
	      fire[i].draw(g);
	      }*/
	    m.colorTiles(g);
	    g.drawRect(50, 52, 800, 480);
	    m.drawPoints(g);
	    Font font = new Font("SansSerif", Font.BOLD, 25);
	    g.setFont(font);
	    g.drawString("Score: " + pacman.numPoints, 410, 40);
	    f.draw(g);
	    int positionX = 40;
	    int positionY = 540;
	    int radius = 6;
	    for(int i = 1; i < numLives; i++) {
		g.setColor(Color.YELLOW);
		g.fillArc((int)positionX + i * 15,(int)positionY,radius * 2, radius * 2,25,180);
		g.fillArc((int)positionX + i * 15,(int)positionY, radius * 2, radius * 2, -25,-180);
	    }
	}
	        
	//draw powerups
    }

    public void update(double time) {
    pacman.update(time, m);
    f.update(time,m);
    if(pacDeath()) {
        pacman = new PacMan(m);
        numLives--;
    }
    
    //update pacman
//    for(int i = 0; i < 5; i++) {
//        fire[i].update(this,time);
//    } fire update
    }
    public boolean pacDeath() {
        if(pacman.tileX == f.tileX && pacman.tileY == f.tileY) {
            return true;
        }
        else {
            return false;
        }
    }
    public void menu(Graphics g) {

    }
    
}
