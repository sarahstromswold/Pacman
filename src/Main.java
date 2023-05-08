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
	    world.pacman.nextTileDir = 1;
        } //move up
	
        if (c == 'a' && world.pacman.tileX >= 1) {
            //world.pacman.velocityX = -100;
            //world.pacman.velocityY = 0;
            world.pacman.startangle1 = -25;
            world.pacman.startangle2 = 25; //larger start angle = smaller mouth opening
	    world.pacman.nextTileDir = 2;
        } //move left
	
        if (c == 's' && world.pacman.tileY < 24) {
            //world.pacman.velocityY = 100;
            //world.pacman.velocityX = 0;
            world.pacman.startangle1 = -65;
            world.pacman.startangle2 = -115;
	    world.pacman.nextTileDir = 3;
        } //move down
	
        if (c == 'd' && world.pacman.tileX < 24) {
            //world.pacman.velocityX = 100;
            //world.pacman.velocityY = 0;
            world.pacman.startangle1 = 25;
            world.pacman.startangle2 = -25;
	    world.pacman.nextTileDir = 4;
        } //move right
    }
    class Runner implements Runnable {
        public void run() {
            while (true) {
                world.update(1.0 / (double) FPS);
                repaint();
		if (world.m.noPoints() && world.f.tileY != 6 && world.f.tileX != 11) {
		    System.out.println("here");
		    world = new World(world.width, world.height, world.pacman.numPoints, world.numLives);
		}
                try {
                    Thread.sleep(1000 / FPS);
                }
		catch (InterruptedException e) {
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
        int x = e.getX();
        int y = e.getY();
        if(world.highscoreScreen) {
            if(x > 650 && x < 850 && y > 450 && y < 540) {
                world.highscoreScreen = false;
            } //exit button
        }
        else {
            if (x > 350 && x < 550) {
                if (y >= 50 && y <= 140) {
                    Main main = new Main();
                    main.main(null);
                } //Play again button
                else if (y >= 250 && y <= 340) {
                    world.highscoreScreen = true;
                    world.currHighScore = world.highscore.loadHighScore("Highscore.txt");
                } //High score button
                else if (y >= 450 && y <= 540) {
                    System.exit(0);
                } //Quit button
            }
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
    Fire f2;
    Fire f3;
    Maze m = new Maze(50, 500); //maze
    PacMan pacman = new PacMan(m); //pacman
    HighScore highscore = new HighScore(pacman);
    int numLives; //lives
    boolean highscoreScreen = false;
    int currHighScore;

    //Powerups power;

    public World(int initWidth, int initHeight) {
	width = initWidth;
	height = initHeight;
	int fposX = 406;
	int fposY = 250;
	f = new RandomFire(11, 6, new Color(157,196,168));
	f2 = new RandomFire(11, 7, new Color(242, 53, 141));
	f3 = new RandomFire(12, 7, new Color(161, 149, 219));
	//fire guy
	numLives = 3; //amount of lives
	/*fire = new Fire[5];
	for (int i = 0; i < 5; i++) {
	    fire[i] = new Fire(this, width/2 + i * 20, height/2);
	    }*/
    }

    public World(int initWidth, int initHeight, int numPoints, int numLives) {
	width = initWidth;
	height = initHeight;
	int fposX = 406;
	int fposY = 250;
	f = new Fire(11, 6, new Color(157,196,168));
	f2 = new Fire(11, 7, new Color(242, 53, 141));
	f3 = new Fire(12, 7, new Color(161, 149, 219));
	//fire guy
	this.numLives = numLives;
	pacman.numPoints = numPoints;
    }
    public void drawWorld(Graphics g) {

	if(numLives == 0) {
	    if (highscoreScreen) {
		highscore(g);
	    }
	    else {
		menu(g);
	    }
	} //when out of lives, menu pops up

	else {
	    pacman.draw(g); //pacman
	    /*for (int i = 0; i < 5; i++) {
	      fire[i].draw(g);
	      }*/
	    
	    m.colorTiles(g); //tiles
	    g.drawRect(50, 52, 800, 480);
	    m.drawPoints(g);
	     //points
	    Font font = new Font("SansSerif", Font.BOLD, 25);
	    g.setFont(font);
	    g.setColor(Color.WHITE);
	    g.drawString("Score: " + pacman.numPoints, 410, 40);
	    f.draw(g);
	    f2.draw(g);
	    f3.draw(g);//fire guy
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
	f.update(time,m);
	f2.update(time,m);
	f3.update(time, m);//update fireguys
	if (pacDeath() && !pacman.eatFire) {
	    int numPoints = pacman.numPoints;
	    pacman = new PacMan(m);
	    pacman.numPoints = numPoints;
	    numLives--;
	    pacman.wallWalk = false;
	    if (numLives == 0) {
		highscore.saveHighScore("Highscore.txt");
	    }
	}
	//make into array and reset position with for each
	else if (pacDeath() && pacman.eatFire) {
	    if(pacman.tileX == f.tileX && pacman.tileY == f.tileY) {
		f.resetPos();
	    }
	    else if (pacman.tileX == f2.tileX && pacman.tileY == f2.tileY){
		f2.resetPos();
	    }
	    else if (pacman.tileX == f3.tileX && pacman.tileY == f3.tileY){
		f3.resetPos();
	    }
	}
    }
    
    public boolean pacDeath() {
        if((pacman.tileX == f.tileX && pacman.tileY == f.tileY) || (pacman.tileX == f2.tileX && pacman.tileY == f2.tileY) || (pacman.tileX == f3.tileX && pacman.tileY == f3.tileY)) {
            return true;
        }
        else {
            return false;
        }
    } //resets pacman when he dies
    
    public void menu(Graphics g) {
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
    
    public void highscore(Graphics g) {
        Font font = new Font("SansSerif", Font.BOLD, 35);
        g.setFont(font);
        g.setColor(Color.RED);
        g.fillRoundRect(650, 450, 200, 90, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("EXIT", 710, 505);
        g.drawString("Highscore: " + currHighScore, 300,50);
    }
    
}
