public class Pattern implements Comparable<Pattern> {
    private String name;
    private String author;
    private int width;
    private int height;
    private int startCol;
    private int startRow;
    private String cells;

    /**
     * Initialises all fields of this class using contents of 'format' to determine the correct values
     * @param format a string to specify a certain pattern
     * @throws PatternFormatException
     */
    public Pattern(String format) throws PatternFormatException {

        String[] splitedArr = format.split(":");

        if (format.isEmpty()) {
            throw new PatternFormatException("Please specify a pattern.");
        }
        if (splitedArr.length != 7) {
            throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern(found "
                    + splitedArr.length + ").");
        }

        name = splitedArr[0];
        author = splitedArr[1];

        try {
            width = Integer.parseInt(splitedArr[2]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as anumber ("
                    + splitedArr[2] + " given).");
        }
        try {
            height = Integer.parseInt(splitedArr[3]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the height field as a number ("
                    + splitedArr[3] + " given).");
        }
        try {
            startCol = Integer.parseInt(splitedArr[4]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the startX field as a number ("
                    + splitedArr[4] + " given).");
        }
        try {
            startRow = Integer.parseInt(splitedArr[5]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the startY field as anumber ("
                    + splitedArr[5] + " given).");
        }

        for (int i = 0; i < splitedArr[6].length(); i++) {
            char checkChar = splitedArr[6].charAt(i);
            if (checkChar != '0' && checkChar != '1' && checkChar != ' ') {
                throw new PatternFormatException("Invalid pattern format: Malformed pattern '" + splitedArr[6] + "'.");
            }
            cells = splitedArr[6];
        }
    }

    @Override
    /** Overrides the compareTo method to compare patterns according to their names. */
    public int compareTo(Pattern p) {
        return name.compareTo(p.getName());
    }

    // Accessor method for name
    public String getName() {
        return name;
    }

    // Accessor method for author
    public String getAuthor() {
        return author;
    }

    // Accessor method for width
    public int getWidth() {
        return width;
    }

    // Accessor method for height
    public int getHeight() {
        return height;
    }

    // Accessor method for startCol
    public int getStartCol() {
        return startCol;
    }

    // Accessor method for startRow
    public int getStartRow() {
        return startRow;
    }

    // Accessor method for cells
    public String getCells() {
        return cells;
    }

    @Override
    /** Overrides the toString method of Object to get the appropriate String representation of Pattern */
    public String toString() {
        String str = name + " (" + author + ")";
        return str;
    }

    /** Initialises 'world' according to 'cells'. */
    public void initialise(World world) {
        String[] split = cells.split(" ");
        for (int i = 0; i < split.length; i++) {
            for (int j = 0; j < split[i].length(); j++) {
                if (split[i].charAt(j) == '1') {
                    world.setCell(getStartCol() + j, getStartRow() + i, true);
                }
            }
        }
    }
}
