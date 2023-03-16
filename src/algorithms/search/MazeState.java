package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * MazeState extends {@link AState} and will be used for solving the search for maze-type problems
 */
public class MazeState extends AState{
    private Position position;
    private int cost;


    /**
     * The default constructor for {@link MazeState}s
     * @param pos The {@link Position} object passed will represent the location int the {@link algorithms.mazeGenerators.Maze}(graph) that the {@link MazeState} represents
     * @param state This argument can be passed null in case we don't want the MazeState to have a previous node,
     *              for example if it is the first node and therefore looking to its prev would mean the end of the path
     */
    public MazeState(Position pos, MazeState state) {
        super(state);
        this.position = pos;
        this.cost = 0;
    }


    /**
     * @return returns the cost of the node, note that by default the cost is 0, so it will remain so unless set beforehand
     */
    public int getCost(){
        return this.cost;
    }

    /**
     * Sets the cost of a {@link MazeState}
     * @param cost the cost of entering the node as an integer
     */
    public void setCost(int cost)
    {

        this.cost = cost;
    }

    /**
     * @param o the object to be compared with self, default implementation until it compares the {@link Position} fields
     * @see Position
     * @return boolean true if eq, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == (MazeState)o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeState state = (MazeState) o;
        return this.position.equals(state.position);
    }

    /**
     * @return The {@link Position} field of the {@link MazeState}
     */
    public Position getPosition()
    {
        return this.position;
    }

    /**
     * default generated method
     * @return 0
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * @return hands off responsibility to the {@link Position} field
     * @see Position
     */
    @Override
    public String toString() {
        return position.toString();
    }
}
