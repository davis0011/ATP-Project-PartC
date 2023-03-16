package algorithms.search;

/**
 * An object that can be searched using an inheritor of {@link ISearchingAlgorithm}
 */
public interface ISearchable {

    /**
     * @return An {@link AState} representing the goal of the {@link ISearchable}
     */
    AState getGoal();

    /**
     * @return An {@link AState} representing the start of the {@link ISearchable}
     */
    AState getStart();

    /**
     *Will mark the state represented by the {@link AState} as visited, useful for optimizing runtimes and memory usage
     * @param state the {@link AState} to mark as visited
     */
    void paintVisited(AState state);

    /**
     *Will check if the state represented by the {@link AState} was marked as visited, useful for optimizing runtimes and memory usage
     * @param state the {@link AState} in the {@link ISearchable}
     * @return True if the {@link AState} was visited, else false
     */
    boolean wasVisited(AState state);

    /**
     * @param state The {@link AState} in the {@link ISearchable} that we want to query for other {@link AState}s that are directly reachable from it
     * @return The list of reachable {@link AState}s
     */
    AState[] getAllPossibleStates(AState state);

    /**
     * will reset the values in the searchable to allow for reuse of the {@link ISearchable} for multiple uses of different algorithms
     * on the same maze
     */
    void reset();
}
