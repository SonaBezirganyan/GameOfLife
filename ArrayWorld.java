public class ArrayWorld extends World implements Cloneable {
    private boolean[][] world;      // represents the board
    private boolean[] deadRow;

    /** A constructor for ArrayWorld. Initialises the states of the class according to the Pattern object passed to it. */
    public ArrayWorld(Pattern pattern) {
        super(pattern);
        world = new boolean[getHeight()][getWidth()];
        deadRow = new boolean[getWidth()];
        int check = 0;
        pattern.initialise(this);
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                if (world[i][j]) {
                    check++;
                }
            }
            if (check == 0) {
                world[i] = deadRow;
            }
        }
    }

    /** A copy constructor to make a deep copy of otherArrayWorld. */
    public ArrayWorld(ArrayWorld otherArrayWorld) {
        super(otherArrayWorld);
        world = cloneArray(otherArrayWorld.world);
        deadRow = otherArrayWorld.deadRow;
    }

    @Override
    /** Overrides clone method of Object to make a deep copy of ArrayWorld. */
    public Object clone() {
        ArrayWorld copy = (ArrayWorld) super.clone();
        copy.world = cloneArray(world);
        int check = 0;
        for (int i = 0; i < copy.world.length; i++) {
            for (int j = 0; j < copy.world[i].length; j++) {
                if (copy.world[i][j]) {
                    check++;
                }
            }
            if (check == 0) {
                copy.world[i] = deadRow;
            }
        }
        return copy;
    }

    /**
     * A method to return an independent copy of a 2D array of booleans.
     * @param matrix a 2D array containing boolean values
     * @return a deep copy of matrix
     */
    public static boolean[][] cloneArray(boolean[][] matrix) {
        int length = matrix.length;
        boolean[][] target = new boolean[length][matrix[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(matrix[i], 0, target[i], 0, matrix[i].length);
        }
        return target;
    }

    @Override
    // Accessor method for a specific cell
    public boolean getCell(int c, int r) {
        if (r < 0 || r >= getHeight()) {
            return false;
        }
        if (c < 0 || c >= getWidth()) {
            return false;
        }
        return world[r][c];
    }

    @Override
    // Mutator method for a specific cell
    public void setCell(int col, int row, boolean val) {
        if (row < 0 || row >= getHeight()) {
            System.out.println("Invalid row number");
        } else if (col < 0 || col >= getWidth()) {
            System.out.println("Invalid column number");
        } else {
            world[row][col] = val;
        }
    }

    @Override
    /** Updates the board to the next generation */
    protected void nextGenerationImpl() {
        boolean[][] sample = new boolean[getHeight()][getWidth()];

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (computeCell(j, i)) {
                    sample[i][j] = true;
                }
            }
        }
        world = sample;
    }
}
