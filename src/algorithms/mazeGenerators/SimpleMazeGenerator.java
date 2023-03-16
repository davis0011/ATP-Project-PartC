package algorithms.mazeGenerators;

import java.util.Random;

/**
 * a maze generator that returns a maze that is solvable and not too complex
 * every second row of the maze will contain all 1s except for a random 0 in the row, allowing passage between rows,
 * meaning that the paths will all be connected and not have any circles
 * Ex: [[0,0,0],
 *      [1,1,0],
 *      [0,0,0]]
 */
public class SimpleMazeGenerator extends AMazeGenerator{

    /**
     * @param rows the number of rows in the maze to be generated
     * @param cols the number of columns in the maze to be generated
     * @return a Maze object with connected cells, the start on the leftmost row and the goal the rightmost column
     */
    @Override
    public Maze generate(int rows, int cols) {
        Maze maze = new Maze(rows, cols);
        //int[][] maze = new int[rows][cols];//now is array, needs to be maze object when we implement Maze
        Random random = new Random();
        maze.setStartPosition(0, 0);
        for (int i = 0; i < rows; i++) {
            if ( i % 2 != 0 ){
                int temp = random.nextInt(cols);
                for (int j = 0; j < cols; j++) {
                    if (j != temp) {
                        maze.SetInPlace(i, j, 1);
                    }
                }
            }
        }
        if (maze.tiles[rows - 1][cols - 1] == 0) {
            maze.setGoalPosition(rows - 1, cols - 1);
        }
        else {maze.setGoalPosition(rows - 2, cols - 1);}
        return maze;
    }
}
