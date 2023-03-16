package algorithms.mazeGenerators;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;


/**
 * a maze generator that returns a maze that is generated using a DFS algorithm and is solvable
 * the paths in the maze will be represented by 0s and the walls by 1s,
 * the paths will all be connected and will not have any circles
 * Ex: [[0,0,1],
 *      [0,1,0],
 *      [0,0,0]]
 */
public class MyMazeGenerator extends AMazeGenerator{

    /**
     * the maze that the generator will eventually return
     */
    private Maze WIP;
    /**
     * row dimension of the maze
     */
    private int WIProws;
    /**
     * column dimension of the maze
     */
    private int WIPcols;
    /**
     * the stack that the DFS algorithm will use to generate the maze
     */
    private Stack<GenOrigin> Prevs;

    /**
     * the generation method for MyMazeGenerator will start by setting all cell values to 1 to simulate a maze with no
     * paths, and it will randomly choose a start position, with the start being on the left boarder of the maze
     * the method will then pass responsibility for the DFS part of the generation to the DFS method
     * @param rows the number of rows in the maze to be generated
     * @param cols the number of columns in the maze to be generated
     * @return the finished maze, random with each generation, a connected graph with no circles
     */
    @Override
    public Maze generate(int rows, int cols) {
        Prevs = new Stack<>();
        WIP = new Maze(rows, cols);
        WIProws = rows;
        WIPcols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                WIP.SetInPlace(i, j, 1);

            }
        }
        Random ran = new Random();
        int s_row = ran.nextInt(WIProws);
        int s_col = 0;
        WIP.setStartPosition(s_row, s_col);
        WIP.SetInPlace(s_row, s_col, 2);//Start of building
        Prevs.push(new GenOrigin(s_row,s_col));
        RandDFS();
        return WIP;
    }

    /**checker method to see if the position passed is within the bounds of the maze
     * @param row the row index of the tested position
     * @param col the column index of the tested position
     * @return boolean true if index is within bounds of the maze
     */
    private boolean LegalMove(int row, int col){
        if (row < 0) {return false;}
        if (col < 0) {return false;}
        if (row > WIProws - 1) {return false;}
        if (col > WIPcols - 1) {return false;}
        return true;
    }

    /**
     * RandDFS is responsible for the algorithmic execution of the DFS algorithm to generate the maze
     * @implNote
     * First we start a while loop that will exit when no more nodes can be made from walls to paths and give it the
     * start position as the first node to check.
     * While the node we are looking at has neighbours that are not paths and that will not form circles if made into paths,
     * which is checked by our Go method, we add such a nodes to our stack, set the value of the current node to 0 so that
     * it is considered a path and restart the loop while looking at the topmost node in the stack. When the stack is empty
     * we have checked all nodes in the maze, and we return the maze
     */
    private void RandDFS(){
        int row, col;
        Random ran = new Random();
        while(!Prevs.isEmpty()){
            Position next = null;
            GenOrigin temp = Prevs.pop();
            row = temp.row;
            col = temp.col;
            int[] directions = {0,1,2,3};
            while(true){//to be comtinue while
                int go = ran.nextInt(4);
                if ((directions[0]+directions[1]+directions[2]+directions[3]) == -4){
                    WIP.SetInPlace(row,col,0);
                    break;
                }
                if (directions[go] == -1) {continue;}
                next = go(go,row,col);
                directions[go] = -1;
                if (next != null){
                    GenOrigin swap = Prevs.pop();
                    Prevs.push(new GenOrigin(row, col));
                    Prevs.push(swap);
                    break;
                }
            }
        }
        int end = ran.nextInt(WIProws);
        while(WIP.tiles[end][WIPcols - 1] != 0){
            end = ran.nextInt(WIProws);
        }
        WIP.setGoalPosition(end, WIPcols - 1);
    }

    /**
     * @param dir An int from 0 to 3, indicated DFT direction
     * @param row The row index of the origin position
     * @param col The column index of the origin position
     * @return the position of the added path if adding said path would not have exited the maze, joined 2 paths to make
     * a circle or attempted to make a path into a path. If the cell in that direction can not be made into a path without
     * breaking those constraints the method returns a null value
     */
    private Position go(int dir, int row, int col){
        Position position;
        if (dir == 0){
            if (LegalMove(row - 1, col)) {
                position = goUp(row - 1, col);
                return position;
            }
        }

        if (dir == 1) {
            if (LegalMove(row, col + 1)) {
                position = goRight(row, col + 1);
                return position;
            }
        }

        if (dir == 2){
            if (LegalMove(row + 1, col)) {
                position = goDown(row + 1, col);
                return position;
            }
        }

        if (dir == 3){
            if (LegalMove(row, col - 1)) {
                position = goLeft(row, col - 1);
                return position;
            }
        }
        return null;
    }

    /** All go-x methods function the same way, they check if the rules mentioned in the documentation for the Go method
     * are broken by the move that the method is given and return a Position object for the cell if none are broken, otherwise
     * it will return a null value
     * @param row The row index of the origin position
     * @param col The column index of the origin position
     * @return New valid Position object in the maze, or null if not valid
     */
    private Position goUp(int row, int col){
        if (LegalMove(row - 1,col))//up
        {
            if (WIP.tiles[row - 1][col] == 0 || WIP.tiles[row - 1][col] == 2){return null;}
        }
        if (LegalMove(row,col + 1))//right
        {
            if (WIP.tiles[row][col + 1] == 0 || WIP.tiles[row][col + 1] == 2){return null;}
        }
        if (LegalMove(row,col - 1))//left
        {
            if (WIP.tiles[row][col - 1] == 0 || WIP.tiles[row][col - 1] == 2){return null;}
        }
        if (WIP.tiles[row][col] == 0) {return null;}
        WIP.SetInPlace(row,col,2);
        Prevs.push(new GenOrigin(row,col));
        return new Position(row,col);
    }

    /** All go-x methods function the same way, they check if the rules mentioned in the documentation for the Go method
     * are broken by the move that the method is given and return a Position object for the cell if none are broken, otherwise
     * it will return a null value
     * @param row The row index of the origin position
     * @param col The column index of the origin position
     * @return New valid Position object in the maze, or null if not valid
     */
    private Position goRight(int row, int col){
        if (LegalMove(row - 1,col))//up
        {
            if (WIP.tiles[row - 1][col] == 0 || WIP.tiles[row - 1][col] == 2){return null;}
        }
        if (LegalMove(row,col + 1))//right
        {
            if (WIP.tiles[row][col + 1] == 0 || WIP.tiles[row][col + 1] == 2){return null;}
        }
        if (LegalMove(row + 1,col))//down
        {
            if (WIP.tiles[row + 1][col] == 0 || WIP.tiles[row + 1][col] == 2){return null;}
        }
        if (WIP.tiles[row][col] == 0) {return null;}
        WIP.SetInPlace(row,col,2);
        Prevs.push(new GenOrigin(row,col));
        return new Position(row,col);
    }
    /** All go-x methods function the same way, they check if the rules mentioned in the documentation for the Go method
     * are broken by the move that the method is given and return a Position object for the cell if none are broken, otherwise
     * it will return a null value
     * @param row The row index of the origin position
     * @param col The column index of the origin position
     * @return New valid Position object in the maze, or null if not valid
     */
    private Position goDown(int row, int col){
        if (LegalMove(row,col - 1))//left
        {
            if (WIP.tiles[row][col - 1] == 0 || WIP.tiles[row][col - 1] == 2){return null;}
        }
        if (LegalMove(row,col + 1))//right
        {
            if (WIP.tiles[row][col + 1] == 0 || WIP.tiles[row][col + 1] == 2){return null;}
        }
        if (LegalMove(row + 1,col))//down
        {
            if (WIP.tiles[row + 1][col] == 0 || WIP.tiles[row + 1][col] == 2){return null;}
        }
        if (WIP.tiles[row][col] == 0) {return null;}
        WIP.SetInPlace(row,col,2);
        Prevs.push(new GenOrigin(row,col));
        return new Position(row,col);
    }
    /** All go-x methods function the same way, they check if the rules mentioned in the documentation for the Go method
     * are broken by the move that the method is given and return a Position object for the cell if none are broken, otherwise
     * it will return a null value
     * @param row The row index of the origin position
     * @param col The column index of the origin position
     * @return New valid Position object in the maze, or null if not valid
     */
    private Position goLeft(int row, int col){
        if (LegalMove(row - 1,col))//up
        {
            if (WIP.tiles[row - 1][col] == 0 || WIP.tiles[row - 1][col] == 2){return null;}
        }
        if (LegalMove(row,col - 1))//left
        {
            if (WIP.tiles[row][col - 1] == 0 || WIP.tiles[row][col - 1] == 2){return null;}
        }
        if (LegalMove(row + 1,col))//down
        {
            if (WIP.tiles[row + 1][col] == 0 || WIP.tiles[row + 1][col] == 2){return null;}
        }
        if (WIP.tiles[row][col] == 0) {return null;}
        WIP.SetInPlace(row,col,2);
        Prevs.push(new GenOrigin(row,col));
        return new Position(row,col);
    }

}
