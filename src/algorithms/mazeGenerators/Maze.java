package algorithms.mazeGenerators;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * the Maze object used in all maze generators and later given to the SearchableMaze class for use in solving mazes
 */
public class Maze implements Serializable {
    /**
     * the matrix representing the maze, a cell has 0 for a path and 1 for a wall
     */
    protected int[][] tiles;
    private int rows;
    private int cols;

    /**
     * the starting Position of the maze, this object will contain the coordinates for the starting location in the tiles field
     * @see Position
     */
    protected Position start;
    /**
     * the starting Position of the maze, this object will contain the coordinates for the starting location in the tiles field
     * @see Position
     */
    protected Position goal;

    /**Constructor for Maze class, illegal arguments will result in a 5x5 maze
     * @param rows the number of rows in the maze
     * @param cols the number of columns in the maze
     */
    public Maze(int rows,int cols) {
        if(rows <= 0){
            rows = 5;
        }
        if(cols <= 0){
            cols = 5;
        }
        tiles = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Maze(byte[] bytes){
        ByteBuffer buff = ByteBuffer.wrap(bytes);
        rows = buff.getInt();
        cols = buff.getInt();
        int startRow = buff.getInt();
        int startCol = buff.getInt();
        int goalRow = buff.getInt();
        int goalCol = buff.getInt();

        tiles = new int[rows][cols];
        start = new Position(startRow,startCol);
        goal = new Position(goalRow, goalCol);
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                tiles[i][j] = (int) buff.get();
            }
        }

    }
    /**Setter method for the value of an indicated index of the maze matrix
     * @param row the row index of the cell that will have its value set
     * @param col the column index of the cell that will have its value set
     * @param val the value to set in the cell
     */
    public void SetInPlace(int row,int col,int val)
    {
        if (val != 1 && val != 0 && val != 2) { return; }
        if (row > tiles.length || row < 0) { return; }
        if (col > tiles[0].length || col < 0) { return; }
        tiles[row][col] = val;
    }

    /**
     * @return Position representing the start of the maze
     */
    public Position getStartPosition()
    {
        return start;
    }

    /**
     * @return Position representing the goal of the maze
     */
    public Position getGoalPosition()
    {
        return goal;
    }

    /**set the Position of the start of the maze
     * @param row the row index of the start position
     * @param col the column index of the start position
     */
    public void setStartPosition(int row, int col)
    {
        start = new Position(row,col);
    }

    /**set the Position of the goal of the maze
     * @param row the row index of the goal position
     * @param col the column index of the goal position
     */
    public void setGoalPosition(int row, int col)
    {
        goal = new Position(row,col);
    }

    /**
     * Prints the maze representation
     */
    public void print(){
        String[][] maze = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = Integer.toString(tiles[i][j]);
            }
        }
        maze[getStartPosition().getRowIndex()][getStartPosition().getColumnIndex()] = "S";
        maze[getGoalPosition().getRowIndex()][getGoalPosition().getColumnIndex()] = "E";
        for (String[] line : maze){
            System.out.println(Arrays.toString(line));
        }

    }

    /**
     * @return the tiles field of the maze
     */
    public int[][] getFullMaze(){
        return tiles;
    }

    /**checker method to see if the position passed is within the bounds of the maze
     * @param row the row index of the tested position
     * @param col the column index of the tested position
     * @return boolean true if index is within bounds of the maze
     */
    private boolean inBounds(int row, int col){
        if (row < 0) return false;
        if (col < 0) return false;
        if (row > rows - 1) return false;
        if (col > cols - 1) return false;
        return true;
    }


    /**Returns the possible neighbours of the cell at the given index
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return a list of Positions containing all Positions reachable with distance 1, including diagonals if a path exists
     */
    public Position[] getNeighbours(int row, int col){
        ArrayList<Position> results = new ArrayList<>();
        if (inBounds(row+1,col)){
            if (tiles[row+1][col] == 0){
                results.add(new Position(row+1,col));
            }
        }
        if (inBounds(row,col+1)){
            if (tiles[row][col+1] == 0){
                results.add(new Position(row,col+1));
            }
        }
        if (inBounds(row-1,col)){
            if (tiles[row-1][col] == 0){
                results.add(new Position(row-1,col));
            }
        }
        if (inBounds(row,col-1)){
            if (tiles[row][col-1] == 0){
                results.add(new Position(row,col-1));
            }
        }
        Position[] returnable = new Position[results.size()];

        for (int i = 0;i<results.size();i++){
            returnable[i] = results.get(i);
        }
        return returnable;

    }

    public byte[] toByteArray(){
        // 4 = int size in bytes, 2 = number of ints for maze size, 2 = number of ints for start position,
        // 2 = number of ints for goal position, (rows+cols) = number of ints in the maze array
        ByteBuffer buff = ByteBuffer.allocate(4*(2+2+2)+(rows*cols));
        buff.putInt(rows).putInt(cols);
        buff.putInt(start.getRowIndex()).putInt(start.getColumnIndex());
        buff.putInt(goal.getRowIndex()).putInt(goal.getColumnIndex());
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                if (tiles[i][j] == 0){
                    buff.put((byte)0);
                }
                else{
                    buff.put((byte)1);
                }
            }
        }
        byte[] byteArr = buff.array();
        return byteArr;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, cols, start, goal);
        result = 31 * result + Arrays.deepHashCode(tiles);
        return result;
    }
}
