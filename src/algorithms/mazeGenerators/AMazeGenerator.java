package algorithms.mazeGenerators;

/**
 * the abstract class for maze generators to extend
 */
public abstract class AMazeGenerator implements IMazeGenerator{
    /**
     * @param rows the number of rows in the maze to be generated
     * @param cols the number of columns in the maze to be generated
     * @return the time in milliseconds it takes to generate the maze with the given params
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int cols) {
        long start,finish;
        start = System.currentTimeMillis();
        generate(rows, cols);
        finish = System.currentTimeMillis();
        return finish-start;
    }
}
