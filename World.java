public abstract class World implements Cloneable {
    private int generation;     // a counter to keep track of how many generations we've passed through
    private Pattern pattern;

    // A constructor for World
    public World(Pattern pattern) {
        this.pattern = pattern;
        generation = 0;
    }

    // A copy constructor for World
    public World(World otherWorld) {
        if (otherWorld == null) {
            System.out.println("Failed to copy");
            System.exit(0);
        }
        this.generation = otherWorld.generation;
        this.pattern = otherWorld.pattern;
    }

    @Override
    /** Overrides clone method of Object to make a deep copy of World. */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    // Accessor method for the width of the board
    public int getWidth() {
        return pattern.getWidth();
    }

    // Accessor method for the height of the board
    public int getHeight() {
        return pattern.getHeight();
    }

    // Accessor method for the generation count
    public int getGenerationCount() {
        return generation;
    }

    // Method to increment the generation count by 1
    protected void incrementGenerationCount() {
        generation++;
    }

    // Method to decrement the generation count by 1
    protected void decrementGenerationCount() {
        generation--;
    }

//    // Accessor method for pattern
//    protected Pattern getPattern() {
//        return pattern;
//    }

    // A method to update the board to the next generation and increment the generation count
    public void nextGeneration() {
        nextGenerationImpl();
        generation++;
    }

    // A method to update the board to the next generation
    protected abstract void nextGenerationImpl();

    // Accessor method for a specific cell
    public abstract boolean getCell(int c, int r);

    // Mutator method for a specific cell
    public abstract void setCell(int col, int row, boolean val);

    /** Counts the number of alive neighbors of the cell with coordinates x and y */
    protected int countNeighbours(int x, int y) {
        int numberOfAliveMembers = 0;
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if ((i != y || j != x) && getCell(j, i)) {
                    numberOfAliveMembers++;
                }
            }
        }
        return numberOfAliveMembers;
    }

    /** Determines whether the cell with coordinates x and y will be alive or dead in the next generation,
     * based on the rules of the game. */
    protected boolean computeCell(int x, int y) {
        int aliveMembers = countNeighbours(x, y);
        if (getCell(x, y)) {    // the cell is alive
            if (aliveMembers == 2 || aliveMembers == 3) {
                return true;
            }
            return false;
        }
        return aliveMembers == 3;   // the cell is dead
    }
}
