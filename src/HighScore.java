import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HighScore {
    PacMan pacman;

    public HighScore(PacMan pacman) {
        this.pacman = pacman;
    }
    public void saveHighScore(String fileName){
        int highscore = 0;
        highscore = loadHighScore("Highscore.txt");
        try{
            PrintWriter writer = new PrintWriter(fileName);
            if(pacman.numPoints > highscore) {
                writer.write(pacman.numPoints + " ");
            }//save pacman's current score if it is higher than the saved high score
            else{
                writer.write(highscore + " ");
            }
            writer.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Badness in savePointToFile");
            System.err.println(e);
        }
    }
    public int loadHighScore(String fileName){
        int highscore = 0;

        try{
            Scanner s = new Scanner(new File(fileName));
            while(s.hasNextInt()) {
                highscore = s.nextInt();
                s.nextLine();
            } //loads high score
            s.close();
        }
        catch (Exception e){
            System.out.println("Badness in loadPointFromFile");
            System.err.println(e);
        }
        return highscore; //returns highscore in terminal
        }
}

