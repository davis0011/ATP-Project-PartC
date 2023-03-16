package algorithms.search;

/**
 * A searching algorithm that solves {@link ISearchable}s
 */
public interface ISearchingAlgorithm {

    /**This method will apply an algorithm to the {@link ISearchable} according to the implementor of the interface
     * @param searchable An instance of {@link ISearchable}
     * @return A {@link Solution} object containing the solution to the {@link ISearchable}
     */
    public Solution solve(ISearchable searchable);

    /**
     * @return The name of the implementing algorithm
     */
    public String getName();

    /**
     * @return The number of nodes that the algorithm had to evaluate in order to solve the {@link ISearchable}
     */
    public String getNumberOfNodesEvaluated();
}
