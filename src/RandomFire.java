import java.util.*;
import java.awt.*;
import java.util.Random;

public class RandomFire extends Fire{

    public RandomFire(int tileX, int tileY, int color) {
	super(tileX, tileY, color);
    }

    public void update(double time, World w) {
	super.update(time, w);
        if (numTilesMoved == 5) {
            direction(w.m);
            numTilesMoved = 0;
        }
    }
}
