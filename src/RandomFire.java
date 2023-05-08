import java.util.*;
import java.awt.*;
import java.util.Random;

public class RandomFire extends Fire{

    public RandomFire(int tileX, int tileY, Color color) {
	super(tileX, tileY, color);
    }

    public void update(double time, Maze m) {
	super.update(time, m);
        if (numTilesMoved == 5) {
            direction(m);
            numTilesMoved = 0;
        }
    }
}
