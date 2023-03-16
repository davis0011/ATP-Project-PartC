package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Position class represents a cells in a Maze object, with row and column coordinates
 */
public class Position implements Serializable{
    private int row;
    private int col;

    /**
     * @param row row in the maze that the Position represents
     * @param col column in the maze that the Position represents
     */
    public Position(int row,int col)
    {
        this.row = row;
        this.col = col;
    }

    /**
     * @return The row field of the Position
     */
    public int getRowIndex(){
        return row;
    }

    /**
     * @return The column field of the Position
     */
    public int getColumnIndex(){
        return col;
    }

    /**
     * @param row the row field to set for the object
     */
    public void setRow(int row)
    {
        this.row = row;
    }

    /**
     * @param col the column field to set for the object
     */
    public void setCol(int col)
    {
        this.col = col;
    }

    /**
     * @return A string representation of the Position object with its field values
     */
    @Override
    public String toString() {
        return
                "[" + row +
                "," + col +
                ']';
    }


    /**
     * @param position The Position we want to check if is diagonal
     * @return true if the argument Position is diagonal to the Position the method was called on
     */
    public boolean diagonalTo(Position position){
        int rowDiff = Math.abs(position.row-row);
        int colDiff = Math.abs(position.col-col);
        if (colDiff != 1 && colDiff != -1) { return false; }
        if (rowDiff != 1 && rowDiff != -1) { return false; }
        return true;
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
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    /**
     * @return default hash for fields by array
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
