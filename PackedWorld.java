/** A class that represents a Game of Life where the underlying board is represented by a single variable of type long.
 * This is done to have a more efficient memory usage. Can represent up to 64 cells. Thus, this class is fit for boards
 * of size 8-by-8 or smaller.
 */

public class PackedWorld extends World implements Cloneable {
    private long world;

    /** A constructor for PackedWorld. Initialises the state of the class according to the Pattern object passed to it. */
    public PackedWorld(Pattern pattern) {
        super(pattern);
        pattern.initialise(this);
    }

    /** A copy constructor to make a deep copy of otherPackedWorld. */
    public PackedWorld(PackedWorld otherPackedWorld) {
        super(otherPackedWorld);
        this.world = otherPackedWorld.world;
    }

    @Override
    /** Overrides clone method of Object to make a deep copy of PackedWorld. */
    public Object clone() {
        PackedWorld copy = (PackedWorld) super.clone();
        return copy;
    }

    @Override
    // Accessor method for a specific cell
    public boolean getCell(int col, int row) {
        if (row < 0 || row >= getHeight()) {
            return false;
        }
        if (col < 0 || col >= getWidth()) {
            return false;
        }
        if (((world >>> (row * getWidth() + col)) & 1L) == 1L) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    // Mutator method for a specific cell
    public void setCell(int col, int row, boolean value) {
        if (row < 0 || row >= getHeight()) {
            System.out.println("Invalid row number");
        } else if (col < 0 || col >= getWidth()) {
            System.out.println("Invalid column number");
        } else if (value) {
            world = (1L << (row * getWidth() + col)) | world;
        } else {
            world = ~(1L << (row * getWidth() + col)) & world;
        }
    }

    @Override
    /** Updates the board to the next generation */
    protected void nextGenerationImpl() {
        long sample = 0L;

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (computeCell(j, i)) {
                    sample = (1L << (i * getWidth() + j)) | sample;
                } else {
                    sample = ~(1L << (i * getWidth() + j)) & sample;
                }
            }
        }
        world = sample;
    }
}
