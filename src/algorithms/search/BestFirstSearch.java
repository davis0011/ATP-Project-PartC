package algorithms.search;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A searching algorithm that uses a modified BFS to find the goal {@link AState} from the start {@link AState},
 * {@link BestFirstSearch} is similar to {@link BreadthFirstSearch}, in that it is more consistent than
 * {@link DepthFirstSearch} but can sometimes solve the {@link ISearchable} in more time and more evaluated nodes, but
 * it is equivalent in matters of O complexity<br>
 * This algorithm uses weights on the cells it checks in order to prioritize diagonals over cardinal movement, but this
 * can be changed with simple edits to the code. In essence {@link BestFirstSearch} is just a slightly more efficient variant
 * or {@link BreadthFirstSearch}
 * @implNote The queue used in this class is different from {@link BreadthFirstSearch}'s queue, it is a {@link PriorityQueue}
 * and as such uses the weights we assign to the nodes in order to arrange the queue so that the top of the queue is always
 * the lightest and easiest node to get to
 */
public class BestFirstSearch extends ASearchingAlgorithm{

    private int nodesEvaluated;
    private PriorityQueue<MazeState> queue;
    private ISearchable searchable;

    /**@implNote 1. Start by putting the start {@link AState} on top of a {@link PriorityQueue}.<br>
    2. Take the top item of the queue and add it to the visited list.<br>
    3. Create a list of that {@link AState}s neighbours. Give cardinal neighbours a weight of the previous node plus 10,
    and diagonal neighbours a weight of the previous node plus 15, meaning that it is cheaper to get to a diagonal node
    directly than it is to go through the 2 cardinal neighbours to reach the diagonal.
    Add the nodes which aren't marked as visited to the {@link PriorityQueue} and sort the queue such that the next
    cheapest node is at the top.<br>
    4. Keep repeating steps 2 and 3 until the stack is empty.<br>
     * @param searchable An instance of {@link ISearchable}
     * @return A {@link Solution} object containing the path from start to goal
     */
    @Override
    public Solution solve(ISearchable searchable) {
        nodesEvaluated = 0;
        this.searchable = searchable;

        AState next = searchable.getStart();
        AState goal = searchable.getGoal();
        queue = new PriorityQueue<>((MazeState a, MazeState b) -> +(a.getCost() - b.getCost()));

        while(next != null){
            if(next.equals(goal)){
                Solution toReturn;
                toReturn = buildSolution(next, searchable.getStart());
                searchable.reset();
                return toReturn;
            }
            AState[] states = searchable.getAllPossibleStates(next);
            for (AState s : states){
                s.setPrev(next);
                MazeState mnext = (MazeState) next,ms = (MazeState) s;
                if (mnext.getPosition().diagonalTo(ms.getPosition())){
                    ((MazeState) s).setCost(15+ mnext.getCost());
                }
                else { ((MazeState) s).setCost(10+ mnext.getCost()); }
            }
            addToQueue(states);

            searchable.paintVisited(next);
            next = queue.poll();
            nodesEvaluated++;
        }
        searchable.reset();
        return null;
    }
    /**
     * @param states The neighbours of an {@link AState} that was inspected during the BFS search.
     * @implNote Since nodes get painted when we visit them, we can know if we should add a node to the queue.<br>
     * The same is true if the node is in the queue already, so we need to check that too
     */
    private void addToQueue(AState[] states){
        for (int i = 0; i < states.length; i++){
            if (searchable.wasVisited((MazeState) states[i])){
                continue;
            }
            if (queue.contains(states[i])){

                continue;
            }
            queue.add((MazeState) states[i]);
        }
    }
    /**
     * @return The number of {@link AState}s the {@link BestFirstSearch} needed to check before it found the goal
     */
    @Override
    public String getNumberOfNodesEvaluated() {
        return Integer.toString(nodesEvaluated);
    }

    /**
     * @return The name of the algorithm that {@link BestFirstSearch} uses
     */
    public String getName()
    {
        return "Best First Search";
    }
}
