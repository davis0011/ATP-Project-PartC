package algorithms.mazeGenerators;

import java.util.Objects;

/**
 * Simple class for use in maze generation for positions, not used externally
 * was implemented as a record when we used java18, but it turns out that java15 does not support records
 */
public class GenOrigin {
    public int row;
    public int col;

    /**
     * @param row row in the maze that the GenOrigin represents
     * @param col column in the maze that the GenOrigin represents
     */
    public GenOrigin(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * compares by row and col integer comparison
     * @param o object for comparison
     * @return boolean true if coordinates are the same, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenOrigin genOrigin = (GenOrigin) o;
        return row == genOrigin.row && col == genOrigin.col;
    }
}
