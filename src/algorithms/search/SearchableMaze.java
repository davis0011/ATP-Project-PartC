package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

/**
 * A subset of {@link ISearchable}s that has a {@link Maze} as its solvable component
 */
public class SearchableMaze implements ISearchable{
    private Maze myMaze;
    private int[][] tiles;
    private AState goal;
    private AState start;


    public SearchableMaze(Maze maze) {
        tiles = maze.getFullMaze();
        myMaze = maze;
        goal = new MazeState(maze.getGoalPosition(),null);
        start = new MazeState(maze.getStartPosition(), null);
    }

    /**
     * The reset method iterates over the tiles of the {@link Maze} in the {@link SearchableMaze} and sets any cells that
     * were marked with a "5", the mark used for having visited that cell in the process of a {@link Solution}, and sets
     * it back to "0" so that another search can be run on the {@link SearchableMaze}
     */
    public void reset(){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j] == 5)
                    tiles[i][j] = 0;
            }
        }
    }

    /**
     * @param state The {@link AState} in the {@link ISearchable} that we want to query for other {@link AState}s that
     *              are directly reachable from it
     * @implNote Since the assignment told us to consider diagonals as adjacent if there was a path to them through the
     * cardinal neighbours, we first build an array of {@link AState}s for the cardinal neighbours, then we get all
     * neighbours for those "closest" neighbours. Since we now have all {@link AState}s reachable in 2 cardinal steps,
     * diagonals included, we just need to check that the difference from each potential {@link AState}'s row and column
     * coordinates is less than or equal to 1, meaning cardinal neighbours and diagonal neighbours.
     * @return A list of {@link AState}s that are either cardinal to or diagonal to the passed argument {@link AState}
     */
    @Override
    public AState[] getAllPossibleStates(AState state) {
        MazeState MState = (MazeState) state;
        ArrayList<Position> PossibleStates = new ArrayList<>();
        Position[] neighbours = myMaze.getNeighbours(MState.getPosition().getRowIndex(),MState.getPosition().getColumnIndex());
        //Position[] neighboursOfNeighbours1 = myMaze.getDiagNeighbours(MState.getPosition().getRowIndex(),MState.getPosition().getColIndex());
        for (int i = 0; i < neighbours.length; i++){
            PossibleStates.add(neighbours[i]);
            Position[] neighboursOfNeighbours = myMaze.getNeighbours(neighbours[i].getRowIndex(),neighbours[i].getColumnIndex());
            for (int j = 0; j < neighboursOfNeighbours.length; j++){
                if (MState.getPosition().diagonalTo(neighboursOfNeighbours[j])) {
                    if (!PossibleStates.contains(neighboursOfNeighbours[j])){
                    PossibleStates.add(neighboursOfNeighbours[j]);
                    }
                }
            }
        }
        AState[] lastCall = new AState[PossibleStates.size()];
        for (int i = 0; i < PossibleStates.size(); i++) {
            lastCall[i] = new MazeState(PossibleStates.get(i),null);
        }
        return lastCall;


    }

    /**
     * @param state the {@link AState} to mark as visited
     */
    public void paintVisited(AState state)
    {
        MazeState  mstate = (MazeState) state;
        this.tiles[mstate.getPosition().getRowIndex()][mstate.getPosition().getColumnIndex()] = 5;
    }

    /**
     * @param state The {@link AState} in the {@link ISearchable}
     * @return True if the {@link AState} was visited, else false
     */
    public boolean wasVisited(AState state){
        MazeState  mstate = (MazeState) state;
        if (this.tiles[mstate.getPosition().getRowIndex()][mstate.getPosition().getColumnIndex()] == 5){
            return true;
        }
        return false;
    }

    /**
     * @return The goal {@link MazeState} of the {@link SearchableMaze}
     */
    public MazeState getGoal(){
        return (MazeState) goal;
    }

    /**
     * @return The start {@link MazeState} of the {@link SearchableMaze}
     */
    public MazeState getStart(){
        return (MazeState) start;
    }
}
