package algorithms.search;

import java.util.ArrayList;

/**
 * A searching algorithm that uses DFS to find the goal {@link AState} from the start {@link AState}, {@link DepthFirstSearch}
 * is less consistent than {@link BreadthFirstSearch} but can sometimes solve the {@link ISearchable} in less time and less
 * evaluated nodes
 * @implNote The {@link ArrayList} used as the queue is accessed in the course of the algorithm in such a way that it behaves
 * like a {@link java.util.Stack}
 */
public class DepthFirstSearch extends ASearchingAlgorithm{
    private int nodesEvaluated;
    private ArrayList<AState> queue;
    private ISearchable searchable;

    /**@implNote 1. Start by putting the start {@link AState} on top of a stack.<br>
    2. Take the top item of the stack and add it to the visited list.<br>
    3. Create a list of that {@link AState}s neighbours. Add the ones which aren't marked as visited to the top of the stack.<br>
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
        queue = new ArrayList<>();

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
            }
            addToQueue(states);

            //visited.add(next);
            searchable.paintVisited(next);
            next = queue.remove(queue.size()-1);
            nodesEvaluated++;
        }
        searchable.reset();
        return null;
    }

    /**
     * @param states The neighbours of an {@link AState} that was inspected during the DFS search.
     * @implNote Since nodes get painted when we visit them, we can know if we should add a node to the stack.<br>
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
            queue.add(states[i]);
        }
    }

    /**
     * @return The number of {@link AState}s the {@link DepthFirstSearch} needed to check before it found the goal
     */
    @Override
    public String getNumberOfNodesEvaluated() {
        return Integer.toString(nodesEvaluated);
    }

    /**
     * @return The name of the algorithm that {@link DepthFirstSearch} uses
     */
    public String getName(){
        return "Depth First Search";
    }


}
