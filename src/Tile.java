public class Tile{

    int[][] tile = new int[5][5];
    int[][][] tiles = new int[2][5][5];
    
    public Tile(int type) {
	tiles = createTiles();
	tile = tiles[type];
    }

    public int[][][] createTiles() {
	int[][][] holder = new int[2][5][5];
	holder[0] = typeOne();
	holder[1] = typeTwo();
	return holder;
    }
    
    public int[][] typeOne() {
	//creates array and initializes all values as zero, then sets necessary values as one
	int[][] a = new int[5][5];
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		a[i][j] = 0;
	    }
	}
	
	a[0][0] = 1;
	
	for (int j = 2; j < 5; j++) {
	    a[1][j] = 1;
	}

	a[2][0] = 1;
	a[2][1] = 1;
	a[3][3] = 1;
	a[4][1] = 1;
	a[4][3] = 1;
	return a;
    }

    public int[][] typeTwo() {
	//since more parts of this tile are colored in than not, we initialize as one, then specify the zeroes
	int[][] b = new int[5][5];
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		b[i][j] = 1;
	    }
	}

	b[0][1] = 0;
	b[0][3] = 0;

	for (int j = 1; j < 4; j++) {
	    b[1][j] = 0;
	}

	b[2][3] = 0;
	b[2][4] = 0;

	for (int j = 0; j < 4; j++) {
	    b[3][j] = 0;
	}

	
	b[4][1] = 0;
	b[4][3] = 0;
	return b;
    }
}
	
    
