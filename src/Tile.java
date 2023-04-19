public class Tile{

    int[][] tile = new int[5][5];
    int[][][] tiles = new int[5][5][5];
    
    public Tile(int type, int numRotations) {
	tiles = createTiles();
	tile = tiles[type];
	for (int i = 0 ; i < numRotations; i++) {
	    tile = rotate();
	}
    }

    public int[][][] createTiles() {
	int[][][] holder = new int[5][5][5];
	holder[0] = typeOne();
	holder[1] = typeTwo();
	holder[2] = typeThree();
	holder[3] = typeFour();
	holder[4] = cell();
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
	
	a[0][1] = 1;
	a[0][3] = 1;
	a[1][3] = 1;
	a[2][0] = 1;
	a[2][1] = 1;
	
	for (int j = 2; j < 5; j++) {
	    a[3][j] = 1;
	}
	
	a[4][0] = 1;
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
	
	for (int j = 0; j < 4; j++) {
	    b[1][j] = 0;
	}
	
	b[2][3] = 0;
	b[2][4] = 0;

	for (int j = 1; j < 4; j++) {
	    b[3][j] = 0;
	}
	
	b[4][1] = 0;
	b[4][3] = 0;
	return b;
    }
    
    public int[][] typeThree() {
	int[][] c = new int[5][5];
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		c[i][j] = 1;
	    }
	}
	c[0][2] = 0;
	
	for (int j = 0; j < 5; j++) {
	    c[1][j] = 0;
	}
	
	c[2][1] = 0;
	c[2][4] = 0;
	
	for (int j = 1; j < 4; j++) {
	    c[3][j] = 0;
	}
	
	c[4][1] = 0;
	c[4][3] = 0;
	c[4][4] = 0;
	return c;
    }

    public int[][] typeFour() {
	int[][] d = new int[5][5];
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		d[i][j] = 1;
	    }
	}

	for (int j = 2; j < 5; j++) {
	    d[0][j] = 0;
	}
	d[1][2] = 0;

	for (int j = 0; j < 5; j++) {
	    d[2][j] = 0;
	}

	d[3][2] = 0;
	
	for (int j = 0; j < 3; j++) {
	    d[4][j] = 0;
	}

	return d;
    }

    public int[][] cell() {
	int[][] s = new int[5][5];
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		if (i == 4) {
		    s[i][j] = 1;
		}
		else {
		    s[i][j] = 0;
		}
	    }
	}
	
	for (int i = 0; i < 4; i++) {
	    s[i][0] = 1;
	    s[i][4] = 1;
	}
	
	for (int j = 1; j < 4; j++) {
	    s[0][j] = 2;
	}
	return s;
    }
    
    public int[][] rotate() {
	int[][] temp = new int[5][5];
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
	        temp[4 - j][i] = tile[i][j];
	    }

	}
	return temp;
    }
}

