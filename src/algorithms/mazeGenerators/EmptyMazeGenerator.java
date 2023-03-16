package algorithms.mazeGenerators;

import java.util.Random;

/**
 * a maze generator that returns only empty mazes, meaning mazes where all cells are paths
 * Ex: [[0,0,0],
 *      [0,0,0],
 *      [0,0,0]]
 */
public class EmptyMazeGenerator extends AMazeGenerator{
    /**
     * @param rows the number of rows in the maze to be generated
     * @param cols the number of columns in the maze to be generated
     * @return a Maze object with only empty cells, the start on the leftmost row and the goal the rightmost column
     */
    @Override
    public Maze generate(int rows, int cols) {
        Maze maze = new Maze(rows, cols);
        Random rand = new Random();
        maze.setStartPosition(rand.nextInt(rows), 0);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                maze.SetInPlace(i,j,0);
            }
        }
        maze.setGoalPosition(rand.nextInt(rows),cols - 1);
        return maze;
    }
}
