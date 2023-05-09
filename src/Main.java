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
        } //move pacman up
	
        if (c == 'a' && world.pacman.tileX >= 1) {
            //world.pacman.velocityX = -100;
            //world.pacman.velocityY = 0;
            world.pacman.startangle1 = -25;
            world.pacman.startangle2 = 25; //larger start angle = smaller mouth opening
	    world.pacman.nextTileDir = 2;
        } //move pacman left
	
        if (c == 's' && world.pacman.tileY < 24) {
            //world.pacman.velocityY = 100;
            //world.pacman.velocityX = 0;
            world.pacman.startangle1 = -65;
            world.pacman.startangle2 = -115;
	    world.pacman.nextTileDir = 3;
        } //move pacman down
	
        if (c == 'd' && world.pacman.tileX < 24) {
            //world.pacman.velocityX = 100;
            //world.pacman.velocityY = 0;
            world.pacman.startangle1 = 25;
            world.pacman.startangle2 = -25;
	    world.pacman.nextTileDir = 4;
        } //move pacman right
    }
    class Runner implements Runnable {
        public void run() {
            while (true) {
                world.update(1.0 / (double) FPS);
                repaint();
		if (world.m.noPoints() && world.f[0].tileY != 6 && world.f[0].tileX != 11) {
		    world = new World(world.width, world.height, world.pacman.numPoints, world.numLives);
		}
		//if there are no points left in the map (and it's not the beginning of the game), create
		//a new instance of world, and pass in the current values of lives and points
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
    Fire[] f = new Fire[4];
    Color c;
    Color c2;
    Color c3;
    Color c4;
  
    Maze m = new Maze(50, 500); //maze
    PacMan pacman = new PacMan(); //pacman
    HighScore highscore = new HighScore(pacman);
    int numLives; //pacman lives
    boolean highscoreScreen = false;
    int currHighScore;


    public World(int initWidth, int initHeight) {
	width = initWidth;
	height = initHeight;

	for (int i = 0; i < 4; i++) {
	    f[i] = new RandomFire((int)(11) + i / 2, (int)(6) + i / 2, i);
	}

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
	for (int i = 0; i < 4; i++) {
	    f[i] = new RandomFire((int)(11) + i / 2, (int)(6) + i / 2, i);
	}
	//fire guys
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

	    for (int i = 0; i < 4; i ++) {
		f[i].draw(g);
	    }
	
	    int positionX = 40;
	    int positionY = 540;
	    int radius = 6;
	    for(int i = 1; i < numLives; i++) {
		g.setColor(Color.YELLOW);
		g.fillArc((int)positionX + i * 15,(int)positionY,radius * 2, radius * 2,25,180);
		g.fillArc((int)positionX + i * 15,(int)positionY, radius * 2, radius * 2, -25,-180);
	    } //life counter
	} ///runs game while pacman still has lives

    }

    public void update(double time) {
	pacman.update(time, m); //update pacman
	for (int i = 0; i < 4; i++) {
	    f[i].update(time, this);
	}

	if (pacDeath() && !pacman.eatFire) {
	    int numPoints = pacman.numPoints;
	    pacman = new PacMan();
	    pacman.numPoints = numPoints;
	    numLives--; //decrement pacmans lives when he gets eaten by ghosts
	    pacman.wallWalk = false;
	    if (numLives == 0) {
		highscore.saveHighScore("Highscore.txt");
	    } //save score when pacman dies
	}
	//make into array and reset position with for each
	else if (pacDeath() && pacman.eatFire) {
	    pacman.numPoints += 10;
	    for (int i = 0; i < 4; i++) {
		if (pacman.tileX == f[i].tileX && pacman.tileY == f[i].tileY) {
		    f[i].resetPos();
		}
	    }
	}
    }
    
    public boolean pacDeath() {
	for (int i = 0; i < 4; i++) {
	    if (pacman.tileX == f[i].tileX && pacman.tileY == f[i].tileY) {
		return true;
	    }
	}
	return false;
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
    } //creates a main menu
    
    public void highscore(Graphics g) {
        Font font = new Font("SansSerif", Font.BOLD, 35);
        g.setFont(font);
        g.setColor(Color.RED);
        g.fillRoundRect(650, 450, 200, 90, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("EXIT", 710, 505);
        g.drawString("Highscore: " + currHighScore, 300,50);
    }//creates a menu that you can access through the main menu by clicking high score
    
}
