import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.event.MouseListener;

public class Main extends JPanel implements KeyListener, MouseListener{
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int FPS = 500;
    World world;

    public Main() {
	world = new World (WIDTH, HEIGHT);
	addKeyListener(this);
    addMouseListener(this);
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

    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'w' && world.pacman.tileY >= 1) {
            //world.pacman.velocityY = -100;
            //world.pacman.velocityX = 0;
            world.pacman.startangle1 = 115;
            world.pacman.startangle2 = 65;
	    world.pacman.tileDir = 1;
	    world.pacman.centerPac();
        } //move up
	
        if (c == 'a' && world.pacman.tileX >= 1) {
            //world.pacman.velocityX = -100;
            //world.pacman.velocityY = 0;
            world.pacman.startangle1 = -25;
            world.pacman.startangle2 = 25; //larger start angle = smaller mouth opening
	    world.pacman.tileDir = 2;
	    world.pacman.centerPac();
        } //move left
	
        if (c == 's' && world.pacman.tileY < 24) {
            //world.pacman.velocityY = 100;
            //world.pacman.velocityX = 0;
            world.pacman.startangle1 = -65;
            world.pacman.startangle2 = -115;
	    world.pacman.tileDir = 3;
	    world.pacman.centerPac();
        } //move down
	
        if (c == 'd' && world.pacman.tileX < 24) {
            //world.pacman.velocityX = 100;
            //world.pacman.velocityY = 0;
            world.pacman.startangle1 = 25;
            world.pacman.startangle2 = -25;
	    world.pacman.tileDir = 4;
	    world.pacman.centerPac();
        } //move right
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
    public void mouseClicked(MouseEvent e) {
        int x = 0;
        int y = 0;
        int hscore;
        x = e.getX();
        y = e.getY();
        if (x > 350 && x < 550) {
            if(y >= 50 && y <= 140) {
                Main main = new Main();
                main.main(null);
            } //Play again button
            else if(y >= 250 && y <= 340) {
                hscore = world.highscore.loadHighScore("Highscore.txt");
            } //High score button
            else if(y >= 450 && y <= 540) {
                System.exit(0);
            } //Quit button
        }

    } //used for the menu buttons
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }
}

class World {
    int height;
    int width;
    Fire f; //fire guy
    Maze m = new Maze(50, 500); //maze
    PacMan pacman = new PacMan(m); //pacman
    HighScore highscore = new HighScore(pacman);
    int numLives; //lives

    //Powerups power;

    public World(int initWidth, int initHeight) {
	width = initWidth;
	height = initHeight;
	int fposX = 406;
	int fposY = 250;
	f = new Fire(fposX, fposY, new Color(157,196,168)); //fire guy
	numLives = 3; //amount of lives
	/*fire = new Fire[5];
	for (int i = 0; i < 5; i++) {
	    fire[i] = new Fire(this, width/2 + i * 20, height/2);
	    }*/
    }

    public void drawWorld(Graphics g) {
	if(numLives == 0) {
            menu(g);
        } //when out of lives, menu pops up

	else {
	    pacman.draw(g); //pacman
	    /*for (int i = 0; i < 5; i++) {
	      fire[i].draw(g);
	      }*/
	    m.colorTiles(g); //tiles
	    g.drawRect(50, 52, 800, 480);
	    m.drawPoints(g); //points
	    Font font = new Font("SansSerif", Font.BOLD, 25);
	    g.setFont(font);
	    g.drawString("Score: " + pacman.numPoints, 410, 40);
	    f.draw(g); //fire guy
	    int positionX = 40;
	    int positionY = 540;
	    int radius = 6;
	    for(int i = 1; i < numLives; i++) {
		g.setColor(Color.YELLOW);
		g.fillArc((int)positionX + i * 15,(int)positionY,radius * 2, radius * 2,25,180);
		g.fillArc((int)positionX + i * 15,(int)positionY, radius * 2, radius * 2, -25,-180);
	    } //life counter
	}
	        
	//draw powerups
    }

    public void update(double time) {
    pacman.update(time, m); //update pacman
    f.update(time,m); //update fireguys
    if(pacDeath()) {
        pacman = new PacMan(m);
        numLives--;
    } //when he dies, lives decrease
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
    } //resets pacman when he dies
    public void menu(Graphics g) {
        highscore.saveHighScore("Highscore.txt");
        Font font = new Font("SansSerif", Font.BOLD, 35);
        g.setFont(font);
        g.setColor(Color.BLUE);
        g.fillRoundRect(350, 50, 200, 90, 20, 20);
        g.fillRoundRect(350, 250, 200, 90, 20, 20);
        g.fillRoundRect(350, 450, 200, 90, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("Play Again", 357, 110);
        g.drawString("High Score", 354, 310);
        g.drawString("Exit", 415, 510);
    } //creates a menu
    
}
