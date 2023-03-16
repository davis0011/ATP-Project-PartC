package algorithms.search;

/**
 * A searching algorithm that solves {@link ISearchable}s
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    /**
     * @param goal The {@link AState} that the {@link ISearchable} needs to get to
     * @param start The {@link AState} that the {@link ISearchable} starts at
     * @return the {@link Solution} to the maze, a wrapper object for a list of {@link AState}s
     */
    protected Solution buildSolution(AState goal, AState start){
        Solution solution = new Solution();
        AState temp = goal;
        while(temp.getPrev()!= null){
            solution.addStateToPath(temp);
            temp = temp.getPrev();
        }
        solution.addStateToPath(start);
        return solution;
    }
}
